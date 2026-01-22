package com.goalwheel.demo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goalwheel.demo.ui.theme.ZomatoRed

@Composable
fun FilterBar() {
    var calories by remember { mutableFloatStateOf(800f) }
    var protein by remember { mutableFloatStateOf(50f) }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0F0F14).copy(alpha = 0.9f))
            .padding(16.dp)
    ) {
        // Healthy Mode Toggle
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF22C55E).copy(alpha = 0.1f))
                .border(1.dp, Color(0xFF22C55E).copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "🥗 Healthy Mode",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF22C55E),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF22C55E))
            ) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White)
                        .align(Alignment.CenterEnd)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Calories Slider
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "CALORIES",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF6B7280),
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Up to ${calories.toInt()} kcal",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Slider(
                    value = calories,
                    onValueChange = { calories = it },
                    valueRange = 100f..1500f,
                    colors = SliderDefaults.colors(
                        thumbColor = ZomatoRed,
                        activeTrackColor = ZomatoRed,
                        inactiveTrackColor = Color(0xFF2A2A35)
                    )
                )
            }
            
            // Protein Slider
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "PROTEIN",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF6B7280),
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Min ${protein.toInt()}g",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Slider(
                    value = protein,
                    onValueChange = { protein = it },
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = ZomatoRed,
                        activeTrackColor = ZomatoRed,
                        inactiveTrackColor = Color(0xFF2A2A35)
                    )
                )
            }
        }
    }
}

