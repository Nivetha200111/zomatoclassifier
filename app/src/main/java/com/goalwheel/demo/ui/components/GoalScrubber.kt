package com.goalwheel.demo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goalwheel.demo.data.Dish
import com.goalwheel.demo.logic.ContextMode
import com.goalwheel.demo.logic.Goal
import com.goalwheel.demo.logic.Scoring
import kotlin.math.roundToInt

@Composable
fun GoalScrubber(
    dish: Dish,
    currentGoal: Goal,
    currentContext: ContextMode,
    onGoalChange: (Goal) -> Unit,
    onScrubbing: (Boolean, Int?) -> Unit
) {
    val goals = Goal.entries
    var sliderPosition by remember { mutableFloatStateOf(currentGoal.ordinal.toFloat()) }
    var isDragging by remember { mutableStateOf(false) }
    var trackSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current
    
    // Animate position when not dragging and goal changes externally
    val animatedPosition by animateFloatAsState(
        targetValue = if (isDragging) sliderPosition else currentGoal.ordinal.toFloat(),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "positionAnimation"
    )
    
    // Calculate interpolated score while dragging
    LaunchedEffect(sliderPosition, isDragging, currentContext) {
        if (isDragging) {
            val lowerIndex = sliderPosition.toInt().coerceIn(0, goals.size - 2)
            val upperIndex = (lowerIndex + 1).coerceIn(0, goals.size - 1)
            val fraction = sliderPosition - lowerIndex
            
            val lowerScore = Scoring.scoreDish(dish, goals[lowerIndex], currentContext)
            val upperScore = Scoring.scoreDish(dish, goals[upperIndex], currentContext)
            val interpolatedScore = (lowerScore + (upperScore - lowerScore) * fraction).roundToInt()
            
            onScrubbing(true, interpolatedScore)
        }
    }
    
    // Get current color based on position
    val currentColor = remember(animatedPosition) {
        val index = animatedPosition.roundToInt().coerceIn(0, goals.size - 1)
        goals[index].color
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Label
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SCRUB TO EXPLORE",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF6B7280),
                letterSpacing = 1.sp
            )
            
            if (isDragging) {
                Surface(
                    color = currentColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Scrubbing...",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = currentColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Scrubber Track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .onSizeChanged { trackSize = it }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            isDragging = true
                            val position = (offset.x / trackSize.width) * (goals.size - 1)
                            sliderPosition = position.coerceIn(0f, (goals.size - 1).toFloat())
                        },
                        onDragEnd = {
                            isDragging = false
                            val nearestGoal = goals[sliderPosition.roundToInt().coerceIn(0, goals.size - 1)]
                            sliderPosition = nearestGoal.ordinal.toFloat()
                            onGoalChange(nearestGoal)
                            onScrubbing(false, null)
                        },
                        onDragCancel = {
                            isDragging = false
                            val nearestGoal = goals[sliderPosition.roundToInt().coerceIn(0, goals.size - 1)]
                            sliderPosition = nearestGoal.ordinal.toFloat()
                            onGoalChange(nearestGoal)
                            onScrubbing(false, null)
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            val delta = (dragAmount / trackSize.width) * (goals.size - 1)
                            sliderPosition = (sliderPosition + delta).coerceIn(0f, (goals.size - 1).toFloat())
                        }
                    )
                }
        ) {
            // Track background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color(0xFF2A2A35))
            )
            
            // Filled track
            val fillFraction = animatedPosition / (goals.size - 1)
            Box(
                modifier = Modifier
                    .fillMaxWidth(fillFraction.coerceIn(0f, 1f))
                    .height(6.dp)
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(goals.first().color, currentColor)
                        )
                    )
            )
            
            // Goal markers
            goals.forEachIndexed { index, goal ->
                val markerPosition = index.toFloat() / (goals.size - 1)
                val isActive = (animatedPosition - index).let { kotlin.math.abs(it) < 0.3f }
                val isPast = animatedPosition >= index
                
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(
                            x = with(density) {
                                (trackSize.width * markerPosition).toDp() - 12.dp
                            }
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Marker dot
                    Box(
                        modifier = Modifier
                            .size(if (isActive) 16.dp else 12.dp)
                            .clip(CircleShape)
                            .background(
                                if (isPast || isActive) goal.color 
                                else Color(0xFF4B5563)
                            )
                            .then(
                                if (isActive) Modifier.shadow(8.dp, CircleShape, spotColor = goal.color)
                                else Modifier
                            )
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Emoji
                    Text(
                        text = goal.emoji,
                        fontSize = if (isActive) 18.sp else 14.sp
                    )
                    
                    // Label
                    Text(
                        text = goal.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isActive) goal.color else Color(0xFF6B7280),
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 9.sp
                    )
                }
            }
            
            // Thumb
            val thumbPosition = animatedPosition / (goals.size - 1)
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(
                        x = with(density) {
                            (trackSize.width * thumbPosition).toDp() - 14.dp
                        }
                    )
                    .size(28.dp)
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(currentColor)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Hint
        Text(
            text = "Drag to see how meaning shifts between goals",
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF4B5563),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
