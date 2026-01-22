package com.goalwheel.demo.logic

import com.goalwheel.demo.data.Dish

object Reasons {
    
    fun reasonsFor(dish: Dish, goal: Goal, context: ContextMode = ContextMode.NONE): List<String> {
        val baseReasons = when (goal) {
            Goal.Cutting -> cuttingReasons(dish)
            Goal.Bulking -> bulkingReasons(dish)
            Goal.Performance -> performanceReasons(dish)
            Goal.LowGI -> lowGIReasons(dish)
            Goal.Recovery -> recoveryReasons(dish)
        }.toMutableList()
        
        // Add context-specific reason if applicable
        val contextReason = getContextReason(dish, goal, context)
        if (contextReason != null) {
            baseReasons[2] = contextReason
        }
        
        return baseReasons.take(3)
    }
    
    private fun getContextReason(dish: Dish, goal: Goal, context: ContextMode): String? {
        return when (context) {
            ContextMode.NONE -> null
            ContextMode.POST_WORKOUT -> getPostWorkoutReason(dish)
            ContextMode.LATE_NIGHT -> getLateNightReason(dish)
            ContextMode.OFFICE_LUNCH -> getOfficeLunchReason(dish)
        }
    }
    
    private fun getPostWorkoutReason(dish: Dish): String {
        return when {
            dish.protein > 30 && !dish.isFried -> 
                "High protein with easy digestion — ideal post-workout"
            dish.protein > 25 -> 
                "Good protein content supports muscle recovery"
            dish.carbs > 40 && dish.protein > 15 -> 
                "Carb-protein combo helps replenish glycogen"
            dish.isFried -> 
                "Fried preparation may slow post-workout absorption"
            dish.protein < 15 -> 
                "Lower protein — consider adding a protein source"
            else -> 
                "Moderate choice for post-workout recovery"
        }
    }
    
    private fun getLateNightReason(dish: Dish): String {
        return when {
            dish.calories < 350 && dish.fat < 15 -> 
                "Light and easy to digest — suitable for late night"
            dish.calories > 600 -> 
                "Heavy meal — may disrupt sleep quality"
            dish.isFried -> 
                "Fried foods digest slowly — not ideal before bed"
            dish.fat > 30 -> 
                "High fat content may cause discomfort at night"
            dish.protein > 20 && dish.fat < 20 -> 
                "Lean protein without heavy fats — reasonable late choice"
            else -> 
                "Moderate weight — timing matters for digestion"
        }
    }
    
    private fun getOfficeLunchReason(dish: Dish): String {
        return when {
            dish.calories in 350..550 && !dish.isFried -> 
                "Balanced energy — helps maintain afternoon focus"
            dish.calories > 700 -> 
                "Heavy meal may cause afternoon energy dip"
            dish.isFried -> 
                "Fried foods can trigger post-lunch sluggishness"
            dish.isSugary -> 
                "Sugar spike followed by crash — affects productivity"
            dish.fiber > 5 && dish.protein > 15 -> 
                "Fiber and protein promote sustained satiety"
            dish.calories < 300 -> 
                "Light portion — may need a snack later"
            else -> 
                "Adequate for office lunch — moderate energy load"
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
