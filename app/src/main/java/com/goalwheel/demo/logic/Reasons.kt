package com.goalwheel.demo.logic

import com.goalwheel.demo.data.Dish

object Reasons {
    
    fun reasonsFor(dish: Dish, goal: Goal): List<String> {
        return when (goal) {
            Goal.Cutting -> cuttingReasons(dish)
            Goal.Bulking -> bulkingReasons(dish)
            Goal.Performance -> performanceReasons(dish)
            Goal.LowGI -> lowGIReasons(dish)
            Goal.Recovery -> recoveryReasons(dish)
        }
    }
    
    private fun cuttingReasons(dish: Dish): List<String> {
        val reasons = mutableListOf<String>()
        
        val proteinRatio = dish.protein.toFloat() / dish.calories * 100
        reasons += when {
            proteinRatio > 10 -> "High protein density supports muscle retention"
            proteinRatio > 6 -> "Moderate protein-to-calorie ratio"
            else -> "Lower protein density — consider protein-rich sides"
        }
        
        reasons += when {
            dish.calories < 350 -> "Low calorie count fits deficit goals well"
            dish.calories < 500 -> "Moderate calories — fits most deficit plans"
            dish.calories > 700 -> "Calorie-dense — budget carefully around this meal"
            else -> "Mid-range calories — reasonable for a main meal"
        }
        
        reasons += when {
            dish.isFried -> "Fried preparation adds extra calories"
            dish.fiber > 5 -> "Fiber content helps with satiety"
            dish.fat < 15 -> "Lower fat keeps overall calories in check"
            else -> "Balanced macros — portion control is key"
        }
        
        return reasons.take(3)
    }
    
    private fun bulkingReasons(dish: Dish): List<String> {
        val reasons = mutableListOf<String>()
        
        reasons += when {
            dish.calories > 700 -> "High calorie density supports surplus goals"
            dish.calories > 500 -> "Good calorie content for muscle building"
            else -> "May need additional portions for surplus"
        }
        
        reasons += when {
            dish.protein > 35 -> "Excellent protein for muscle synthesis"
            dish.protein > 20 -> "Solid protein contribution"
            else -> "Consider adding a protein source alongside"
        }
        
        reasons += when {
            dish.carbs > 50 -> "Carbs provide energy for intense training"
            dish.carbs > 30 -> "Moderate carbs support glycogen stores"
            else -> "Low carb — pair with rice or bread for energy"
        }
        
        return reasons.take(3)
    }
    
    private fun performanceReasons(dish: Dish): List<String> {
        val reasons = mutableListOf<String>()
        
        reasons += when {
            dish.carbs in 40..80 -> "Good carb range for sustained energy"
            dish.carbs > 80 -> "High carbs — time well around activity"
            dish.carbs < 20 -> "Low carbs may limit glycogen availability"
            else -> "Moderate carbs — adequate for lighter sessions"
        }
        
        reasons += when {
            dish.isFried -> "Fried foods may slow digestion pre-workout"
            dish.fat > 35 -> "High fat content — allow digestion time"
            else -> "Digestible — suitable around training windows"
        }
        
        reasons += when {
            dish.protein > 25 -> "Protein supports muscle preservation"
            dish.protein > 15 -> "Adequate protein for general activity"
            else -> "Lower protein — fine for carb-focused fueling"
        }
        
        return reasons.take(3)
    }
    
    private fun lowGIReasons(dish: Dish): List<String> {
        val reasons = mutableListOf<String>()
        
        reasons += when {
            dish.carbs < 15 -> "Very low carbs — minimal glucose impact"
            dish.carbs < 30 -> "Low carb content supports stable levels"
            dish.carbs > 60 -> "High carbs cause notable glucose rise"
            else -> "Moderate carbs — impact depends on source"
        }
        
        reasons += when {
            dish.isSugary -> "Added sugars cause faster glucose spike"
            dish.fiber > 6 -> "High fiber slows carb absorption"
            dish.fiber > 3 -> "Some fiber helps moderate response"
            else -> "Limited fiber — glucose rise may be quicker"
        }
        
        reasons += when {
            dish.protein > 20 && dish.fat > 15 -> "Protein and fat buffer glucose release"
            dish.protein > 20 -> "Protein helps moderate absorption"
            dish.fat > 20 -> "Fat content slows digestion somewhat"
            else -> "Quick digestion — pair with protein if needed"
        }
        
        return reasons.take(3)
    }
    
    private fun recoveryReasons(dish: Dish): List<String> {
        val reasons = mutableListOf<String>()
        
        reasons += when {
            dish.protein > 35 -> "High protein accelerates muscle repair"
            dish.protein > 20 -> "Good protein content aids recovery"
            else -> "Lower protein — add eggs or yogurt alongside"
        }
        
        reasons += when {
            dish.carbs in 30..60 -> "Carbs help replenish glycogen stores"
            dish.carbs > 60 -> "High carbs for significant glycogen reload"
            dish.carbs < 20 -> "Limited carbs — glycogen replenishment slower"
            else -> "Moderate carbs contribute to recovery"
        }
        
        reasons += when {
            dish.isFried -> "Heavy meal — may cause post-activity sluggishness"
            dish.calories > 700 -> "Calorie-rich — great after intense sessions"
            dish.calories < 300 -> "Light meal — suitable for lighter activity"
            else -> "Balanced portion for standard recovery needs"
        }
        
        return reasons.take(3)
    }
}

