package com.hongul.filq.ui.share

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.hongul.filq.R
import com.hongul.filq.ui.CardShareViewModelProvider
import kotlinx.serialization.Serializable

@Serializable
data class CardShareRoute(
    val cardId: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardShareScreen(
    viewModel: CardShareViewModel = viewModel(
        factory = CardShareViewModelProvider.Factory,
    )
) {
    val context = LocalContext.current
    val card by viewModel.selectedCard.collectAsState()

    DisposableEffect(card) {
        if(card != null) {
            viewModel.startAdvertising(
                context = context,
                onAfterSending = { reply ->
                    if (reply == "1")
                        Toast.makeText(context, "명함을 전달했습니다!", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "명함 전달에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            )
        }

        onDispose {
            viewModel.stopAdvertising(context)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Color.Black,
            contentColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    var showQR by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "QR 표시",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Switch(checked = showQR, onCheckedChange = { showQR = it })
                    }

                    ElevatedCard(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        val cardImage = ImageBitmap.imageResource(R.drawable.test_card)
                        val qrImage = ImageBitmap.imageResource(R.drawable.test_qr)

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text("명함 공유하기")

                            Image(
                                cardImage,
                                contentDescription = "명함 이미지",
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentScale = ContentScale.FillWidth
                            )

                            AnimatedVisibility(showQR) {
                                Image(
                                    viewModel.qrCode.asImageBitmap(),
                                    contentDescription = "QR 코드",
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.FillWidth
                                )
                            }
                        }
                    }
                }

                Text("전송할 기기와 서로 마주 대세요.", color = Color.White)
            }
        }
    }
}