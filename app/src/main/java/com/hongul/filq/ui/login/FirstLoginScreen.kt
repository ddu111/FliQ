package com.hongul.filq.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun FirstLoginScreen(onLoginClick: () -> Unit = {}, onSignUpClick: () -> Unit = {}) {
    val customGreen = Color(0xFF125422)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "FliQ",
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = MaterialTheme.typography.displayLarge.fontSize * 1.5,
                color = customGreen,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "터치 한 번으로 빠르게 명함 전달",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = customGreen
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                onClick = onLoginClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(44.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = customGreen
                ),
                border = BorderStroke(1.dp, customGreen)
            ) {
                Text(text = "로그인")
            }
            Button(
                onClick = onSignUpClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = customGreen,
                    contentColor = Color.White
                )
            ) {
                Text(text = "회원가입")
            }
        }
        Spacer(modifier = Modifier.height(1.dp))
        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "계정이 기억나지 않나요?",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.width(0.3.dp))
            TextButton(onClick = { /* Add action for '계정 찾기' 구현 안하기러 함 */ }) {
                Text(
                    text = "계정 찾기",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = customGreen,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFirstLoginScreen() {
    FirstLoginScreen()
}
