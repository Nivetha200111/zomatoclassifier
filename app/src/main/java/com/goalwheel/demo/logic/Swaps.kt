package com.goalwheel.demo.logic

import com.goalwheel.demo.data.Dish

data class SwapSuggestion(
    val originalDish: Dish,
    val suggestedDish: Dish,
    val scoreDelta: Int,
    val reason: String
)

object Swaps {
    
    // Swap mappings: original dish ID -> suggested dish ID
    private val swapMappings = mapOf(
        "2" to "1",  // Butter Chicken -> Grilled Chicken Salad (creamy -> grilled)
        "6" to "4",  // Chole Bhature -> Dal Makhani (fried -> non-fried)
        "7" to "5",  // Egg Biryani -> Tandoori Prawns (rice heavy -> lean protein)
        "8" to "1",  // Greek Yogurt Parfait -> Grilled Chicken Salad (sugary -> savory)
        "3" to "5",  // Paneer Tikka -> Tandoori Prawns (moderate -> high protein)
        "4" to "3",  // Dal Makhani -> Paneer Tikka (high carb -> lower carb)
    )
    
    fun getSwapSuggestion(
        dish: Dish,
        goal: Goal,
        context: ContextMode,
        allDishes: List<Dish>
    ): SwapSuggestion? {
        // Get mapped swap or find best alternative
        val suggestedDishId = swapMappings[dish.id]
        val suggestedDish = suggestedDishId?.let { id -> 
            allDishes.find { it.id == id } 
        } ?: findBestSwap(dish, goal, context, allDishes)
        
        if (suggestedDish == null || suggestedDish.id == dish.id) return null
        
        val originalScore = Scoring.scoreDish(dish, goal, context)
        val suggestedScore = Scoring.scoreDish(suggestedDish, goal, context)
        val delta = suggestedScore - originalScore
        
        // Only suggest if improvement is significant
        if (delta < 5) return null
        
        // Validate swap makes sense for context
        if (!isSwapValidForContext(dish, suggestedDish, context)) return null
        
        val reason = getSwapReason(dish, suggestedDish, goal, context)
        
        return SwapSuggestion(
            originalDish = dish,
            suggestedDish = suggestedDish,
            scoreDelta = delta,
            reason = reason
        )
    }
    
    private fun findBestSwap(
        dish: Dish,
        goal: Goal,
        context: ContextMode,
        allDishes: List<Dish>
    ): Dish? {
        val currentScore = Scoring.scoreDish(dish, goal, context)
        
        return allDishes
            .filter { it.id != dish.id }
            .map { it to Scoring.scoreDish(it, goal, context) }
            .filter { (_, score) -> score > currentScore + 5 }
            .maxByOrNull { (_, score) -> score }
            ?.first
    }
    
    private fun isSwapValidForContext(
        original: Dish,
        suggested: Dish,
        context: ContextMode
    ): Boolean {
        return when (context) {
            ContextMode.NONE -> true
            ContextMode.POST_WORKOUT -> {
                // Prefer higher protein swaps
                suggested.protein >= original.protein - 5
            }
            ContextMode.LATE_NIGHT -> {
                // Prefer lighter swaps
                suggested.calories <= original.calories + 50 &&
                suggested.fat <= original.fat + 5
            }
            ContextMode.OFFICE_LUNCH -> {
                // Prefer balanced, non-fried swaps
                !suggested.isFried || !original.isFried
            }
        }
    }
    
    private fun getSwapReason(
        original: Dish,
        suggested: Dish,
        goal: Goal,
        context: ContextMode
    ): String {
        // Context-specific reasons
        val contextReason = when (context) {
            ContextMode.POST_WORKOUT -> when {
                suggested.protein > original.protein -> "Higher protein for recovery"
                !suggested.isFried && original.isFried -> "Easier digestion post-workout"
                else -> "Better aligned with recovery needs"
            }
            ContextMode.LATE_NIGHT -> when {
                suggested.calories < original.calories -> "Lighter option for late night"
                suggested.fat < original.fat -> "Lower fat for easier digestion"
                !suggested.isFried && original.isFried -> "Non-fried for better sleep"
                else -> "More suitable for evening"
            }
            ContextMode.OFFICE_LUNCH -> when {
                suggested.calories in 350..550 -> "Balanced energy for afternoon"
                !suggested.isFried && original.isFried -> "Less likely to cause sluggishness"
                suggested.fiber > original.fiber -> "Better sustained satiety"
                else -> "More balanced for work hours"
            }
            ContextMode.NONE -> when (goal) {
                Goal.Cutting -> when {
                    suggested.calories < original.calories -> "Lower calorie option"
                    suggested.protein > original.protein -> "Higher protein density"
                    else -> "Better macro profile for cutting"
                }
                Goal.Bulking -> when {
                    suggested.protein > original.protein -> "Higher protein content"
                    suggested.calories > original.calories -> "More calorie-dense"
                    else -> "Better suited for mass gain"
                }
                Goal.Performance -> when {
                    suggested.carbs > original.carbs && !suggested.isFried -> "Better carb source for energy"
                    !suggested.isFried && original.isFried -> "Cleaner fuel for performance"
                    else -> "More performance-friendly"
                }
                Goal.LowGI -> when {
                    suggested.carbs < original.carbs -> "Lower carb content"
                    suggested.fiber > original.fiber -> "Higher fiber slows absorption"
                    !suggested.isSugary && original.isSugary -> "No added sugars"
                    else -> "More stable glucose impact"
                }
                Goal.Recovery -> when {
                    suggested.protein > original.protein -> "Higher protein aids repair"
                    !suggested.isFried && original.isFried -> "Lighter on digestion"
                    else -> "Better recovery profile"
                }
            }
        }
        
        return contextReason
    }
}

