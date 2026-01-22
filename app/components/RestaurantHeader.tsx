'use client';

export default function RestaurantHeader() {
  return (
    <header className="relative overflow-hidden">
      {/* Background gradient */}
      <div className="absolute inset-0 bg-gradient-to-r from-gray-900 via-gray-800 to-gray-900" />
      <div className="absolute inset-0 bg-[radial-gradient(ellipse_at_top,rgba(226,55,68,0.15),transparent_50%)]" />
      
      <div className="relative max-w-6xl mx-auto px-4 py-6">
        <div className="flex items-start justify-between">
          {/* Restaurant Info */}
          <div className="flex-1">
            <div className="flex items-center gap-3 mb-2">
              <h1 className="text-2xl md:text-3xl font-bold text-white">
                The Spice Kitchen
              </h1>
              <span className="px-2 py-0.5 bg-green-500/20 text-green-400 text-xs font-medium rounded">
                PROMOTED
              </span>
            </div>
            
            <p className="text-gray-400 text-sm mb-3">
              North Indian, Mughlai, Biryani • Connaught Place
            </p>
            
            <div className="flex flex-wrap items-center gap-4 text-sm">
              {/* Rating */}
              <div className="flex items-center gap-1.5 bg-green-600 px-2.5 py-1 rounded-md">
                <svg className="w-4 h-4 text-white" fill="currentColor" viewBox="0 0 20 20">
                  <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                </svg>
                <span className="font-semibold text-white">4.2</span>
              </div>
              
              {/* Delivery Time */}
              <div className="flex items-center gap-1.5 text-gray-300">
                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                <span>25-30 min</span>
              </div>
              
              {/* Price Range */}
              <div className="flex items-center gap-1.5 text-gray-300">
                <span>₹350 for two</span>
              </div>
            </div>
          </div>
          
          {/* Quick Actions */}
          <div className="hidden md:flex items-center gap-3">
            <button className="flex items-center gap-2 px-4 py-2 bg-gray-800/80 hover:bg-gray-700 border border-gray-700 rounded-lg transition-colors">
              <svg className="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
              </svg>
              <span className="text-sm text-gray-300">Save</span>
            </button>
            
            <button className="flex items-center gap-2 px-4 py-2 bg-gray-800/80 hover:bg-gray-700 border border-gray-700 rounded-lg transition-colors">
              <svg className="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.368 2.684 3 3 0 00-5.368-2.684z" />
              </svg>
              <span className="text-sm text-gray-300">Share</span>
            </button>
          </div>
        </div>
        
        {/* Offers Banner */}
        <div className="mt-4 flex items-center gap-2 text-sm">
          <span className="text-[#E23744]">
            <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
              <path fillRule="evenodd" d="M5 5a3 3 0 015-2.236A3 3 0 0114.83 6H16a2 2 0 110 4h-5V9a1 1 0 10-2 0v1H4a2 2 0 110-4h1.17C5.06 5.687 5 5.35 5 5zm4 1V5a1 1 0 10-1 1h1zm3 0a1 1 0 10-1-1v1h1z" clipRule="evenodd" />
              <path d="M9 11H3v5a2 2 0 002 2h4v-7zM11 18h4a2 2 0 002-2v-5h-6v7z" />
            </svg>
          </span>
          <span className="text-gray-300">50% off up to ₹100 | Use code WELCOME50</span>
        </div>
      </div>
    </header>
  );
}

