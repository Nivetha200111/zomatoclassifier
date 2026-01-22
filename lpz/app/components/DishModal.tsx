'use client';

import { useState, useCallback, useMemo } from 'react';
import { Dish, GoalType, ContextType, goals, dishes } from '../data/dishes';
import GoalWheel from './GoalWheel';
import GoalScrubber from './GoalScrubber';
import ScorePanel from './ScorePanel';
import ContextToggleRow from './ContextToggleRow';
import SwapCard, { getSwapSuggestion } from './SwapCard';

interface DishModalProps {
  dish: Dish;
  onClose: () => void;
  onChangeDish?: (dish: Dish) => void;
}

// Context-aware scoring
function getContextModifier(dish: Dish, goal: GoalType, context: ContextType): number {
  if (context === 'none') return 0;

  let modifier = 0;

  if (context === 'post-workout') {
    // Protein becomes more important
    if (dish.protein > 30) modifier += 8;
    else if (dish.protein > 20) modifier += 4;
    else if (dish.protein < 15) modifier -= 5;
    // Carbs less penalized
    if (dish.carbs > 40 && goal === 'cutting') modifier += 5;
    if (!dish.isFried) modifier += 3;
  }

  if (context === 'late-night') {
    // Heavy foods penalized
    if (dish.calories > 600) modifier -= 10;
    else if (dish.calories > 450) modifier -= 5;
    else if (dish.calories < 350) modifier += 5;
    // High fat penalized
    if (dish.fat > 30) modifier -= 8;
    else if (dish.fat < 15) modifier += 4;
    // Fried foods penalized
    if (dish.isFried) modifier -= 8;
  }

  if (context === 'office-lunch') {
    // Balanced calories preferred
    if (dish.calories >= 350 && dish.calories <= 550) modifier += 6;
    else if (dish.calories > 700) modifier -= 8;
    // Avoid fried
    if (dish.isFried) modifier -= 6;
    if (dish.fat > 35) modifier -= 5;
    // Fiber helps
    if ((dish.fiber || 0) > 5) modifier += 3;
    // Sugary foods cause crashes
    if (dish.isSugary) modifier -= 5;
  }

  return modifier;
}

function getScoreWithContext(dish: Dish, goal: GoalType, context: ContextType): number {
  const baseScore = dish.goals[goal].score;
  const modifier = getContextModifier(dish, goal, context);
  return Math.max(0, Math.min(100, baseScore + modifier));
}

function getContextReason(dish: Dish, context: ContextType): string | null {
  if (context === 'none') return null;

  if (context === 'post-workout') {
    if (dish.protein > 30 && !dish.isFried) 
      return 'High protein with easy digestion — ideal post-workout';
    if (dish.protein > 25) 
      return 'Good protein content supports muscle recovery';
    if (dish.isFried) 
      return 'Fried preparation may slow post-workout absorption';
    return 'Moderate choice for post-workout recovery';
  }

  if (context === 'late-night') {
    if (dish.calories < 350 && dish.fat < 15) 
      return 'Light and easy to digest — suitable for late night';
    if (dish.calories > 600) 
      return 'Heavy meal — may disrupt sleep quality';
    if (dish.isFried) 
      return 'Fried foods digest slowly — not ideal before bed';
    if (dish.fat > 30) 
      return 'High fat content may cause discomfort at night';
    return 'Moderate weight — timing matters for digestion';
  }

  if (context === 'office-lunch') {
    if (dish.calories >= 350 && dish.calories <= 550 && !dish.isFried) 
      return 'Balanced energy — helps maintain afternoon focus';
    if (dish.calories > 700) 
      return 'Heavy meal may cause afternoon energy dip';
    if (dish.isFried) 
      return 'Fried foods can trigger post-lunch sluggishness';
    if (dish.isSugary) 
      return 'Sugar spike followed by crash — affects productivity';
    return 'Adequate for office lunch — moderate energy load';
  }

  return null;
}

