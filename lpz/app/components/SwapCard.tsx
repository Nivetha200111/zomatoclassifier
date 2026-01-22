'use client';

import { Dish, GoalType, ContextType, goals, contexts } from '../data/dishes';

interface SwapSuggestion {
  originalDish: Dish;
  suggestedDish: Dish;
  scoreDelta: number;
  reason: string;
}

interface SwapCardProps {
  suggestion: SwapSuggestion | null;
  goal: GoalType;
  context: ContextType;
  onSwap: () => void;
}

export default function SwapCard({ suggestion, goal, context, onSwap }: SwapCardProps) {
  if (!suggestion) return null;

  const goalData = goals.find(g => g.id === goal);
  const contextData = contexts.find(c => c.id === context);

  return (
    <div className="mt-6 animate-slideUp">
      <div className="p-4 rounded-2xl bg-green-500/10 border border-green-500/20">
        {/* Header */}
        <div className="flex items-center gap-2 mb-3">
          <span className="text-base">💡</span>
          <span className="text-sm font-medium text-green-400">Suggested swap</span>
          <span className="text-xs text-gray-500">(optional)</span>
        </div>

        {/* Swap visualization */}
        <div className="flex items-center gap-3 mb-3">
          <div className="flex-1">
            <p className="text-sm text-gray-400 truncate">{suggestion.originalDish.name}</p>
          </div>
          
          <div className="w-8 h-8 rounded-full bg-green-500/20 flex items-center justify-center">
            <span className="text-green-400 font-bold">→</span>
          </div>
          
          <div className="flex-1 text-right">
            <p className="text-sm font-semibold text-white truncate">{suggestion.suggestedDish.name}</p>
          </div>
        </div>

        {/* Score delta */}
        <div className="flex items-center gap-2 mb-3">
          <span className="px-2 py-1 rounded-lg bg-green-500/20 text-green-400 text-sm font-bold">
            +{suggestion.scoreDelta}
          </span>
          <span className="text-xs text-gray-500">
            for {goalData?.label}
            {context !== 'none' && contextData && ` · ${contextData.label}`}
          </span>
        </div>

        {/* Reason */}
        <p className="text-sm text-gray-400 mb-4">{suggestion.reason}</p>

        {/* CTA */}
        <button
          onClick={onSwap}
          className="w-full py-3 rounded-xl bg-green-500 hover:bg-green-600 text-white font-semibold transition-colors"
        >
          Try this instead
        </button>
      </div>
    </div>
  );
}

// Swap logic
const swapMappings: Record<string, string> = {
  '2': '1',  // Butter Chicken -> Grilled Chicken Salad
  '6': '4',  // Chole Bhature -> Dal Makhani
  '7': '5',  // Egg Biryani -> Tandoori Prawns
  '8': '1',  // Greek Yogurt Parfait -> Grilled Chicken Salad
  '3': '5',  // Paneer Tikka -> Tandoori Prawns
  '4': '3',  // Dal Makhani -> Paneer Tikka
};

export function getSwapSuggestion(
  dish: Dish,
  goal: GoalType,
  context: ContextType,
  allDishes: Dish[],
  getScore: (dish: Dish, goal: GoalType, context: ContextType) => number
): SwapSuggestion | null {
  const suggestedDishId = swapMappings[dish.id];
  const suggestedDish = suggestedDishId 
    ? allDishes.find(d => d.id === suggestedDishId)
    : findBestSwap(dish, goal, context, allDishes, getScore);

  if (!suggestedDish || suggestedDish.id === dish.id) return null;

  const originalScore = getScore(dish, goal, context);
  const suggestedScore = getScore(suggestedDish, goal, context);
  const delta = suggestedScore - originalScore;

  if (delta < 5) return null;

  const reason = getSwapReason(dish, suggestedDish, goal, context);

  return {
    originalDish: dish,
    suggestedDish,
    scoreDelta: delta,
    reason,
  };
}

function findBestSwap(
  dish: Dish,
  goal: GoalType,
  context: ContextType,
  allDishes: Dish[],
  getScore: (dish: Dish, goal: GoalType, context: ContextType) => number
): Dish | null {
  const currentScore = getScore(dish, goal, context);
  
  let bestDish: Dish | null = null;
  let bestScore = currentScore + 5;

  for (const d of allDishes) {
    if (d.id === dish.id) continue;
    const score = getScore(d, goal, context);
    if (score > bestScore) {
      bestScore = score;
      bestDish = d;
    }
  }

  return bestDish;
}

function getSwapReason(dish: Dish, suggested: Dish, goal: GoalType, context: ContextType): string {
  if (context === 'post-workout') {
    if (suggested.protein > dish.protein) return 'Higher protein for recovery';
    if (!suggested.isFried && dish.isFried) return 'Easier digestion post-workout';
    return 'Better aligned with recovery needs';
  }
  
  if (context === 'late-night') {
    if (suggested.calories < dish.calories) return 'Lighter option for late night';
    if (suggested.fat < dish.fat) return 'Lower fat for easier digestion';
    if (!suggested.isFried && dish.isFried) return 'Non-fried for better sleep';
    return 'More suitable for evening';
  }
  
  if (context === 'office-lunch') {
    if (suggested.calories >= 350 && suggested.calories <= 550) return 'Balanced energy for afternoon';
    if (!suggested.isFried && dish.isFried) return 'Less likely to cause sluggishness';
    return 'More balanced for work hours';
  }

  // Default reasons by goal
  switch (goal) {
    case 'cutting':
      if (suggested.calories < dish.calories) return 'Lower calorie option';
      if (suggested.protein > dish.protein) return 'Higher protein density';
      return 'Better macro profile for cutting';
    case 'bulking':
      if (suggested.protein > dish.protein) return 'Higher protein content';
      if (suggested.calories > dish.calories) return 'More calorie-dense';
      return 'Better suited for mass gain';
    case 'performance':
      if (suggested.carbs > dish.carbs && !suggested.isFried) return 'Better carb source for energy';
      return 'More performance-friendly';
    case 'lowgi':
      if (suggested.carbs < dish.carbs) return 'Lower carb content';
      return 'More stable glucose impact';
    case 'recovery':
      if (suggested.protein > dish.protein) return 'Higher protein aids repair';
      return 'Better recovery profile';
    default:
      return 'Better option for your goal';
  }
}

