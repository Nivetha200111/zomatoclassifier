package com.goalwheel.demo.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goalwheel.demo.logic.Goal

@Composable
fun ScorePanel(
    score: Int,
    label: String,
    reasons: List<String>,
    goal: Goal,
    isAnimating: Boolean = false
) {
    val animatedScore by animateIntAsState(
        targetValue = score,
        animationSpec = tween(
            durationMillis = if (isAnimating) 100 else 400,
            easing = FastOutSlowInEasing
        ),
        label = "scoreAnimation"
    )
    
    val scoreColor = when {
        animatedScore >= 80 -> Color(0xFF22C55E)
        animatedScore >= 60 -> Color(0xFFEAB308)
        animatedScore >= 40 -> Color(0xFFF97316)
        else -> Color(0xFFEF4444)
    }
    
    val backgroundColor = when {
        animatedScore >= 80 -> Color(0xFF22C55E).copy(alpha = 0.1f)
        animatedScore >= 60 -> Color(0xFFEAB308).copy(alpha = 0.1f)
        animatedScore >= 40 -> Color(0xFFF97316).copy(alpha = 0.1f)
        else -> Color(0xFFEF4444).copy(alpha = 0.1f)
    }
    
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Circular Score
                Box(
                    modifier = Modifier.size(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularScoreIndicator(
                        score = animatedScore,
                        color = goal.color
                    )
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "$animatedScore",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = scoreColor
                        )
                        Text(
                            text = "/100",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Goal badge
                    Surface(
                        color = goal.color.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = goal.emoji,
                                fontSize = 16.sp
                            )
                            Text(
                                text = goal.label,
                                style = MaterialTheme.typography.labelMedium,
                                color = goal.color,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Score label with animation
                    AnimatedContent(
                        targetState = label,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(200)) togetherWith
                                fadeOut(animationSpec = tween(200))
                        },
                        label = "labelAnimation"
                    ) { targetLabel ->
                        Text(
                            text = targetLabel,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = scoreColor
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Reasons with crossfade
            AnimatedContent(
                targetState = reasons,
                transitionSpec = {
                    fadeIn(animationSpec = tween(250)) togetherWith
                        fadeOut(animationSpec = tween(200))
                },
                label = "reasonsAnimation"
            ) { targetReasons ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    targetReasons.forEachIndexed { index, reason ->
                        ReasonItem(
                            index = index + 1,
                            text = reason,
                            color = goal.color
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Disclaimer
            Text(
                text = "Scores reflect general alignment with the selected goal. Individual needs vary.",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF4B5563),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun CircularScoreIndicator(
    score: Int,
    color: Color
) {
    val sweepAngle by animateFloatAsState(
        targetValue = (score / 100f) * 360f,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "sweepAnimation"
    )
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeWidth = 8.dp.toPx()
        val radius = (size.minDimension - strokeWidth) / 2
        val topLeft = Offset(
            (size.width - radius * 2) / 2,
            (size.height - radius * 2) / 2
        )
        
        // Background arc
        drawArc(
            color = Color(0xFF2A2A35),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        
        // Progress arc
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun ReasonItem(
    index: Int,
    text: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            modifier = Modifier.size(20.dp),
            color = color.copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "$index",
                    style = MaterialTheme.typography.labelSmall,
                    color = color,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF9CA3AF),
            lineHeight = 18.sp
        )
    }
}

