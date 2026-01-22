package com.goalwheel.demo.logic

import com.goalwheel.demo.data.Dish

object Scoring {
    
    fun scoreDish(dish: Dish, goal: Goal, context: ContextMode = ContextMode.NONE): Int {
        val baseScore = when (goal) {
            Goal.Cutting -> scoreCutting(dish)
            Goal.Bulking -> scoreBulking(dish)
            Goal.Performance -> scorePerformance(dish)
            Goal.LowGI -> scoreLowGI(dish)
            Goal.Recovery -> scoreRecovery(dish)
        }
        
        val contextModifier = getContextModifier(dish, goal, context)
        return (baseScore + contextModifier).coerceIn(0, 100)
    }
    
    private fun getContextModifier(dish: Dish, goal: Goal, context: ContextMode): Int {
        return when (context) {
            ContextMode.NONE -> 0
            ContextMode.POST_WORKOUT -> getPostWorkoutModifier(dish, goal)
            ContextMode.LATE_NIGHT -> getLateNightModifier(dish, goal)
            ContextMode.OFFICE_LUNCH -> getOfficeLunchModifier(dish, goal)
        }
    }
    
    private fun getPostWorkoutModifier(dish: Dish, goal: Goal): Int {
        var modifier = 0
        
        // Protein becomes more important
        if (dish.protein > 30) modifier += 8
        else if (dish.protein > 20) modifier += 4
        else if (dish.protein < 15) modifier -= 5
        
        // Carbs less penalized (glycogen replenishment)
        if (dish.carbs > 40 && goal == Goal.Cutting) modifier += 5
        if (dish.carbs > 50 && goal == Goal.LowGI) modifier += 3
        
        // Quick digestion preferred
        if (!dish.isFried) modifier += 3
        
        return modifier
    }
    
    private fun getLateNightModifier(dish: Dish, goal: Goal): Int {
        var modifier = 0
        
        // Heavy foods penalized
        if (dish.calories > 600) modifier -= 10
        else if (dish.calories > 450) modifier -= 5
        else if (dish.calories < 350) modifier += 5
        
        // High fat penalized (slow digestion)
        if (dish.fat > 30) modifier -= 8
        else if (dish.fat < 15) modifier += 4
        
        // Fried foods penalized more
        if (dish.isFried) modifier -= 8
        
        // Light protein is fine
        if (dish.protein > 20 && dish.protein < 35 && dish.fat < 20) modifier += 3
        
        return modifier
    }
    
    private fun getOfficeLunchModifier(dish: Dish, goal: Goal): Int {
        var modifier = 0
        
        // Balanced calories preferred (avoid energy crashes)
        if (dish.calories in 350..550) modifier += 6
        else if (dish.calories > 700) modifier -= 8
        else if (dish.calories < 250) modifier -= 4
        
        // Moderate protein for sustained energy
        if (dish.protein in 18..35) modifier += 4
        
        // Avoid very heavy/fried (afternoon slump)
        if (dish.isFried) modifier -= 6
        if (dish.fat > 35) modifier -= 5
        
        // Fiber helps satiety
        if (dish.fiber > 5) modifier += 3
        
        // Sugary foods cause crashes
        if (dish.isSugary) modifier -= 5
        
        return modifier
    }
    
    private fun scoreCutting(dish: Dish): Int {
        var score = 50
        
        val proteinRatio = dish.protein.toFloat() / dish.calories * 100
        score += when {
            proteinRatio > 10 -> 30
            proteinRatio > 7 -> 20
            proteinRatio > 4 -> 10
            else -> -10
        }
        
        score += when {
            dish.calories < 350 -> 20
            dish.calories < 500 -> 10
            dish.calories > 700 -> -25
            dish.calories > 550 -> -10
            else -> 0
        }
        
        if (dish.isFried) score -= 15
        if (dish.fiber > 5) score += 10
        
        return score.coerceIn(0, 100)
    }
    
    private fun scoreBulking(dish: Dish): Int {
        var score = 50
        
        score += when {
            dish.calories > 700 -> 25
            dish.calories > 500 -> 15
            dish.calories < 300 -> -15
            else -> 5
        }
        
        score += when {
            dish.protein > 35 -> 25
            dish.protein > 25 -> 15
            dish.protein > 15 -> 5
            else -> -10
        }
        
        if (dish.carbs > 40) score += 10
        
        return score.coerceIn(0, 100)
    }
    
    private fun scorePerformance(dish: Dish): Int {
        var score = 50
        
        score += when {
            dish.carbs in 40..80 -> 20
            dish.carbs > 80 -> 10
            dish.carbs < 20 -> -15
            else -> 5
        }
        
        score += when {
            dish.protein > 25 -> 15
            dish.protein > 15 -> 10
            else -> 0
        }
        
        if (dish.isFried) score -= 20
        if (dish.fat > 40) score -= 10
        if (dish.calories in 400..600) score += 10
        
        return score.coerceIn(0, 100)
    }
    
    private fun scoreLowGI(dish: Dish): Int {
        var score = 50
        
        score += when {
            dish.carbs < 15 -> 30
            dish.carbs < 30 -> 15
            dish.carbs > 70 -> -25
            dish.carbs > 50 -> -15
            else -> 0
        }
        
        score += when {
            dish.fiber > 8 -> 15
            dish.fiber > 4 -> 10
            else -> 0
        }
        
        if (dish.isSugary) score -= 20
        if (dish.protein > 20 && dish.fat > 10) score += 10
        
        return score.coerceIn(0, 100)
    }
    
    private fun scoreRecovery(dish: Dish): Int {
        var score = 50
        
        score += when {
            dish.protein > 35 -> 30
            dish.protein > 25 -> 20
            dish.protein > 15 -> 10
            else -> -10
        }
        
        score += when {
            dish.carbs in 30..60 -> 15
            dish.carbs > 60 -> 5
            dish.carbs < 15 -> -5
            else -> 10
        }
        
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
