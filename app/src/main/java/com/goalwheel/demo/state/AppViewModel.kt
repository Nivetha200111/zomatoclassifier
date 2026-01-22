package com.goalwheel.demo.state

import androidx.lifecycle.ViewModel
import com.goalwheel.demo.data.Dish
import com.goalwheel.demo.data.MockDishes
import com.goalwheel.demo.logic.Goal
import com.goalwheel.demo.logic.Reasons
import com.goalwheel.demo.logic.Scoring
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AppState(
    val dishes: List<Dish> = MockDishes.dishes,
    val currentGoal: Goal = Goal.Cutting,
    val selectedDish: Dish? = null,
    val showGoalWheel: Boolean = false,
    val isScrubbing: Boolean = false,
    val scrubScore: Int? = null
)

class AppViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(AppState())
    val state: StateFlow<AppState> = _state.asStateFlow()
    
    fun selectDish(dish: Dish) {
        _state.value = _state.value.copy(
            selectedDish = dish,
            scrubScore = null,
            isScrubbing = false
        )
    }
    
    fun closeDishSheet() {
        _state.value = _state.value.copy(
            selectedDish = null,
            scrubScore = null,
            isScrubbing = false
        )
    }
    
    fun setGoal(goal: Goal) {
        _state.value = _state.value.copy(
            currentGoal = goal,
            scrubScore = null,
            isScrubbing = false
        )
    }
    
    fun showGoalWheel() {
        _state.value = _state.value.copy(showGoalWheel = true)
    }
    
    fun hideGoalWheel() {
        _state.value = _state.value.copy(showGoalWheel = false)
    }
    
    fun setScrubbing(isScrubbing: Boolean, interpolatedScore: Int? = null) {
        _state.value = _state.value.copy(
            isScrubbing = isScrubbing,
            scrubScore = interpolatedScore
        )
    }
    
    fun getScore(dish: Dish, goal: Goal = _state.value.currentGoal): Int {
        return Scoring.scoreDish(dish, goal)
    }
    
    fun getReasons(dish: Dish, goal: Goal = _state.value.currentGoal): List<String> {
        return Reasons.reasonsFor(dish, goal)
    }
    
    fun getScoreLabel(score: Int): String {
        return Scoring.getScoreLabel(score)
    }
}

