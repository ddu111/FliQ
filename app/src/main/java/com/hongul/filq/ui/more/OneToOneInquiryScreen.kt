package com.hongul.filq.ui.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.hongul.filq.R // 리소스 import
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneToOneInquiryScreen(onBack: () -> Unit, onSubmit: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var inquiryContent by remember { mutableStateOf("") }
    var attachedFiles by remember { mutableStateOf(mutableListOf<String>()) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val isFormFilled = email.isNotBlank() && inquiryContent.isNotBlank()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "1:1 문의",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back), // 뒤로 가기 아이콘
                            contentDescription = "뒤로 가기",
                            tint = Color.Unspecified
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // 이메일 입력 영역
            Text(
                text = "연락 받을 이메일 주소",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x80CCCCCC)) // 배경색 지정
                    .padding(12.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (email.isEmpty()) {
                        Text(
                            text = "예) xxx@stu.kmu.ac.kr",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "이메일 주소는 [마이페이지]>[계정 설정]에서 변경할 수 있습니다.",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Light),
                color = Color(0xFF6F6F6F)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 문의 내용 입력 영역
            Text(
                text = "문의 내용",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = Color.Black
            )
            BasicTextField(
                value = inquiryContent,
                onValueChange = { inquiryContent = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color(0x80CCCCCC))
                    .padding(12.dp),
                decorationBox = { innerTextField ->
                    if (inquiryContent.isEmpty()) {
                        Text(
                            text = "문의 내용을 작성하세요",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 파일 추가 버튼
            attachedFiles.forEachIndexed { index, fileName ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(Color(0x80CCCCCC))
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = fileName,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f),
                        color = Color.Black,
                        maxLines = 1
                    )
                    IconButton(onClick = {
                        attachedFiles = attachedFiles.toMutableList().apply { removeAt(index) }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "파일 삭제",
                            tint = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            if (attachedFiles.size < 5) {
                Text(
                    text = "+ 파일 추가",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        attachedFiles = attachedFiles.toMutableList().apply {
                            add("스크린샷_${attachedFiles.size + 1}.jpg")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "문제가 발생한 화면의 스크린샷을 첨부할 수 있으며, 이미지는 최대 5장까지 선택할 수 있습니다.",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Light),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 문의하기 버튼
            Button(
                onClick = {
                    if (isFormFilled) showConfirmationDialog = true
                    onSubmit()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFormFilled) Color(0xFF125422) else Color(0xFFD8D8D8)
                ),
                enabled = isFormFilled,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "문의하기",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (isFormFilled) Color.White else Color.Gray
                )
            }

        }
    }

    // 1:1 문의 접수 확인 다이얼로그
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "1:1 문의가 접수되었습니다.",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmationDialog = false
                        onBack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF125422)),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                ) {
                    Text("완료", color = Color.White)
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            properties = DialogProperties(usePlatformDefaultWidth = false),
            shape = RoundedCornerShape(4.dp),
            containerColor = Color.White // 다이얼로그 배경을 하얀색으로 설정
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOneToOneInquiryScreen() {
    OneToOneInquiryScreen(onBack = {}, onSubmit = {})
}
