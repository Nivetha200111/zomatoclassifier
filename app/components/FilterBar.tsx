'use client';

import { useState } from 'react';

export default function FilterBar() {
  const [calories, setCalories] = useState(800);
  const [protein, setProtein] = useState(50);

  return (
    <div className="bg-gray-900/60 backdrop-blur-sm border-y border-gray-800 sticky top-0 z-10">
      <div className="max-w-6xl mx-auto px-4 py-4">
        <div className="flex flex-col md:flex-row md:items-center gap-6">
          {/* Healthy Mode Toggle */}
          <div className="flex items-center gap-3">
            <div className="flex items-center gap-2 px-3 py-1.5 bg-green-500/10 border border-green-500/30 rounded-full">
              <span className="text-green-400 text-sm">🥗</span>
              <span className="text-green-400 text-sm font-medium">Healthy Mode</span>
              <div className="w-8 h-4 bg-green-500 rounded-full relative">
                <div className="absolute right-0.5 top-0.5 w-3 h-3 bg-white rounded-full shadow" />
              </div>
            </div>
          </div>

          {/* Sliders */}
          <div className="flex-1 flex flex-col md:flex-row gap-6">
            {/* Calories Slider */}
            <div className="flex-1">
              <div className="flex items-center justify-between mb-2">
                <label className="text-xs text-gray-400 uppercase tracking-wide">
                  Calories
                </label>
                <span className="text-sm font-medium text-white">
                  Up to {calories} kcal
                </span>
              </div>
              <input
                type="range"
                min={100}
                max={1500}
                value={calories}
                onChange={(e) => setCalories(Number(e.target.value))}
                className="w-full h-1.5"
              />
              <div className="flex justify-between mt-1">
                <span className="text-xs text-gray-500">100</span>
                <span className="text-xs text-gray-500">1500</span>
              </div>
            </div>

            {/* Protein Slider */}
            <div className="flex-1">
              <div className="flex items-center justify-between mb-2">
                <label className="text-xs text-gray-400 uppercase tracking-wide">
                  Protein
                </label>
                <span className="text-sm font-medium text-white">
                  Min {protein}g
                </span>
              </div>
              <input
                type="range"
                min={0}
                max={100}
                value={protein}
                onChange={(e) => setProtein(Number(e.target.value))}
                className="w-full h-1.5"
              />
              <div className="flex justify-between mt-1">
                <span className="text-xs text-gray-500">0g</span>
                <span className="text-xs text-gray-500">100g</span>
              </div>
            </div>
          </div>

          {/* Filter Tags */}
          <div className="flex items-center gap-2 flex-wrap">
            <span className="px-3 py-1.5 bg-gray-800 border border-gray-700 rounded-full text-sm text-gray-300 cursor-pointer hover:border-gray-600 transition-colors">
              Veg
            </span>
            <span className="px-3 py-1.5 bg-gray-800 border border-gray-700 rounded-full text-sm text-gray-300 cursor-pointer hover:border-gray-600 transition-colors">
              Non-Veg
            </span>
            <span className="px-3 py-1.5 bg-[#E23744]/10 border border-[#E23744]/30 rounded-full text-sm text-[#E23744] cursor-pointer">
              Bestseller
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}

