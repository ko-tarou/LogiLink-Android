package com.example.logilink.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.logilink.R

@Composable
fun OverviewTab() {
    Column(modifier = Modifier.padding(16.dp)) {
        // 上部の統計カード
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            OverviewCard(title = "取引先企業", value = "12")
            OverviewCard(title = "進行中の注文", value = "23")
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            OverviewCard(title = "未読メッセージ", value = "8")
            OverviewCard(title = "完了した注文", value = "42")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // アクティビティリスト
        Text("最近のアクティビティ", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(activityList) { activity ->
                ActivityItem(activity)
            }
        }
    }
}

@Composable
fun OverviewCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 160.dp, height = 100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(value, style = MaterialTheme.typography.headlineLarge)
        }
    }
}

data class Activity(val title: String, val description: String, val time: String)

val activityList = listOf(
    Activity("新規注文 #1234", "山田製造株式会社から新しい注文がありました", "2時間前"),
    Activity("配送状況更新", "注文 #1230 が配送中になりました", "5時間前"),
    Activity("新規メッセージ", "佐藤物流株式会社からメッセージがあります", "昨日"),
    Activity("生産完了", "注文 #1228 の生産が完了しました", "2日前")
)

@Composable
fun ActivityItem(activity: Activity) {
    Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(activity.title, style = MaterialTheme.typography.bodyLarge)
                Text(activity.description, style = MaterialTheme.typography.bodySmall)
            }
            Text(activity.time, style = MaterialTheme.typography.bodySmall)
        }
    }
}
