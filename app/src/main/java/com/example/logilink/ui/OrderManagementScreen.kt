package com.example.logilink.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun OrderManagementScreen() {
    var selectedCustomer by remember { mutableStateOf("") }
    var deliveryDate by remember { mutableStateOf("2025/03/12") }
    var orderItems by remember { mutableStateOf(listOf(OrderItem())) }

    Column(modifier = Modifier.padding(16.dp)) {
        // 📌 タイトル
        Text("注文管理", style = MaterialTheme.typography.headlineMedium)
        Text("注文の作成と管理", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 取引先選択
        CustomerDropdown(selectedCustomer) { selectedCustomer = it }

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 納期選択
        DeliveryDatePicker(deliveryDate) { deliveryDate = it }

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 注文アイテムリスト
        Text("注文アイテム", style = MaterialTheme.typography.titleLarge)
        LazyColumn {
            items(orderItems) { item ->
                OrderItemRow(
                    item = item,
                    onRemove = { orderItems = orderItems - item }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 📌 アイテム追加ボタン
        Button(onClick = { orderItems = orderItems + OrderItem() }) {
            Icon(Icons.Default.Add, contentDescription = "アイテム追加")
            Spacer(modifier = Modifier.width(8.dp))
            Text("アイテム追加")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 送信 & キャンセル ボタン
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = { /* キャンセル処理 */ }) {
                Text("キャンセル")
            }
            Button(onClick = { /* 注文送信処理 */ }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                Text("注文を送信")
            }
        }
    }
}

// 📌 取引先選択ドロップダウン
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDropdown(selectedCustomer: String, onCustomerSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val customers = listOf("山田商事", "佐藤物流", "鈴木製造", "高橋貿易")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedCustomer,
            onValueChange = {},
            readOnly = true,
            label = { Text("取引先を選択") },
            modifier = Modifier.menuAnchor(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            customers.forEach { customer ->
                DropdownMenuItem(
                    text = { Text(customer) },
                    onClick = {
                        onCustomerSelected(customer)
                        expanded = false
                    }
                )
            }
        }
    }
}

// 📌 納期選択（カレンダーダイアログ）
@Composable
fun DeliveryDatePicker(selectedDate: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            onDateSelected("$selectedYear/${selectedMonth + 1}/$selectedDay")
        },
        year, month, day
    )

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {},
        readOnly = true,
        label = { Text("納期") },
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.DateRange, contentDescription = "カレンダーを開く")
            }
        }
    )
}

// 📌 注文アイテムの行
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderItemRow(item: OrderItem, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var selectedProduct by remember { mutableStateOf("製品A") }
        var quantity by remember { mutableStateOf("1") }
        var note by remember { mutableStateOf("") }

        // 製品選択
        val products = listOf("製品A", "製品B", "製品C")
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = selectedProduct,
                onValueChange = {},
                readOnly = true,
                label = { Text("製品") },
                modifier = Modifier.menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                products.forEach { product ->
                    DropdownMenuItem(
                        text = { Text(product) },
                        onClick = {
                            selectedProduct = product
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // 数量
        OutlinedTextField(value = quantity, onValueChange = { quantity = it }, label = { Text("数量") }, modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.width(8.dp))

        // 備考
        OutlinedTextField(value = note, onValueChange = { note = it }, label = { Text("備考") }, modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.width(8.dp))

        // 削除ボタン
        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, contentDescription = "削除")
        }
    }
}

// 注文アイテムのデータクラス
data class OrderItem(val product: String = "製品A", val quantity: Int = 1, val note: String = "")
