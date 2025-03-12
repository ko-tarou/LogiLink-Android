package com.example.logilink.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SideMenu(onClose: () -> Unit) {
    var selectedMenu by remember { mutableStateOf("ダッシュボード") }

    val menuItems = listOf(
        "ダッシュボード" to Icons.Default.Home,
        "メッセージ" to Icons.Default.Email,
        "取引先" to Icons.Default.Person,
        "注文管理" to Icons.Default.ShoppingCart,
        "配送" to Icons.Default.LocationOn,
        "レポート" to Icons.Default.Create,
        "設定" to Icons.Default.Settings
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // アプリタイトル
        Text(
            text = "LogiLink",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Divider()

        // メニューリスト
        Column(modifier = Modifier.padding(top = 8.dp)) {
            menuItems.forEach { (title, icon) ->
                SideMenuItem(
                    title = title,
                    icon = icon,
                    selected = title == selectedMenu,
                    onClick = {
                        selectedMenu = title
                        onClose()
                    }
                )
            }
        }
    }
}

@Composable
fun SideMenuItem(title: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent
    val textColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = textColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = textColor,
            style = if (selected) MaterialTheme.typography.bodyLarge.copy(fontWeight = MaterialTheme.typography.headlineSmall.fontWeight) else MaterialTheme.typography.bodyLarge
        )
    }
}