export default function DishModal({ dish: initialDish, onClose, onChangeDish }: DishModalProps) {
  const [dish, setDish] = useState(initialDish);
  const [activeGoal, setActiveGoal] = useState<GoalType>('cutting');
  const [activeContext, setActiveContext] = useState<ContextType>('none');
  const [showGoalWheel, setShowGoalWheel] = useState(false);
  const [interpolatedScore, setInterpolatedScore] = useState<number | undefined>(undefined);
  const [isScrubbing, setIsScrubbing] = useState(false);

  const currentGoal = goals.find(g => g.id === activeGoal)!;

  const score = useMemo(() => {
    if (interpolatedScore !== undefined) return interpolatedScore;
    return getScoreWithContext(dish, activeGoal, activeContext);
  }, [dish, activeGoal, activeContext, interpolatedScore]);

  const explanation = useMemo(() => {
    const contextReason = getContextReason(dish, activeContext);
    if (contextReason) return contextReason;
    return dish.goals[activeGoal].explanation;
  }, [dish, activeGoal, activeContext]);

  const swapSuggestion = useMemo(() => {
    return getSwapSuggestion(dish, activeGoal, activeContext, dishes, getScoreWithContext);
  }, [dish, activeGoal, activeContext]);

  const handleScrubbing = useCallback((newScore: number) => {
    setInterpolatedScore(newScore);
    setIsScrubbing(true);
  }, []);

  const handleGoalChange = useCallback((goal: GoalType) => {
    setActiveGoal(goal);
    setInterpolatedScore(undefined);
    setIsScrubbing(false);
  }, []);

  const handleSwap = useCallback(() => {
    if (swapSuggestion) {
      setDish(swapSuggestion.suggestedDish);
      setInterpolatedScore(undefined);
      setIsScrubbing(false);
      onChangeDish?.(swapSuggestion.suggestedDish);
    }
  }, [swapSuggestion, onChangeDish]);

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
          <div 
            className="relative h-44 md:h-52 rounded-t-2xl overflow-hidden"
            style={{ background: dish.imageGradient }}
          >
            <div className="absolute inset-0 opacity-30">
              <div className="absolute inset-0" style={{
                backgroundImage: `radial-gradient(circle at 30% 70%, rgba(255,255,255,0.15) 0%, transparent 50%),
                                 radial-gradient(circle at 70% 30%, rgba(255,255,255,0.2) 0%, transparent 50%)`
              }} />
            </div>

            <button
              onClick={onClose}
              className="absolute top-4 right-4 w-10 h-10 rounded-full bg-black/50 backdrop-blur-sm flex items-center justify-center text-white hover:bg-black/70 transition-colors"
            >
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>

            <div className="absolute top-4 left-4">
              <div className={`w-6 h-6 border-2 ${
                dish.isVeg ? 'border-green-500' : 'border-red-500'
              } rounded-sm flex items-center justify-center bg-gray-900/90`}>
                <div className={`w-2.5 h-2.5 rounded-full ${
                  dish.isVeg ? 'bg-green-500' : 'bg-red-500'
                }`} />
              </div>
            </div>

            {(dish.id === '2' || dish.id === '7') && (
              <div className="absolute bottom-4 left-4 px-3 py-1 bg-[#E23744] text-white text-sm font-medium rounded">
                BESTSELLER
              </div>
            )}
          </div>

          {/* Content */}
          <div className="p-6">
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

            {/* Context Toggle Row */}
            <ContextToggleRow
              currentContext={activeContext}
              onContextChange={setActiveContext}
            />

            {/* Goal Scrubber */}
            <div className="mb-6 p-4 bg-gray-800/30 rounded-2xl border border-gray-800">
              <GoalScrubber
                dish={dish}
                activeGoal={activeGoal}
                context={activeContext}
                onGoalChange={handleGoalChange}
                onScrubbing={handleScrubbing}
                getScoreWithContext={getScoreWithContext}
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
              goalData={{ score, explanation }} 
              activeGoal={activeGoal}
              activeContext={activeContext}
              interpolatedScore={interpolatedScore}
              isAnimating={isScrubbing}
            />

            {/* Swap Card */}
            <SwapCard
              suggestion={swapSuggestion}
              goal={activeGoal}
              context={activeContext}
              onSwap={handleSwap}
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
