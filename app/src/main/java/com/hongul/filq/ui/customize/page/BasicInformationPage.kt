package com.hongul.filq.ui.customize.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.TextStyle
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicInformationPage(onNext: () -> Unit) {
    val progress = 0.25f

    // 입력 필드의 상태 관리
    val name = remember { mutableStateOf("") }
    val title = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }

    // 에러 메시지 상태
    val errorMessage = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(50)) // Box의 모서리를 둥글게 설정
        ) {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxSize() // Box 안에 맞게 채움
                    .clip(RoundedCornerShape(50)), // 진행 바도 둥글게 설정
                color = Color(0xFF9CD2A1) // 진행 바 색상
            )
        }

        Text(
            text = "기본 정보를\n입력해 주세요.",
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Red)) { // * 부분만 빨간색으로 설정
                        append("*")
                    }
                    append("은 필수 사항입니다.") // 나머지 텍스트
                },
                modifier = Modifier.padding(start = 280.dp, top = 0.dp),
                style = androidx.compose.ui.text.TextStyle(fontSize = 13.sp, color = Color.Gray)
            )
            Text(
                text = buildAnnotatedString {
                    append("이름 또는 닉네임 ")
                    withStyle(style = SpanStyle(color = Color.Red)) { // * 부분만 빨간색으로 설정
                        append("*")
                    }
                },
                modifier = Modifier.padding(start = 5.dp, bottom = 4.dp),
                fontWeight = FontWeight.Bold
            )


            // 이름 입력 필드
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                placeholder = { Text("2-20자 이내 / 특수문자 사용 가능") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.Gray.copy(alpha = 0.1f)), // 배경 색상 설정
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent, // 테두리 없애기
                    unfocusedBorderColor = Color.Transparent // 테두리 없애기
                )
            )

            // "0/20" 문자
            Text(
                text = "${name.value.length}/20",
                modifier = Modifier.padding(start = 360.dp, top = 0.dp),
                style = androidx.compose.ui.text.TextStyle(fontSize = 13.sp, color = Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = buildAnnotatedString {
                    append("명함 제목 ")
                    withStyle(style = SpanStyle(color = Color.Red)) { // * 부분만 빨간색으로 설정
                        append("*")
                    }
                },
                modifier = Modifier.padding(start = 5.dp, bottom = 4.dp),
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = title.value,
                onValueChange = { title.value = it },
                placeholder = { Text("명함 제목을 입력하세요") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.Gray.copy(alpha = 0.1f)), // 배경 색상 설정
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent, // 테두리 없애기
                    unfocusedBorderColor = Color.Transparent // 테두리 없애기
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        Column(modifier = Modifier.fillMaxWidth()) {
             Text(
                    text = buildAnnotatedString {
                        append("휴대폰 번호 ")
                        withStyle(style = SpanStyle(color = Color.Red)) { // * 부분만 빨간색으로 설정
                            append("*")
                        }
                    },
            modifier = Modifier.padding(start = 5.dp, bottom = 4.dp),
            fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = phone.value,
                onValueChange = { phone.value = it },
                placeholder = { Text("휴대폰 번호를 입력하세요.") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.Gray.copy(alpha = 0.1f)), // 배경 색상 설정
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent, // 테두리 없애기
                    unfocusedBorderColor = Color.Transparent // 테두리 없애기
                )
            )
            Text(
                text = "예: 010-0000-0000",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 290.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                    text = buildAnnotatedString {
                        append("이메일 ")
                        withStyle(style = SpanStyle(color = Color.Red)) { // * 부분만 빨간색으로 설정
                            append("*")
                        }
                    },
            modifier = Modifier.padding(start = 5.dp, bottom = 4.dp),
            fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                placeholder = { Text("이메일을 입력하세요") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.Gray.copy(alpha = 0.1f)), // 배경 색상 설정
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent, // 테두리 없애기
                    unfocusedBorderColor = Color.Transparent // 테두리 없애기
                )
            )
        }


        Spacer(modifier = Modifier.height(8.dp))


        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "주소",
                modifier = Modifier.padding(start = 5.dp, bottom = 4.dp),
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = address.value,
                onValueChange = { address.value = it },
                placeholder = { Text("주소를 입력하세요") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.Gray.copy(alpha = 0.1f)), // 배경 색상 설정
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent, // 테두리 없애기
                    unfocusedBorderColor = Color.Transparent // 테두리 없애기
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 에러 메시지 표시
        if (errorMessage.value.isNotEmpty()) {
            Text(
                text = errorMessage.value,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        // 다음 버튼
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter // 버튼을 화면 하단에 정렬
        ) {
            Button(
                onClick = {
                    // 필수 필드가 채워졌는지 확인
                    if (name.value.isBlank() || title.value.isBlank() || phone.value.isBlank() || email.value.isBlank()) {
                        errorMessage.value = "모든 필수 입력란을 채워주세요."
                    } else if (!isValidPhone(phone.value)) {
                        errorMessage.value = "유효한 전화번호를 입력하세요."
                    } else if (!isValidEmail(email.value)) {
                        errorMessage.value = "유효한 이메일 주소를 입력하세요."
                    } else {
                        errorMessage.value = ""
                        onNext() // 모든 필드가 채워졌을 경우에만 다음으로 이동
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF125422))
            ) {
                Text(text = "다음", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

// 전화번호 유효성 검사
fun isValidPhone(phone: String): Boolean {
    val phoneRegex = "^01[0-9]-\\d{3,4}-\\d{4}\$"
    return Pattern.matches(phoneRegex, phone)
}

// 이메일 유효성 검사
fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return Pattern.matches(emailRegex, email)
}