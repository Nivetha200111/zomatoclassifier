'use client';

import { contexts, ContextType } from '../data/dishes';

interface ContextToggleRowProps {
  currentContext: ContextType;
  onContextChange: (context: ContextType) => void;
}

export default function ContextToggleRow({ currentContext, onContextChange }: ContextToggleRowProps) {
  return (
    <div className="mb-6">
      <p className="text-xs text-gray-500 uppercase tracking-wider mb-3">Context</p>
      <div className="flex gap-2">
        {contexts.map((context) => {
          const isActive = currentContext === context.id;
          return (
            <button
              key={context.id}
              onClick={() => onContextChange(isActive ? 'none' : context.id)}
              className={`
                flex-1 flex items-center justify-center gap-2 px-3 py-2.5 rounded-xl
                border transition-all duration-200
                ${isActive 
                  ? 'border-opacity-50 scale-[1.02]' 
                  : 'border-gray-700 hover:border-gray-600 bg-gray-800/30'
                }
              `}
              style={{
                backgroundColor: isActive ? `${context.color}20` : undefined,
                borderColor: isActive ? `${context.color}50` : undefined,
              }}
            >
              <span className="text-sm">{context.icon}</span>
              <span 
                className={`text-xs font-medium ${isActive ? '' : 'text-gray-400'}`}
                style={{ color: isActive ? context.color : undefined }}
              >
                {context.label}
              </span>
            </button>
          );
        })}
      </div>
    </div>
  );
}

