package com.hongul.filq.ui.customize.page

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hongul.filq.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBusinessCardPage(
    onCompleteClick: () -> Unit,
    selectedImage: Bitmap?,
    selectedColor: Color,
    selectedAlignment: Alignment
) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // 중간 이미지와 텍스트
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f) // 가로 크기를 약간 좁힘
                    .height(240.dp), // 세로 크기를 고정
                contentAlignment = Alignment.Center
            ) {
                // 이미지 표시
                selectedImage?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "명함 이미지",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.bc4),
                    contentDescription = "명함 이미지",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )


                // 텍스트 표시
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(23.dp), // 이미지 안쪽에 여백 추가
                    verticalArrangement = when (selectedAlignment) {
                        Alignment.BottomStart, Alignment.BottomEnd -> Arrangement.Bottom
                        Alignment.TopStart, Alignment.TopEnd -> Arrangement.Top
                        else -> Arrangement.Center
                    },
                    horizontalAlignment = when (selectedAlignment) {
                        Alignment.BottomStart, Alignment.TopStart -> Alignment.Start
                        Alignment.BottomEnd, Alignment.TopEnd -> Alignment.End
                        else -> Alignment.CenterHorizontally
                    }
                ){
                    Text(
                        text = "홍얼홍얼",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = selectedColor,
                    )
                    Text(
                        text = "캡스톤(1)",
                        fontSize = 14.sp,
                        color = selectedColor,
                    )
                    Text(
                        text = "010.0000.0000",
                        fontSize = 14.sp,
                        color = selectedColor,
                    )
                    Text(
                        text = "xxx@stu.kmu.ac.kr",
                        fontSize = 14.sp,
                        color = selectedColor,
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 완료 버튼
            Button(
                onClick = onCompleteClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF125422))
            ) {
                Text(
                    text = "완료",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }

