package com.goalwheel.demo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goalwheel.demo.data.Dish
import com.goalwheel.demo.logic.ContextMode
import com.goalwheel.demo.logic.Goal
import com.goalwheel.demo.logic.Reasons
import com.goalwheel.demo.logic.Scoring
import com.goalwheel.demo.logic.SwapSuggestion
import com.goalwheel.demo.ui.theme.ZomatoRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishBottomSheet(
    dish: Dish,
    currentGoal: Goal,
    currentContext: ContextMode,
    scrubScore: Int?,
    isScrubbing: Boolean,
    swapSuggestion: SwapSuggestion?,
    onGoalChange: (Goal) -> Unit,
    onContextChange: (ContextMode) -> Unit,
    onScrubbing: (Boolean, Int?) -> Unit,
    onShowGoalWheel: () -> Unit,
    onSwap: () -> Unit,
    onDismiss: () -> Unit
) {
    val gradientColors = when (dish.id.toIntOrNull()?.rem(5) ?: 0) {
        0 -> listOf(Color(0xFF22C55E), Color(0xFF15803D))
        1 -> listOf(Color(0xFFF97316), Color(0xFFC2410C))
        2 -> listOf(Color(0xFFEAB308), Color(0xFFA16207))
        3 -> listOf(Color(0xFF78350F), Color(0xFF451A03))
        else -> listOf(Color(0xFFDC2626), Color(0xFF991B1B))
    }
    
    val score = scrubScore ?: Scoring.scoreDish(dish, currentGoal, currentContext)
    val label = Scoring.getScoreLabel(score)
    val reasons = Reasons.reasonsFor(dish, currentGoal, currentContext)
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF0F0F14),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // Header image area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Brush.linearGradient(gradientColors))
            ) {
                // Close button
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
                
                // Veg/Non-Veg indicator
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .size(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFF16161D).copy(alpha = 0.9f))
                        .border(
                            2.dp,
                            if (dish.isVeg) Color(0xFF22C55E) else Color(0xFFEF4444),
                            RoundedCornerShape(4.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (dish.isVeg) Color(0xFF22C55E) else Color(0xFFEF4444))
                    )
                }
                
                // Bestseller badge
                if (dish.isBestseller) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp),
                        color = ZomatoRed,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "BESTSELLER",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            // Content
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Title and price
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = dish.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = dish.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF9CA3AF)
                        )
                    }
                    Text(
                        text = "₹${dish.price}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Macro row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MacroCard(
                        value = "${dish.calories}",
                        label = "kcal",
                        color = Color(0xFFF97316),
                        modifier = Modifier.weight(1f)
                    )
                    MacroCard(
                        value = "${dish.protein}g",
                        label = "protein",
                        color = Color(0xFF3B82F6),
                        modifier = Modifier.weight(1f)
                    )
                    MacroCard(
                        value = "${dish.carbs}g",
                        label = "carbs",
                        color = Color(0xFFEAB308),
                        modifier = Modifier.weight(1f)
                    )
                    MacroCard(
                        value = "${dish.fat}g",
                        label = "fat",
                        color = Color(0xFFA855F7),
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Context Toggle Row
                ContextToggleRow(
                    currentContext = currentContext,
                    onContextChange = onContextChange
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Goal Scrubber section
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF16161D),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        GoalScrubber(
                            dish = dish,
                            currentGoal = currentGoal,
                            currentContext = currentContext,
                            onGoalChange = onGoalChange,
                            onScrubbing = onScrubbing
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Goal header with wheel button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Goal Interpretation",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    
                    Surface(
                        modifier = Modifier.clickable { onShowGoalWheel() },
                        color = currentGoal.color.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = currentGoal.emoji,
                                fontSize = 14.sp
                            )
                            Text(
                                text = currentGoal.label,
                                style = MaterialTheme.typography.labelMedium,
                                color = currentGoal.color
                            )
                            Text(
                                text = "⬍",
                                color = Color(0xFF6B7280),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Score Panel
                ScorePanel(
                    score = score,
                    label = label,
                    reasons = reasons,
                    goal = currentGoal,
                    context = currentContext,
                    isAnimating = isScrubbing
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Swap Card
                SwapCard(
                    suggestion = swapSuggestion,
                    goal = currentGoal,
                    context = currentContext,
                    onSwap = onSwap
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Add to Cart button
                Button(
                    onClick = { /* Demo only */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ZomatoRed
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Add to Cart • ₹${dish.price}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun MacroCard(
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color(0xFF16161D),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF6B7280),
                fontSize = 9.sp
            )
        }
    }
}
