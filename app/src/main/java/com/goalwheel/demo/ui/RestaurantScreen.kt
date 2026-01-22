package com.goalwheel.demo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goalwheel.demo.state.AppViewModel
import com.goalwheel.demo.ui.components.*
import com.goalwheel.demo.ui.theme.ZomatoRed

@Composable
fun RestaurantScreen(
    viewModel: AppViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Restaurant Header
            item {
                RestaurantHeader()
            }
            
            // Filter Bar
            item {
                FilterBar()
            }
            
            // Menu section header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Menu",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${state.dishes.size} items",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }
            
            // Dish cards
            itemsIndexed(state.dishes) { _, dish ->
                com.goalwheel.demo.ui.components.DishCard(
                    dish = dish,
                    currentGoal = state.currentGoal,
                    score = viewModel.getScore(dish),
                    onClick = { viewModel.selectDish(dish) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            
            // Footer
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "This is a UI concept demo",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF4B5563)
                    )
                    Text(
                        text = "Goal interpretations are illustrative only",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF374151)
                    )
                }
            }
        }
        
        // Goal FAB
        FloatingActionButton(
            onClick = { viewModel.showGoalWheel() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = ZomatoRed,
            shape = CircleShape
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = state.currentGoal.emoji,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Goal",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        
        // Dish Bottom Sheet
        state.selectedDish?.let { dish ->
            val swapSuggestion = remember(dish, state.currentGoal, state.currentContext) {
                viewModel.getSwapSuggestion(dish)
            }
            
            DishBottomSheet(
                dish = dish,
                currentGoal = state.currentGoal,
                currentContext = state.currentContext,
                scrubScore = state.scrubScore,
                isScrubbing = state.isScrubbing,
                swapSuggestion = swapSuggestion,
                onGoalChange = { viewModel.setGoal(it) },
                onContextChange = { viewModel.setContext(it) },
                onScrubbing = { scrubbing, score -> viewModel.setScrubbing(scrubbing, score) },
                onShowGoalWheel = { viewModel.showGoalWheel() },
                onSwap = {
                    swapSuggestion?.let { viewModel.swapToDish(it.suggestedDish) }
                },
                onDismiss = { viewModel.closeDishSheet() }
            )
        }
        
        // Goal Wheel Overlay
        if (state.showGoalWheel) {
            GoalWheelOverlay(
                currentGoal = state.currentGoal,
                onGoalSelected = { 
                    viewModel.setGoal(it)
                    viewModel.hideGoalWheel()
                },
                onDismiss = { viewModel.hideGoalWheel() }
            )
        }
    }
}
