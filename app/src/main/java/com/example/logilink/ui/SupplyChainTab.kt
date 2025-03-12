package com.example.logilink.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SupplyChainTab() {
    var selectedFilter by remember { mutableStateOf("すべて") }
    val filterOptions = listOf("A", "B", "C")

    Column(modifier = Modifier.padding(16.dp)) {
        // タイトル
        Text("サプライチェーンフロー", style = MaterialTheme.typography.headlineMedium)
        Text("材料の流れに沿った企業の表示", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

        Spacer(modifier = Modifier.height(12.dp))

        // フィルターボタン
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            filterOptions.forEach { filter ->
                FilterButton(
                    text = filter,
                    isSelected = filter == selectedFilter,
                    onClick = { selectedFilter = filter }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 企業カテゴリごとの表示
        SupplyChainCategory(title = "材料サプライヤー") {
            SupplyChainCompany(name = "サンプル材料会社", icon = Icons.Default.AccountCircle, isSample = true)
        }
        SupplyChainCategory(title = "製造会社") {
            SupplyChainCompany(name = "サンプル製造会社", icon = Icons.Default.AccountCircle, isSample = true)
        }
        SupplyChainCategory(title = "販売会社") {
            SupplyChainCompany(name = "サンプル販売会社", icon = Icons.Default.AccountCircle, isSample = true)
            SupplyChainCompany(name = "山田株式会社", icon = Icons.Default.AccountCircle, isSample = false)
        }
    }
}

// フィルターボタン（カスタムデザイン）
@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(text, fontSize = 14.sp)
    }
}

// 各カテゴリのタイトルと企業リスト
@Composable
fun SupplyChainCategory(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(title, style = MaterialTheme.typography.titleLarge, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

// 企業情報カード
@Composable
fun SupplyChainCompany(name: String, icon: ImageVector, isSample: Boolean) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = name, modifier = Modifier.size(24.dp), tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                fontSize = 16.sp,
                color = Color.Black
            )
            if (isSample) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(サンプル)",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
