package com.goalwheel.demo.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goalwheel.demo.logic.ContextMode

@Composable
fun ContextToggleRow(
    currentContext: ContextMode,
    onContextChange: (ContextMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val contexts = listOf(
        ContextMode.POST_WORKOUT,
        ContextMode.LATE_NIGHT,
        ContextMode.OFFICE_LUNCH
    )
    
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "CONTEXT",
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF6B7280),
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            contexts.forEach { context ->
                ContextChip(
                    context = context,
                    isSelected = currentContext == context,
                    onClick = {
                        if (currentContext == context) {
                            onContextChange(ContextMode.NONE)
                        } else {
                            onContextChange(context)
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ContextChip(
    context: ContextMode,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) context.color.copy(alpha = 0.2f) else Color(0xFF1E1E26),
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "bgColor"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) context.color.copy(alpha = 0.5f) else Color(0xFF2A2A35),
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "borderColor"
    )
    
    val textColor by animateColorAsState(
        targetValue = if (isSelected) context.color else Color(0xFF9CA3AF),
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "textColor"
    )
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = context.emoji,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = context.label.replace("-", "\n"),
                style = MaterialTheme.typography.labelSmall,
                color = textColor,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 10.sp,
                maxLines = 2,
                lineHeight = 12.sp
            )
        }
    }
}

