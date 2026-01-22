'use client';

import { Dish } from '../data/dishes';

interface DishCardProps {
  dish: Dish;
  onClick: () => void;
  index: number;
}

export default function DishCard({ dish, onClick, index }: DishCardProps) {
  return (
    <div
      onClick={onClick}
      className="group relative bg-gray-900/80 border border-gray-800 rounded-2xl overflow-hidden cursor-pointer hover:border-gray-700 hover:shadow-xl hover:shadow-black/20 transition-all duration-300 animate-slideUp"
      style={{ animationDelay: `${index * 50}ms` }}
    >
      {/* Food Image */}
      <div className="relative h-40 overflow-hidden bg-gray-800">
        <img 
          src={dish.imageUrl} 
          alt={dish.name}
          className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500"
        />
        
        {/* Gradient Overlay for text readability */}
        <div className="absolute inset-0 bg-gradient-to-t from-black/60 via-transparent to-transparent" />

        {/* Hover Overlay */}
        <div className="absolute inset-0 bg-black/0 group-hover:bg-black/20 transition-colors duration-300 flex items-center justify-center">
          <span className="opacity-0 group-hover:opacity-100 transition-opacity duration-300 bg-white/20 backdrop-blur-sm px-4 py-2 rounded-full text-sm font-medium text-white">
            View Details
          </span>
        </div>

        {/* Bestseller Badge */}
        {dish.id === '2' || dish.id === '7' ? (
          <div className="absolute top-3 left-3 px-2 py-1 bg-[#E23744] text-white text-xs font-medium rounded">
            BESTSELLER
          </div>
        ) : null}

        {/* Veg/Non-Veg Indicator */}
        <div className="absolute top-3 right-3">
          <div className={`w-5 h-5 border-2 ${
            dish.isVeg ? 'border-green-500' : 'border-red-500'
          } rounded-sm flex items-center justify-center bg-gray-900/80`}>
            <div className={`w-2 h-2 rounded-full ${
              dish.isVeg ? 'bg-green-500' : 'bg-red-500'
            }`} />
          </div>
        </div>
      </div>

      {/* Content */}
      <div className="p-4">
        {/* Title & Price */}
        <div className="flex items-start justify-between gap-2 mb-2">
          <h3 className="font-semibold text-white group-hover:text-[#E23744] transition-colors line-clamp-1">
            {dish.name}
          </h3>
          <span className="text-white font-semibold whitespace-nowrap">
            ₹{dish.price}
          </span>
        </div>

        {/* Description */}
        <p className="text-sm text-gray-500 line-clamp-2 mb-3">
          {dish.description}
        </p>

        {/* Macro Badges */}
        <div className="flex items-center gap-2 flex-wrap">
          <span className="inline-flex items-center gap-1 px-2 py-1 bg-orange-500/10 border border-orange-500/20 rounded-full text-xs text-orange-400">
            <svg className="w-3 h-3" fill="currentColor" viewBox="0 0 20 20">
              <path fillRule="evenodd" d="M12.395 2.553a1 1 0 00-1.45-.385c-.345.23-.614.558-.822.88-.214.33-.403.713-.57 1.116-.334.804-.614 1.768-.84 2.734a31.365 31.365 0 00-.613 3.58 2.64 2.64 0 01-.945-1.067c-.328-.68-.398-1.534-.398-2.654A1 1 0 005.05 6.05 6.981 6.981 0 003 11a7 7 0 1011.95-4.95c-.592-.591-.98-.985-1.348-1.467-.363-.476-.724-1.063-1.207-2.03zM12.12 15.12A3 3 0 017 13s.879.5 2.5.5c0-1 .5-4 1.25-4.5.5 1 .786 1.293 1.371 1.879A2.99 2.99 0 0113 13a2.99 2.99 0 01-.879 2.121z" clipRule="evenodd" />
            </svg>
            {dish.calories} kcal
          </span>
          <span className="inline-flex items-center gap-1 px-2 py-1 bg-blue-500/10 border border-blue-500/20 rounded-full text-xs text-blue-400">
            <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
            </svg>
            {dish.protein}g protein
          </span>
        </div>
      </div>

      {/* Add Button */}
      <div className="absolute bottom-4 right-4">
        <button 
          className="w-8 h-8 rounded-lg bg-[#E23744] hover:bg-[#c42f3a] flex items-center justify-center text-white shadow-lg shadow-[#E23744]/30 transition-all hover:scale-110"
          onClick={(e) => {
            e.stopPropagation();
            // Visual only - no cart functionality
          }}
        >
          <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
          </svg>
        </button>
      </div>
    </div>
  );
}

