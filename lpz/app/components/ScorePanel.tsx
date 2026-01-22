'use client';

import { goals, GoalType, GoalData, ContextType, contexts } from '../data/dishes';

interface ScorePanelProps {
  goalData: GoalData;
  activeGoal: GoalType;
  activeContext?: ContextType;
  interpolatedScore?: number;
  isAnimating?: boolean;
}

export default function ScorePanel({ 
  goalData, 
  activeGoal,
  activeContext = 'none',
  interpolatedScore,
  isAnimating = false 
}: ScorePanelProps) {
  const goal = goals.find(g => g.id === activeGoal)!;
  const context = contexts.find(c => c.id === activeContext);
  const displayScore = interpolatedScore ?? goalData.score;
  const { explanation } = goalData;

  const getScoreColor = (score: number) => {
    if (score >= 80) return 'text-green-400';
    if (score >= 60) return 'text-yellow-400';
    if (score >= 40) return 'text-orange-400';
    return 'text-red-400';
  };

  const getScoreBackground = (score: number) => {
    if (score >= 80) return 'from-green-500/20 to-green-500/5';
    if (score >= 60) return 'from-yellow-500/20 to-yellow-500/5';
    if (score >= 40) return 'from-orange-500/20 to-orange-500/5';
    return 'from-red-500/20 to-red-500/5';
  };

  const getScoreLabel = (score: number) => {
    if (score >= 80) return 'Great fit';
    if (score >= 60) return 'Good option';
    if (score >= 40) return 'Moderate';
    return 'Consider alternatives';
  };

  const circumference = 2 * Math.PI * 45;
  const strokeDasharray = circumference;
  const strokeDashoffset = circumference - (displayScore / 100) * circumference;

  return (
    <div className={`bg-gradient-to-b ${getScoreBackground(displayScore)} rounded-2xl p-5 border border-gray-800 transition-all duration-300 ${isAnimating ? 'ring-2 ring-white/10' : ''}`}>
      <div className="flex items-start gap-5">
        {/* Circular Score */}
        <div className="relative flex-shrink-0">
          <svg className="w-28 h-28 transform -rotate-90">
            <circle
              cx="56"
              cy="56"
              r="45"
              stroke="currentColor"
              strokeWidth="8"
              fill="none"
              className="text-gray-800"
            />
            <circle
              cx="56"
              cy="56"
              r="45"
              stroke={goal.color}
              strokeWidth="8"
              fill="none"
              strokeLinecap="round"
              style={{
                strokeDasharray,
                strokeDashoffset,
                transition: isAnimating ? 'stroke-dashoffset 0.1s ease-out' : 'stroke-dashoffset 0.6s ease-out',
              }}
            />
          </svg>
          <div className="absolute inset-0 flex flex-col items-center justify-center">
            <span className={`text-3xl font-bold tabular-nums transition-colors duration-200 ${getScoreColor(displayScore)}`}>
              {displayScore}
            </span>
            <span className="text-xs text-gray-500">/100</span>
          </div>
          
          {isAnimating && (
            <div 
              className="absolute -inset-1 rounded-full animate-pulse opacity-30"
              style={{ 
                background: `radial-gradient(circle, ${goal.color}40, transparent 70%)`
              }}
            />
          )}
        </div>

        {/* Score Info */}
        <div className="flex-1 min-w-0">
          {/* Goal + Context Badges */}
          <div className="flex flex-wrap items-center gap-2 mb-3">
            <div 
              className="inline-flex items-center gap-2 px-3 py-1.5 rounded-full"
              style={{ 
                backgroundColor: `${goal.color}20`,
                border: `1px solid ${goal.color}40`
              }}
            >
              <span className="text-lg">{goal.icon}</span>
              <span className="text-sm font-medium" style={{ color: goal.color }}>
                {goal.label}
              </span>
            </div>

            {activeContext !== 'none' && context && (
              <div 
                className="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full animate-fadeIn"
                style={{ 
                  backgroundColor: `${context.color}20`,
                  border: `1px solid ${context.color}40`
                }}
              >
                <span className="text-sm">{context.icon}</span>
                <span className="text-xs font-medium" style={{ color: context.color }}>
                  {context.label}
                </span>
              </div>
            )}
          </div>

          {/* Score Label */}
          <p className={`text-sm font-medium mb-2 transition-colors duration-200 ${getScoreColor(displayScore)}`}>
            {getScoreLabel(displayScore)}
          </p>

          {/* Explanation */}
          <p className={`text-sm text-gray-400 leading-relaxed transition-opacity duration-300 ${isAnimating ? 'opacity-70' : 'opacity-100'}`}>
            {explanation}
          </p>
        </div>
      </div>

      {/* Disclaimer */}
      <div className="mt-4 pt-4 border-t border-gray-800/50">
        <p className="text-xs text-gray-600 italic">
          Scores reflect general alignment with the selected goal. Individual needs vary.
        </p>
      </div>
    </div>
  );
}
