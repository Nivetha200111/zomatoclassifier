package com.goalwheel.demo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goalwheel.demo.logic.Goal
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GoalWheelOverlay(
    currentGoal: Goal,
    onGoalSelected: (Goal) -> Unit,
    onDismiss: () -> Unit
) {
    val goals = Goal.entries
    
    // Animation for entrance
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }
    
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleAnimation"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(200),
        label = "alphaAnimation"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f * alpha))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onDismiss
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .scale(scale)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {} // Prevent clicks from dismissing
                )
        ) {
            // Wheel container
            Box(
                modifier = Modifier.size(320.dp),
                contentAlignment = Alignment.Center
            ) {
                // Outer decorative ring
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1A1A24).copy(alpha = 0.5f))
                )
                
                // Goal buttons arranged in a circle
                goals.forEachIndexed { index, goal ->
                    val angle = (index * 72.0 - 90.0) * (Math.PI / 180.0)
                    val radius = 110.dp
                    val isActive = goal == currentGoal
                    
                    val buttonScale by animateFloatAsState(
                        targetValue = if (isActive) 1.1f else 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy
                        ),
                        label = "buttonScaleAnimation"
                    )
                    
                    Box(
                        modifier = Modifier
                            .offset(
                                x = (cos(angle) * radius.value).dp,
                                y = (sin(angle) * radius.value).dp
                            )
                            .scale(buttonScale)
                            .size(70.dp)
                            .then(
                                if (isActive) Modifier.shadow(
                                    16.dp,
                                    CircleShape,
                                    spotColor = goal.color
                                ) else Modifier
                            )
                            .clip(CircleShape)
                            .background(
                                if (isActive) goal.color
                                else goal.color.copy(alpha = 0.2f)
                            )
                            .clickable {
                                onGoalSelected(goal)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = goal.emoji,
                                fontSize = 24.sp
                            )
                            Text(
                                text = goal.label,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isActive) Color.White else Color(0xFFD1D5DB),
                                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 9.sp
                            )
                        }
                    }
                }
                
                // Center hub
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0F0F14))
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1A1A24)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "SELECT",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF6B7280),
                            letterSpacing = 1.sp,
                            fontSize = 8.sp
                        )
                        Text(
                            text = "Goal",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(24.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(currentGoal.color)
                        )
                    }
                }
            }
            
            // Close button
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = (-20).dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2A2A35))
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
        
        // Current selection indicator at bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = currentGoal.color.copy(alpha = 0.2f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = currentGoal.emoji,
                        fontSize = 18.sp
                    )
                    Text(
                        text = currentGoal.label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = currentGoal.color,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Tap a segment to change",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF4B5563)
            )
        }
    }
}

