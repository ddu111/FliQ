package com.hongul.filq.ui.more

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InnerMoreScreen(
    onNavigateToMyPage: () -> Unit,
    onNavigateToEvent: () -> Unit,
    onNavigateToInquiry: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("더보기", fontSize = 18.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(paddingValues)
        ) {
            MoreMenuItem("마이페이지", onClick = onNavigateToMyPage)
            Divider(color = Color.Gray, thickness = 1.dp)
            MoreMenuItem("이벤트/공지사항", onClick = onNavigateToEvent)
            Divider(color = Color.Gray, thickness = 1.dp)
            MoreMenuItem("1:1 문의", onClick = onNavigateToInquiry)
            Divider(color = Color.Gray, thickness = 1.dp)
            MoreMenuItem("설정", onClick = onNavigateToSettings)
        }
    }
}

@Composable
fun MoreMenuItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp, fontWeight = FontWeight.Medium),
            color = Color.Black
        )
    }
}
