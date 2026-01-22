package com.goalwheel.demo.logic

import com.goalwheel.demo.data.Dish

object Scoring {
    
    fun scoreDish(dish: Dish, goal: Goal): Int {
        return when (goal) {
            Goal.Cutting -> scoreCutting(dish)
            Goal.Bulking -> scoreBulking(dish)
            Goal.Performance -> scorePerformance(dish)
            Goal.LowGI -> scoreLowGI(dish)
            Goal.Recovery -> scoreRecovery(dish)
        }
    }
    
    private fun scoreCutting(dish: Dish): Int {
        var score = 50
        
        // Favor high protein-to-calorie ratio
        val proteinRatio = dish.protein.toFloat() / dish.calories * 100
        score += when {
            proteinRatio > 10 -> 30
            proteinRatio > 7 -> 20
            proteinRatio > 4 -> 10
            else -> -10
        }
        
        // Penalize high calories
        score += when {
            dish.calories < 350 -> 20
            dish.calories < 500 -> 10
            dish.calories > 700 -> -25
            dish.calories > 550 -> -10
            else -> 0
        }
        
        // Penalize fried foods
        if (dish.isFried) score -= 15
        
        // Favor fiber
        if (dish.fiber > 5) score += 10
        
        return score.coerceIn(0, 100)
    }
    
    private fun scoreBulking(dish: Dish): Int {
        var score = 50
        
        // Favor high calories
        score += when {
            dish.calories > 700 -> 25
            dish.calories > 500 -> 15
            dish.calories < 300 -> -15
            else -> 5
        }
        
        // Favor high protein
        score += when {
            dish.protein > 35 -> 25
            dish.protein > 25 -> 15
            dish.protein > 15 -> 5
            else -> -10
        }
        
        // Balanced carbs help
        if (dish.carbs > 40) score += 10
        
        return score.coerceIn(0, 100)
    }
    
    private fun scorePerformance(dish: Dish): Int {
        var score = 50
        
        // Favor moderate-high carbs for glycogen
        score += when {
            dish.carbs in 40..80 -> 20
            dish.carbs > 80 -> 10
            dish.carbs < 20 -> -15
            else -> 5
        }
        
        // Good protein matters
        score += when {
            dish.protein > 25 -> 15
            dish.protein > 15 -> 10
            else -> 0
        }
        
        // Penalize very heavy/fried foods
        if (dish.isFried) score -= 20
        if (dish.fat > 40) score -= 10
        
        // Moderate calories preferred
        if (dish.calories in 400..600) score += 10
        
        return score.coerceIn(0, 100)
    }
    
    private fun scoreLowGI(dish: Dish): Int {
        var score = 50
        
        // Penalize high carbs
        score += when {
            dish.carbs < 15 -> 30
            dish.carbs < 30 -> 15
            dish.carbs > 70 -> -25
            dish.carbs > 50 -> -15
            else -> 0
        }
        
        // Favor fiber (slows absorption)
        score += when {
            dish.fiber > 8 -> 15
            dish.fiber > 4 -> 10
            else -> 0
        }
        
        // Penalize sugary foods
        if (dish.isSugary) score -= 20
        
        // Protein and fat slow glucose rise
        if (dish.protein > 20 && dish.fat > 10) score += 10
        
        return score.coerceIn(0, 100)
    }
    
    private fun scoreRecovery(dish: Dish): Int {
        var score = 50
        
        // High protein is key
        score += when {
            dish.protein > 35 -> 30
            dish.protein > 25 -> 20
            dish.protein > 15 -> 10
            else -> -10
        }
        
        // Some carbs for glycogen replenishment
        score += when {
            dish.carbs in 30..60 -> 15
            dish.carbs > 60 -> 5
            dish.carbs < 15 -> -5
            else -> 10
        }
        
        // Not too heavy
        if (dish.calories > 750) score -= 10
        if (dish.isFried) score -= 10
        
        return score.coerceIn(0, 100)
    }
    
    fun getScoreLabel(score: Int): String {
        return when {
            score >= 80 -> "Great fit"
            score >= 60 -> "Good option"
            score >= 40 -> "Moderate"
            else -> "Consider alternatives"
        }
    }
}

