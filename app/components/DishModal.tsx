'use client';

import { useState, useCallback } from 'react';
import { Dish, GoalType, goals } from '../data/dishes';
import GoalWheel from './GoalWheel';
import GoalScrubber from './GoalScrubber';
import ScorePanel from './ScorePanel';

interface DishModalProps {
  dish: Dish;
  onClose: () => void;
}

export default function DishModal({ dish, onClose }: DishModalProps) {
  const [activeGoal, setActiveGoal] = useState<GoalType>('cutting');
  const [showGoalWheel, setShowGoalWheel] = useState(false);
  const [interpolatedScore, setInterpolatedScore] = useState<number | undefined>(undefined);
  const [isScrubbing, setIsScrubbing] = useState(false);

  const currentGoal = goals.find(g => g.id === activeGoal)!;

  const handleScrubbing = useCallback((score: number, progress: number) => {
    setInterpolatedScore(score);
    setIsScrubbing(true);
  }, []);

  const handleGoalChange = useCallback((goal: GoalType) => {
    setActiveGoal(goal);
    setInterpolatedScore(undefined);
    setIsScrubbing(false);
  }, []);

  return (
    <>
      {/* Modal Backdrop */}
      <div 
        className="fixed inset-0 z-40 bg-black/70 backdrop-blur-sm animate-fadeIn"
        onClick={onClose}
      />

      {/* Modal Content */}
      <div className="fixed inset-4 md:inset-auto md:top-1/2 md:left-1/2 md:-translate-x-1/2 md:-translate-y-1/2 md:w-full md:max-w-2xl md:max-h-[90vh] z-40 overflow-auto">
        <div className="bg-gray-900 border border-gray-800 rounded-2xl shadow-2xl animate-scaleIn">
          {/* Header Image */}
          <div className="relative h-44 md:h-52 rounded-t-2xl overflow-hidden bg-gray-800">
            <img 
              src={dish.imageUrl} 
              alt={dish.name}
              className="w-full h-full object-cover"
            />
            
            {/* Gradient Overlay */}
            <div className="absolute inset-0 bg-gradient-to-t from-black/70 via-black/20 to-transparent" />

            {/* Close Button */}
            <button
              onClick={onClose}
              className="absolute top-4 right-4 w-10 h-10 rounded-full bg-black/50 backdrop-blur-sm flex items-center justify-center text-white hover:bg-black/70 transition-colors"
            >
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>

            {/* Veg/Non-Veg Badge */}
            <div className="absolute top-4 left-4">
              <div className={`w-6 h-6 border-2 ${
                dish.isVeg ? 'border-green-500' : 'border-red-500'
              } rounded-sm flex items-center justify-center bg-gray-900/90`}>
                <div className={`w-2.5 h-2.5 rounded-full ${
                  dish.isVeg ? 'bg-green-500' : 'bg-red-500'
                }`} />
              </div>
            </div>

            {/* Bestseller Badge */}
            {(dish.id === '2' || dish.id === '7') && (
              <div className="absolute bottom-4 left-4 px-3 py-1 bg-[#E23744] text-white text-sm font-medium rounded">
                BESTSELLER
              </div>
            )}
          </div>

          {/* Content */}
          <div className="p-6">
            {/* Title & Price Row */}
            <div className="flex items-start justify-between gap-4 mb-4">
              <div>
                <h2 className="text-2xl font-bold text-white mb-1">{dish.name}</h2>
                <p className="text-gray-400 text-sm">{dish.description}</p>
              </div>
              <span className="text-2xl font-bold text-white whitespace-nowrap">
                ₹{dish.price}
              </span>
            </div>

            {/* Macro Grid */}
            <div className="grid grid-cols-4 gap-3 mb-6">
              <div className="bg-gray-800/50 rounded-xl p-3 text-center">
                <p className="text-lg font-semibold text-orange-400">{dish.calories}</p>
                <p className="text-xs text-gray-500">kcal</p>
              </div>
              <div className="bg-gray-800/50 rounded-xl p-3 text-center">
                <p className="text-lg font-semibold text-blue-400">{dish.protein}g</p>
                <p className="text-xs text-gray-500">protein</p>
              </div>
              <div className="bg-gray-800/50 rounded-xl p-3 text-center">
                <p className="text-lg font-semibold text-yellow-400">{dish.carbs}g</p>
                <p className="text-xs text-gray-500">carbs</p>
              </div>
              <div className="bg-gray-800/50 rounded-xl p-3 text-center">
                <p className="text-lg font-semibold text-purple-400">{dish.fat}g</p>
                <p className="text-xs text-gray-500">fat</p>
              </div>
            </div>

            {/* Goal Scrubber - THE WOW FACTOR */}
            <div className="mb-6 p-4 bg-gray-800/30 rounded-2xl border border-gray-800">
              <GoalScrubber
                dish={dish}
                activeGoal={activeGoal}
                onGoalChange={handleGoalChange}
                onScrubbing={handleScrubbing}
              />
            </div>

            {/* Goal Section Header */}
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold text-white">Goal Interpretation</h3>
              <button
                onClick={() => setShowGoalWheel(true)}
                className="flex items-center gap-2 px-3 py-1.5 rounded-lg text-sm transition-all hover:scale-105"
                style={{ 
                  backgroundColor: `${currentGoal.color}15`,
                  border: `1px solid ${currentGoal.color}30`
                }}
              >
                <span>{currentGoal.icon}</span>
                <span style={{ color: currentGoal.color }}>{currentGoal.label}</span>
                <svg className="w-4 h-4 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 9l4-4 4 4m0 6l-4 4-4-4" />
                </svg>
              </button>
            </div>

            {/* Score Panel */}
            <ScorePanel 
              goalData={dish.goals[activeGoal]} 
              activeGoal={activeGoal}
              interpolatedScore={interpolatedScore}
              isAnimating={isScrubbing}
            />

            {/* Add to Cart Button */}
            <button className="w-full mt-6 py-4 bg-[#E23744] hover:bg-[#c42f3a] text-white font-semibold rounded-xl transition-colors flex items-center justify-center gap-2">
              <span>Add to Cart</span>
              <span className="text-white/80">•</span>
              <span>₹{dish.price}</span>
            </button>
          </div>
        </div>
      </div>

      {/* Goal Wheel Overlay */}
      {showGoalWheel && (
        <GoalWheel
          activeGoal={activeGoal}
          onSelectGoal={handleGoalChange}
          onClose={() => setShowGoalWheel(false)}
        />
      )}
    </>
  );
}
