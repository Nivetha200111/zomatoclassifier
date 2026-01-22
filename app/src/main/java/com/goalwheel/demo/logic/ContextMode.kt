package com.goalwheel.demo.logic

import androidx.compose.ui.graphics.Color

enum class ContextMode(
    val label: String,
    val emoji: String,
    val color: Color
) {
    NONE("None", "", Color.Gray),
    POST_WORKOUT("Post-workout", "🏋️", Color(0xFF22C55E)),
    LATE_NIGHT("Late night", "🌙", Color(0xFF6366F1)),
    OFFICE_LUNCH("Office lunch", "💼", Color(0xFFF59E0B))
}

