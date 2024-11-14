package com.hongul.filq.ui.theme.PhoneNumber

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hongul.filq.R

@Composable
fun ContactImportScreen(modifier: Modifier = Modifier) {
    var selectedContacts by remember { mutableStateOf(setOf<String>()) }
    var showDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val contacts = listOf("홍길동", "백수연", "홍중복", "김현진")
    val filteredContacts = contacts.filter { it.contains(searchQuery) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                textStyle = TextStyle(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text("이름 검색", color = Color.Gray, fontSize = 18.sp)
                    }
                    innerTextField()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        filteredContacts.forEach { contact ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedContacts = if (selectedContacts.contains(contact)) {
                            selectedContacts - contact
                        } else {
                            selectedContacts + contact
                        }
                    }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedContacts.contains(contact),
                    onCheckedChange = {
                        selectedContacts = if (it) {
                            selectedContacts + contact
                        } else {
                            selectedContacts - contact
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = contact, fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedContacts.isNotEmpty()) Color(0xFF4CAF50) else Color.Gray
            ),
            enabled = selectedContacts.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "가져오기", color = Color.White, fontSize = 18.sp)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("알림") },
            text = {
                Text("가져온 연락처는 암호화되어 안전하게 서버에 저장됩니다.\n" +
                        "가져오기 버튼을 누를 경우 개인정보처리방침을 준수한 것으로 간주합니다.")
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("가져오기")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("취소")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContactImportPreview() {
    MaterialTheme {
        ContactImportScreen(Modifier.fillMaxSize())
    }
}