package com.hongul.filq.ui.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hongul.filq.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Email


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("마이페이지", fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back), // 새로운 아이콘 리소스
                            contentDescription = "뒤로 가기",
                            tint = Color.Unspecified                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            ProfileInfo()
        }
    }
}

@Composable
fun ProfileInfo() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(90.dp),
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_camera), // 기본 이미지 리소스
                    contentDescription = "Profile Image",
                    colorFilter = ColorFilter.tint(Color.Gray) // 이미지를 회색으로 표시
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "홍길동",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider(color = Color(0xFF125422), thickness = 2.dp)

        Spacer(modifier = Modifier.height(16.dp))

        ContactInfo(icon = Icons.Filled.Phone, label = "휴대전화", value = "010-1234-5678")
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Color(0x2A585656), thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        ContactInfo(icon = Icons.Filled.Email, label = "이메일", value = "xxx@stu.kmu.ac.kr")
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Color(0x2A585656), thickness = 1.dp)
    }
}

@Composable
fun ContactInfo(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = Color(0x33000000),
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Column {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF125422)
                )
            )
            Text(
                text = value,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyPageScreen() {
    MyPageScreen(onBack = {})
}
