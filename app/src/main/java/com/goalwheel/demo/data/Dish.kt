package com.goalwheel.demo.data

data class Dish(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val fiber: Int,
    val isFried: Boolean,
    val isSugary: Boolean,
    val isVeg: Boolean,
    val isBestseller: Boolean = false,
    val imageUrl: String
)

