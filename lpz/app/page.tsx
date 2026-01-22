'use client';

import { useState } from 'react';
import RestaurantHeader from './components/RestaurantHeader';
import FilterBar from './components/FilterBar';
import DishCard from './components/DishCard';
import DishModal from './components/DishModal';
import { dishes, Dish } from './data/dishes';

export default function Home() {
  const [selectedDish, setSelectedDish] = useState<Dish | null>(null);

  return (
    <main className="min-h-screen">
      {/* Restaurant Header */}
      <RestaurantHeader />

      {/* Filter Bar */}
      <FilterBar />

      {/* Menu Section */}
      <section className="max-w-6xl mx-auto px-4 py-8">
        {/* Section Header */}
        <div className="flex items-center justify-between mb-6">
          <div>
            <h2 className="text-xl font-bold text-white mb-1">Menu</h2>
            <p className="text-sm text-gray-500">{dishes.length} items</p>
          </div>
          
          {/* Sort Dropdown (Visual Only) */}
          <button className="flex items-center gap-2 px-4 py-2 bg-gray-800/50 border border-gray-700 rounded-lg text-sm text-gray-300 hover:bg-gray-800 transition-colors">
            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 4h13M3 8h9m-9 4h6m4 0l4-4m0 0l4 4m-4-4v12" />
            </svg>
            <span>Relevance</span>
            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
            </svg>
          </button>
        </div>

        {/* Concept Banner */}
        <div className="mb-8 p-4 bg-gradient-to-r from-[#E23744]/10 via-purple-500/10 to-blue-500/10 border border-gray-800 rounded-2xl">
          <div className="flex items-start gap-4">
            <div className="flex-shrink-0 w-12 h-12 rounded-xl bg-gradient-to-br from-[#E23744] to-purple-600 flex items-center justify-center">
              <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
              </svg>
            </div>
            <div>
              <h3 className="font-semibold text-white mb-1">Goal Wheel Concept</h3>
              <p className="text-sm text-gray-400 leading-relaxed">
                Click any dish to see how its meaning changes based on your fitness goal. 
                The same food can be a great choice or a trade-off — it depends on context.
              </p>
            </div>
          </div>
        </div>

        {/* Dish Grid */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-5">
          {dishes.map((dish, index) => (
            <DishCard
              key={dish.id}
              dish={dish}
              index={index}
              onClick={() => setSelectedDish(dish)}
            />
          ))}
        </div>

        {/* Footer Info */}
        <div className="mt-12 text-center">
          <p className="text-xs text-gray-600">
            This is a UI concept demo. No real orders will be placed.
          </p>
          <p className="text-xs text-gray-700 mt-1">
            Goal interpretations are illustrative and not nutritional advice.
          </p>
        </div>
      </section>

      {/* Dish Modal */}
      {selectedDish && (
        <DishModal
          dish={selectedDish}
          onClose={() => setSelectedDish(null)}
        />
      )}
    </main>
  );
}
