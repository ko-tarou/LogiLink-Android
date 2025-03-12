package com.example.logilink.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatScreen() {
    var selectedChat by remember { mutableStateOf(chatList.first()) }
    var messageInput by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxSize()) {
        // 🔹 小さめのチャットリスト（左側）
        ChatList(
            chatList = chatList,
            selectedChat = selectedChat,
            onChatSelected = { selectedChat = it },
            modifier = Modifier.width(80.dp) // 80dp にしてサイドバー風に
        )

        // 🔹 会話履歴エリア（右側）
        Column(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp)) {
            // 📌 会社情報
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(selectedChat.company.first().toString(), fontSize = 16.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = selectedChat.company, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = selectedChat.type, fontSize = 14.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 📌 メッセージ履歴
            LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
                items(selectedChat.messages) { message ->
                    ChatBubble(message)
                }
            }

            // 📌 メッセージ入力欄
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = messageInput,
                    onValueChange = { messageInput = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("メッセージを入力...") }
                )
                IconButton(onClick = {
                    if (messageInput.isNotBlank()) {
                        selectedChat.messages.add(ChatMessage(messageInput, true, "14:45"))
                        messageInput = ""
                    }
                }) {
                    Icon(Icons.Default.Send, contentDescription = "送信")
                }
            }
        }
    }
}

// 🔹 小さくなったチャットリスト
@Composable
fun ChatList(chatList: List<Chat>, selectedChat: Chat, onChatSelected: (Chat) -> Unit, modifier: Modifier) {
    Column(modifier = modifier.fillMaxHeight().padding(8.dp)) {
        LazyColumn {
            items(chatList) { chat ->
                ChatListItem(chat, chat == selectedChat, onChatSelected)
            }
        }
    }
}

// 🔹 チャットリストの項目
@Composable
fun ChatListItem(chat: Chat, isSelected: Boolean, onChatSelected: (Chat) -> Unit) {
    val backgroundColor = if (isSelected) Color.LightGray else Color.Transparent

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { onChatSelected(chat) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text(chat.company.first().toString(), fontSize = 16.sp, color = Color.White)
        }
    }
}

// 🔹 メッセージの吹き出し
@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleColor = if (message.isSent) Color.Black else Color(0xFFEFEFEF)
    val textColor = if (message.isSent) Color.White else Color.Black
    val alignment = if (message.isSent) Alignment.End else Alignment.Start

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
        Box(
            modifier = Modifier
                .background(bubbleColor, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(message.text, color = textColor)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(message.time, fontSize = 12.sp, color = Color.Gray)
    }
}

// 🔹 チャットデータモデル
data class Chat(
    val company: String,
    val type: String,
    val lastMessage: String,
    val time: String,
    val messages: MutableList<ChatMessage>
)

data class ChatMessage(val text: String, val isSent: Boolean, val time: String)

// 🔹 ダミーデータ
val chatList = listOf(
    Chat("山田製造株式会社", "製造会社", "注文 #1234 の納期について", "14:30", mutableListOf(
        ChatMessage("お世話になっております。注文 #1234 の納期について確認したいのですが、いつ頃になりますか？", false, "14:30"),
        ChatMessage("お問い合わせありがとうございます。現在確認中です。明日までにご連絡いたします。", true, "14:35"),
        ChatMessage("承知しました。よろしくお願いいたします。", false, "14:40")
    )),
    Chat("佐藤物流株式会社", "物流会社", "配送スケジュールを調整しました", "昨日", mutableListOf()),
    Chat("鈴木材料株式会社", "材料サプライヤー", "納品についての確認", "2日前", mutableListOf()),
    Chat("高橋素材株式会社", "素材サプライヤー", "新しい素材のサンプルを送ります", "3日前", mutableListOf())
)
