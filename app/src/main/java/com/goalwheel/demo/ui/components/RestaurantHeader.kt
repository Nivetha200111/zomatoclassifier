package com.goalwheel.demo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RestaurantHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A24),
                        Color(0xFF0F0F14)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "The Spice Kitchen",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Surface(
                    color = Color(0xFF22C55E).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "PROMOTED",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF22C55E),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "North Indian, Mughlai, Biryani • Connaught Place",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF9CA3AF)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rating
                Surface(
                    color = Color(0xFF22C55E),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "★",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "4.2",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
                
                // Delivery time
                Text(
                    text = "🕐 25-30 min",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFD1D5DB)
                )
                
                // Price
                Text(
                    text = "₹350 for two",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFD1D5DB)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Offer banner
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "🎁",
                    fontSize = 14.sp
                )
                Text(
                    text = "50% off up to ₹100 | Use code WELCOME50",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFD1D5DB)
                )
            }
        }
    }
}

