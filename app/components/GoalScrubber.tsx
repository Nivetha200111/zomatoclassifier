'use client';

import { useRef, useState, useCallback, useEffect } from 'react';
import { goals, GoalType, Dish } from '../data/dishes';

interface GoalScrubberProps {
  dish: Dish;
  activeGoal: GoalType;
  onGoalChange: (goal: GoalType) => void;
  onScrubbing: (interpolatedScore: number, progress: number) => void;
}

export default function GoalScrubber({ 
  dish, 
  activeGoal, 
  onGoalChange,
  onScrubbing 
}: GoalScrubberProps) {
  const trackRef = useRef<HTMLDivElement>(null);
  const [isDragging, setIsDragging] = useState(false);
  const [scrubPosition, setScrubPosition] = useState(() => {
    const index = goals.findIndex(g => g.id === activeGoal);
    return index / (goals.length - 1);
  });

  // Update position when activeGoal changes externally
  useEffect(() => {
    if (!isDragging) {
      const index = goals.findIndex(g => g.id === activeGoal);
      setScrubPosition(index / (goals.length - 1));
    }
  }, [activeGoal, isDragging]);

  // Calculate interpolated values based on scrub position
  const getInterpolatedData = useCallback((position: number) => {
    const totalSegments = goals.length - 1;
    const segmentIndex = Math.min(Math.floor(position * totalSegments), totalSegments - 1);
    const segmentProgress = (position * totalSegments) - segmentIndex;
    
    const goalA = goals[segmentIndex];
    const goalB = goals[Math.min(segmentIndex + 1, goals.length - 1)];
    
    const scoreA = dish.goals[goalA.id].score;
    const scoreB = dish.goals[goalB.id].score;
    
    // Smooth interpolation using easeInOutCubic
    const eased = segmentProgress < 0.5
      ? 4 * segmentProgress * segmentProgress * segmentProgress
      : 1 - Math.pow(-2 * segmentProgress + 2, 3) / 2;
    
    const interpolatedScore = Math.round(scoreA + (scoreB - scoreA) * eased);
    
    return {
      score: interpolatedScore,
      dominantGoal: segmentProgress < 0.5 ? goalA : goalB,
      progress: position,
    };
  }, [dish]);

  const handleScrub = useCallback((clientX: number) => {
    if (!trackRef.current) return;
    
    const rect = trackRef.current.getBoundingClientRect();
    const x = clientX - rect.left;
    const position = Math.max(0, Math.min(1, x / rect.width));
    
    setScrubPosition(position);
    
    const data = getInterpolatedData(position);
    onScrubbing(data.score, position);
    
    // Snap to nearest goal when close to a marker
    const snapThreshold = 0.08;
    for (let i = 0; i < goals.length; i++) {
      const markerPosition = i / (goals.length - 1);
      if (Math.abs(position - markerPosition) < snapThreshold) {
        onGoalChange(goals[i].id);
        break;
      }
    }
  }, [getInterpolatedData, onScrubbing, onGoalChange]);

  const handleMouseDown = (e: React.MouseEvent) => {
    setIsDragging(true);
    handleScrub(e.clientX);
  };

  const handleMouseMove = useCallback((e: MouseEvent) => {
    if (isDragging) {
      handleScrub(e.clientX);
    }
  }, [isDragging, handleScrub]);

  const handleMouseUp = useCallback(() => {
    if (isDragging) {
      setIsDragging(false);
      // Snap to nearest goal on release
      const nearestIndex = Math.round(scrubPosition * (goals.length - 1));
      const snappedPosition = nearestIndex / (goals.length - 1);
      setScrubPosition(snappedPosition);
      onGoalChange(goals[nearestIndex].id);
    }
  }, [isDragging, scrubPosition, onGoalChange]);

  const handleTouchStart = (e: React.TouchEvent) => {
    setIsDragging(true);
    handleScrub(e.touches[0].clientX);
  };

  const handleTouchMove = useCallback((e: TouchEvent) => {
    if (isDragging) {
      handleScrub(e.touches[0].clientX);
    }
  }, [isDragging, handleScrub]);

  useEffect(() => {
    if (isDragging) {
      window.addEventListener('mousemove', handleMouseMove);
      window.addEventListener('mouseup', handleMouseUp);
      window.addEventListener('touchmove', handleTouchMove);
      window.addEventListener('touchend', handleMouseUp);
    }
    return () => {
      window.removeEventListener('mousemove', handleMouseMove);
      window.removeEventListener('mouseup', handleMouseUp);
      window.removeEventListener('touchmove', handleTouchMove);
      window.removeEventListener('touchend', handleMouseUp);
    };
  }, [isDragging, handleMouseMove, handleMouseUp, handleTouchMove]);

  // Get current interpolated color
  const getCurrentColor = () => {
    const totalSegments = goals.length - 1;
    const segmentIndex = Math.min(Math.floor(scrubPosition * totalSegments), totalSegments - 1);
    const segmentProgress = (scrubPosition * totalSegments) - segmentIndex;
    
    const colorA = goals[segmentIndex].color;
    const colorB = goals[Math.min(segmentIndex + 1, goals.length - 1)].color;
    
    // Simple color interpolation by mixing
    if (segmentProgress < 0.3) return colorA;
    if (segmentProgress > 0.7) return colorB;
    return segmentProgress < 0.5 ? colorA : colorB;
  };

  const currentColor = getCurrentColor();

  return (
    <div className="py-4">
      {/* Label */}
      <div className="flex items-center justify-between mb-3">
        <span className="text-xs text-gray-500 uppercase tracking-wider">Scrub to explore</span>
        {isDragging && (
          <span 
            className="text-xs font-medium px-2 py-0.5 rounded-full animate-pulse"
            style={{ 
              backgroundColor: `${currentColor}20`,
              color: currentColor 
            }}
          >
            Scrubbing...
          </span>
        )}
      </div>

      {/* Scrubber Track */}
      <div 
        ref={trackRef}
        className="relative h-14 cursor-grab active:cursor-grabbing select-none"
        onMouseDown={handleMouseDown}
        onTouchStart={handleTouchStart}
      >
        {/* Track Background */}
        <div className="absolute top-1/2 left-0 right-0 h-2 -translate-y-1/2 rounded-full bg-gray-800 overflow-hidden">
          {/* Gradient fill based on position */}
          <div 
            className="h-full rounded-full transition-all duration-75"
            style={{ 
              width: `${scrubPosition * 100}%`,
              background: `linear-gradient(90deg, ${goals[0].color}, ${currentColor})`
            }}
          />
        </div>

        {/* Goal Markers */}
        {goals.map((goal, index) => {
          const position = index / (goals.length - 1);
          const isActive = Math.abs(scrubPosition - position) < 0.08;
          const isPast = scrubPosition >= position;
          
          return (
            <div
              key={goal.id}
              className="absolute top-1/2 -translate-y-1/2 -translate-x-1/2 flex flex-col items-center"
              style={{ left: `${position * 100}%` }}
            >
              {/* Marker dot */}
              <div 
                className={`
                  w-4 h-4 rounded-full border-2 transition-all duration-200
                  ${isActive 
                    ? 'scale-125 border-white shadow-lg' 
                    : isPast 
                      ? 'border-transparent' 
                      : 'border-gray-600'
                  }
                `}
                style={{ 
                  backgroundColor: isPast || isActive ? goal.color : 'transparent',
                  boxShadow: isActive ? `0 0 12px ${goal.color}` : 'none'
                }}
              />
              
              {/* Icon + Label */}
              <div 
                className={`
                  absolute top-6 flex flex-col items-center transition-all duration-200
                  ${isActive ? 'opacity-100 scale-105' : 'opacity-50'}
                `}
              >
                <span className="text-base">{goal.icon}</span>
                <span 
                  className="text-[9px] font-medium mt-0.5 whitespace-nowrap"
                  style={{ color: isActive ? goal.color : '#6b7280' }}
                >
                  {goal.label}
                </span>
              </div>
            </div>
          );
        })}

        {/* Scrubber Thumb */}
        <div 
          className={`
            absolute top-1/2 -translate-y-1/2 -translate-x-1/2 
            w-6 h-6 rounded-full bg-white 
            shadow-lg transition-transform duration-75
            flex items-center justify-center
            ${isDragging ? 'scale-125' : 'scale-100'}
          `}
          style={{ 
            left: `${scrubPosition * 100}%`,
            boxShadow: `0 0 0 4px ${currentColor}40, 0 4px 12px rgba(0,0,0,0.3)`
          }}
        >
          <div 
            className="w-2 h-2 rounded-full"
            style={{ backgroundColor: currentColor }}
          />
        </div>
      </div>

      {/* Hint */}
      <p className="text-center text-[10px] text-gray-600 mt-6">
        Drag to see how meaning shifts between goals
      </p>
    </div>
  );
}

