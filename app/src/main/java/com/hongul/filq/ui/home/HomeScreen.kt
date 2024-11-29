package com.hongul.filq.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.hongul.filq.R
import com.hongul.filq.model.Avatar
import com.hongul.filq.model.BusinessCard
import com.hongul.filq.model.SNS
import com.hongul.filq.ui.HomeViewModelProvider
import com.hongul.filq.ui.share.CardShareRoute
import com.hongul.filq.ui.theme.PrimaryDeepDark
import com.hongul.filq.util.permissionList

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navigator: NavController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val cards by viewModel.businessCards.collectAsState()
    val shareRequest by viewModel.shareRequest.collectAsState()
    val pagerState = rememberPagerState(initialPage = 0) { cards.size+1 }
    val permissionState = rememberMultiplePermissionsState(
        permissions = permissionList
    )

    var isBottomSheetOpen by remember { mutableStateOf(false) }
    var isAlertDialogOpen by remember { mutableStateOf(false) }

    // TODO: 권한 부여 받을 시 startDiscovering 재실행
    DisposableEffect(Unit) {
        if(!permissionState.allPermissionsGranted) {
            permissionState.launchMultiplePermissionRequest()
        }
        viewModel.startDiscovering(context)

        onDispose {
            viewModel.stopDiscovering(context)
        }
    }

    LaunchedEffect(shareRequest) {
        if(shareRequest != null) {
            Log.d("shareRequest", shareRequest.toString())
            isAlertDialogOpen = true
        }
    }

    if(isAlertDialogOpen) {
        AlertDialog(
            title = { Text("${shareRequest!!.second}님이 명함을 공유중이에요.") },
            text = { Text("명함을 확인해보세요.") },
            onDismissRequest = {
                isAlertDialogOpen = false
            },
            confirmButton = {
                TextButton(onClick = {
                    isAlertDialogOpen = false
                    // TODO: 이름 처리
                    viewModel.requestConnection(context, "")
                }) {
                    Text("수락")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isAlertDialogOpen = false
                }) {
                    Text("거절")
                }
            }
        )
    }

    if(isBottomSheetOpen) {
        ChangeProfileBottomSheet(
            onClickPhoto = { },
            onClickGallery = { },
            onClickSticker = {
                isBottomSheetOpen = false
                navigator.navigate(
                    StickerChangeRoute(cardId = cards[pagerState.currentPage].id)
                )
            },
            onDismissRequest = { isBottomSheetOpen = false }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            if (pagerState.currentPage+1 < pagerState.pageCount) {
                ExtendedFloatingActionButton(
                    containerColor = Color.White,
                    contentColor = PrimaryDeepDark,
                    text = {
                        Text(
                            text = "전달 하기",
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = "명함 전달"
                        )
                    },
                    modifier = Modifier.scale(0.8f),
                    onClick = {
                        if(pagerState.currentPage+1 < pagerState.pageCount) {
                            navigator.navigate(
                                CardShareRoute(cardId = cards[pagerState.currentPage].id)
                            )
                        }
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: Pager content 영역 밖에서 swipe 안되는 문제 해결하기
            PagerOffsets(pagerState.pageCount, pagerState.currentPage)
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.Top
            ) { page ->
                when(page+1) {
                    pagerState.pageCount -> EmptyBusinessCard(onClick = {
                        viewModel.insertCard(
                            BusinessCard(
                                name = "홍길동",
                                title = "나야, 홍길동",
                                phoneNumber = "010-3213-5392",
                                email = "gildong@gmail.com",
                                address = "대구광역시 달서구 신당동",
                                organization = "계명대학교",
                                department = "컴퓨터공학부",
                                position = "홍얼홍얼",
                                sns = listOf(
                                    SNS.Instagram("https://www.instagram.com/gildong")
                                ),
                                imagePath = "image.png",
                                avatar = Avatar(),
                                introduction = "안녕하세여."
                            )
                        )
                    })
                    else -> BusinessCardView(
                        businessCard = cards[page],
                        onClickCardImage = { viewModel.delete(cards[page])},
                        onClickProfileImage = { isBottomSheetOpen = true }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeProfileBottomSheet(
    onClickPhoto: () -> Unit,
    onClickGallery: () -> Unit,
    onClickSticker: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "프로필 사진을 등록해보세요!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            ListItem(
                headlineContent = { Text("사진 촬영하기") },
                leadingContent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_camera),
                        contentDescription = "사진 촬영하기",
                        modifier = Modifier.size(32.dp)
                    )
                },
                modifier = Modifier.clickable(onClick = onClickPhoto)
            )
            ListItem(
                headlineContent = { Text("앨범에서 가져오기") },
                leadingContent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_photo),
                        contentDescription = "앨범에서 가져오기",
                        modifier = Modifier.size(32.dp)
                    )
                },
                modifier = Modifier.clickable(onClick = onClickGallery)
            )
            ListItem(
                headlineContent = { Text("스티커 사용하기") },
                leadingContent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_sticker),
                        contentDescription = "스티커 사용하기",
                        modifier = Modifier.size(32.dp)
                    )
                },
                modifier = Modifier.clickable(onClick = onClickSticker)
            )
        }
    }
}

@Composable
fun PagerOffsets(pageCount: Int, currentPage: Int) {
    Row {
        for (i in 0 until pageCount) {
            Text(
                text = "●",
                fontSize = 8.sp,
                color = if (currentPage == i) Color.Gray else Color.LightGray,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}