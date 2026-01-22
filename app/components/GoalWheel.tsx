'use client';

import { goals, GoalType } from '../data/dishes';

interface GoalWheelProps {
  activeGoal: GoalType;
  onSelectGoal: (goal: GoalType) => void;
  onClose: () => void;
}

export default function GoalWheel({ activeGoal, onSelectGoal, onClose }: GoalWheelProps) {
  const handleSelect = (goalId: GoalType) => {
    onSelectGoal(goalId);
    setTimeout(onClose, 400);
  };

  const size = 340;
  const center = size / 2;
  const outerRadius = 150;
  const innerRadius = 65;
  const segmentAngle = 360 / goals.length;

  // Create SVG arc path for a segment
  const createSegmentPath = (index: number) => {
    const startAngle = (index * segmentAngle - 90) * (Math.PI / 180);
    const endAngle = ((index + 1) * segmentAngle - 90) * (Math.PI / 180);
    
    const x1 = center + outerRadius * Math.cos(startAngle);
    const y1 = center + outerRadius * Math.sin(startAngle);
    const x2 = center + outerRadius * Math.cos(endAngle);
    const y2 = center + outerRadius * Math.sin(endAngle);
    const x3 = center + innerRadius * Math.cos(endAngle);
    const y3 = center + innerRadius * Math.sin(endAngle);
    const x4 = center + innerRadius * Math.cos(startAngle);
    const y4 = center + innerRadius * Math.sin(startAngle);

    const largeArc = segmentAngle > 180 ? 1 : 0;

    return `
      M ${x1} ${y1}
      A ${outerRadius} ${outerRadius} 0 ${largeArc} 1 ${x2} ${y2}
      L ${x3} ${y3}
      A ${innerRadius} ${innerRadius} 0 ${largeArc} 0 ${x4} ${y4}
      Z
    `;
  };

  // Get position for icon/label
  const getSegmentCenter = (index: number) => {
    const midAngle = ((index + 0.5) * segmentAngle - 90) * (Math.PI / 180);
    const labelRadius = (outerRadius + innerRadius) / 2;
    return {
      x: center + labelRadius * Math.cos(midAngle),
      y: center + labelRadius * Math.sin(midAngle),
    };
  };

  return (
    <div 
      className="fixed inset-0 z-50 flex items-center justify-center animate-fadeIn"
      onClick={onClose}
    >
      {/* Backdrop with radial gradient */}
      <div className="absolute inset-0 bg-black/85 backdrop-blur-lg" />
      <div 
        className="absolute inset-0 opacity-30"
        style={{
          background: `radial-gradient(circle at center, ${goals.find(g => g.id === activeGoal)?.color}30 0%, transparent 50%)`
        }}
      />
      
      {/* Wheel Container */}
      <div 
        className="relative animate-scaleIn"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Main SVG Wheel */}
        <svg 
          width={size} 
          height={size} 
          viewBox={`0 0 ${size} ${size}`}
          className="drop-shadow-2xl"
        >
          {/* Outer glow ring */}
          <circle
            cx={center}
            cy={center}
            r={outerRadius + 8}
            fill="none"
            stroke="rgba(255,255,255,0.05)"
            strokeWidth="2"
          />
          
          {/* Segments */}
          {goals.map((goal, index) => {
            const isActive = activeGoal === goal.id;
            const pos = getSegmentCenter(index);
            
            return (
              <g key={goal.id} className="cursor-pointer" onClick={() => handleSelect(goal.id)}>
                {/* Segment background */}
                <path
                  d={createSegmentPath(index)}
                  fill={isActive ? goal.color : `${goal.color}25`}
                  stroke={isActive ? goal.color : 'rgba(255,255,255,0.1)'}
                  strokeWidth={isActive ? 3 : 1}
                  className="transition-all duration-300 ease-out"
                  style={{
                    filter: isActive ? `drop-shadow(0 0 20px ${goal.color}80)` : 'none',
                  }}
                />
                
                {/* Hover overlay */}
                <path
                  d={createSegmentPath(index)}
                  fill="transparent"
                  className="hover:fill-white/10 transition-colors duration-200"
                />
                
                {/* Icon */}
                <text
                  x={pos.x}
                  y={pos.y - 8}
                  textAnchor="middle"
                  dominantBaseline="middle"
                  className="text-2xl pointer-events-none select-none"
                  style={{ 
                    fontSize: '28px',
                    filter: isActive ? 'drop-shadow(0 2px 4px rgba(0,0,0,0.5))' : 'none'
                  }}
                >
                  {goal.icon}
                </text>
                
                {/* Label */}
                <text
                  x={pos.x}
                  y={pos.y + 18}
                  textAnchor="middle"
                  dominantBaseline="middle"
                  fill={isActive ? 'white' : 'rgba(255,255,255,0.7)'}
                  className="text-xs font-semibold pointer-events-none select-none tracking-wide"
                  style={{ 
                    fontSize: '11px',
                    textTransform: 'uppercase',
                    letterSpacing: '0.05em'
                  }}
                >
                  {goal.label}
                </text>
              </g>
            );
          })}
          
          {/* Inner circle (hub) background */}
          <circle
            cx={center}
            cy={center}
            r={innerRadius}
            fill="#0f0f14"
            stroke="rgba(255,255,255,0.1)"
            strokeWidth="2"
          />
          
          {/* Inner decorative ring */}
          <circle
            cx={center}
            cy={center}
            r={innerRadius - 8}
            fill="none"
            stroke="rgba(255,255,255,0.05)"
            strokeWidth="1"
          />
        </svg>

        {/* Center hub content */}
        <div 
          className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 flex flex-col items-center justify-center pointer-events-none"
          style={{ width: innerRadius * 2 - 20, height: innerRadius * 2 - 20 }}
        >
          <p className="text-[10px] text-gray-500 uppercase tracking-widest mb-1">Select</p>
          <p className="text-lg font-bold text-white">Goal</p>
          <div 
            className="w-8 h-1 rounded-full mt-2"
            style={{ backgroundColor: goals.find(g => g.id === activeGoal)?.color }}
          />
        </div>

        {/* Close Button */}
        <button
          onClick={onClose}
          className="absolute -top-3 -right-3 w-10 h-10 rounded-full bg-gray-900 border border-gray-700 flex items-center justify-center text-gray-400 hover:text-white hover:bg-gray-800 hover:border-gray-600 transition-all shadow-lg"
        >
          <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>

        {/* Active goal indicator */}
        <div className="absolute -bottom-16 left-1/2 -translate-x-1/2 flex flex-col items-center gap-2">
          <div 
            className="flex items-center gap-2 px-4 py-2 rounded-full"
            style={{ 
              backgroundColor: `${goals.find(g => g.id === activeGoal)?.color}20`,
              border: `1px solid ${goals.find(g => g.id === activeGoal)?.color}40`
            }}
          >
            <span className="text-lg">{goals.find(g => g.id === activeGoal)?.icon}</span>
            <span 
              className="text-sm font-medium"
              style={{ color: goals.find(g => g.id === activeGoal)?.color }}
            >
              {goals.find(g => g.id === activeGoal)?.label}
            </span>
          </div>
          <p className="text-xs text-gray-600">Tap a segment to change</p>
        </div>
      </div>
    </div>
  );
}
