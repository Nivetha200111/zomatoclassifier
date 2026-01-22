package com.goalwheel.demo.logic

import androidx.compose.ui.graphics.Color

enum class Goal(
    val label: String,
    val emoji: String,
    val color: Color
) {
    Cutting("Cutting", "🔥", Color(0xFFEF4444)),
    Bulking("Bulking", "💪", Color(0xFFF97316)),
    Performance("Performance", "🏃", Color(0xFF22C55E)),
    LowGI("Low-GI", "🧠", Color(0xFF3B82F6)),
    Recovery("Recovery", "🩺", Color(0xFFA855F7));

    companion object {
        fun fromIndex(index: Int): Goal = entries[index.coerceIn(0, entries.size - 1)]
    }
}

