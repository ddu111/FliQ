package com.hongul.filq.ui.customize

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hongul.filq.R
import com.hongul.filq.ui.customize.page.BasicInformationPage
import com.hongul.filq.ui.customize.page.BusinessCardPhotoGuidePage
import com.hongul.filq.ui.customize.page.BusinessCardPreviewPage
import com.hongul.filq.ui.customize.page.BusinessCardTemplatePage
import com.hongul.filq.ui.customize.page.ChangeTextColorPage
import com.hongul.filq.ui.customize.page.LetterPositionPage
import com.hongul.filq.ui.customize.page.OrganizationInfoPage
import com.hongul.filq.ui.customize.page.PlusSnsPage
import com.hongul.filq.ui.customize.page.RegisterBusinessCardPage
import com.hongul.filq.ui.customize.page.SelectBusinessCardStylePage
import com.hongul.filq.ui.customize.page.SelectPhotoPage
import com.hongul.filq.ui.customize.page.SocialInfoPage
import com.hongul.filq.ui.customize.page.StartPage
import com.hongul.filq.ui.customize.page.URLPage
import com.hongul.filq.ui.customize.page.saveImageToGallery
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessCardGenerateScreen(navigator: NavHostController) {

    val ps = rememberPagerState(initialPage = 0) { 15 }//총 페이지 개수 바꾸기
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var selectedTemplateImageRes by remember { mutableStateOf<Int?>(null) }
    var currentSNS by remember { mutableStateOf<String?>(null) } // 현재 선택된 SNS 이름

    // 상위에서 공유되는 상태
    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }
    var selectedColor by remember { mutableStateOf<Color?>(null) }
    var selectedAlignment by remember { mutableStateOf<Alignment>(Alignment.BottomStart) }

    val context = LocalContext.current
    // 페이지 간 이동에 사용될 상태
    var currentPage by remember { mutableStateOf(0) }

    // ps.currentPage 값에 따라 title을 업데이트
    LaunchedEffect(ps.currentPage, currentSNS) {
        title = when {
            currentSNS != null -> "$currentSNS " // SNS URL 등록 타이틀
            else -> when (ps.currentPage) {
                0 -> ""
                1 -> "명함 사진 불러오기란?"
                2 -> "명함 생성"
                3 -> "명함 생성"
                4 -> "명함 생성"
                5 -> "명함 생성"
                6 -> "명함 생성"
                9 -> "글자 색 바꾸기"
                10 -> "명함 등록"
                8 -> "글자 위치 바꾸기"
                else -> "명함 생성"
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        when {
                            currentSNS != null -> currentSNS = null // URL 입력 화면에서 뒤로가기
                            else -> {
                                when (ps.currentPage) {
                                    0 -> {
                                        navigator.popBackStack() // 첫 페이지에서 네비게이션 뒤로
                                    }

                                    1 -> {
                                        scope.launch { ps.animateScrollToPage(0) } // "명함 사진 불러오기란?"에서 첫 페이지로
                                    }

                                    2 -> {
                                        scope.launch { ps.scrollToPage(0) }
                                    }

                                    in 3..5 -> {
                                        scope.launch { ps.animateScrollToPage(ps.currentPage - 1) } // 일반 뒤로가기
                                    }

                                    6 -> {
                                        scope.launch { ps.scrollToPage(4) } // URL 페이지에서 SNS 추가로 이동
                                    }
                                    7 -> {
                                        Log.d("Navigation", "Navigating from page 7 to page 6")
                                        scope.launch { ps.animateScrollToPage(6) } // 7번 페이지에서 6번 페이지로 이동
                                    }

                                    8 -> {
                                        Log.d("Navigation", "Navigating from page 8 to page 7")
                                        scope.launch { ps.animateScrollToPage(7) } // 8번 페이지에서 7번 페이지로 이동
                                    }

                                    9 -> {
                                        Log.d("Navigation", "Navigating from page 9 to page 8")
                                        scope.launch { ps.animateScrollToPage(8) } // 9번 페이지에서 8번 페이지로 이동
                                    }

                                    10 -> {
                                        Log.d("Navigation", "Navigating from page 10 to page 9")
                                        scope.launch { ps.animateScrollToPage(9) } // 10번 페이지에서 9번 페이지로 이동
                                    }

                                    11 -> {
                                        Log.d("Navigation", "Navigating from page 11 to page 6")
                                        scope.launch { ps.scrollToPage(6) } // 11번 페이지에서 6번 페이지로 이동
                                    }

                                    12 -> {
                                        Log.d("Navigation", "Navigating from page 12 to page 11")
                                        scope.launch { ps.animateScrollToPage(11) } // 12번 페이지에서 11번 페이지로 이동
                                    }

                                    else -> {
                                        Log.e("Navigation", "Unexpected page number: ${ps.currentPage}")
                                        scope.launch { ps.animateScrollToPage(0) } // 기본적으로 첫 페이지로 이동
                                    }
                                }
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "뒤로 가기"
                        )
                    }
                },
                actions = {
                    // 4번 페이지에만 "건너뛰기" 버튼 표시
                    if (ps.currentPage == 4) {
                        TextButton(onClick = {
                            scope.launch { ps.scrollToPage(6) } // 6번 페이지로 이동
                        },
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Text(
                                text = "건너뛰기",
                                color = Color(0xFF125422),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (currentSNS != null) {
            // URL 입력 화면
            URLPage(
                snsName = currentSNS!!,
                onBack = { currentSNS = null }, // SNS 선택 화면으로 돌아감
                onRegisterClick = { url ->
                    Log.d("URL 등록", "$currentSNS: $url")
                    currentSNS = null // 등록 후 SNS 선택 화면으로 돌아감
                }
            )
        } else {
            // 일반 페이지
            HorizontalPager(
                state = ps,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> StartPage(
                        onCreate = { scope.launch { ps.scrollToPage(2) } },
                        onGuideClick = { scope.launch { ps.animateScrollToPage(1) } },
                        onUpload = {}
                    )

                    1 -> BusinessCardPhotoGuidePage(
                        onBackClick = { scope.launch { ps.animateScrollToPage(0) } }
                    )

                    2 -> BasicInformationPage(
                        onNext = { scope.launch { ps.animateScrollToPage(3) } }
                    )

                    3 -> OrganizationInfoPage(
                        onNext = { scope.launch { ps.animateScrollToPage(4) } }
                    )

                    4 -> SocialInfoPage(
                        onNext = { scope.launch { ps.scrollToPage(6) } }, // "다음" 버튼 클릭 시 6번 페이지로 이동
                        onAddSNS = { scope.launch { ps.scrollToPage(5) } }
                    )

                    5 -> PlusSnsPage(
                        onBack = { scope.launch { ps.animateScrollToPage(4) } },
                        onSelectSNS = { snsType ->
                            currentSNS = snsType // SNS 선택 시 URL 입력 화면으로 전환
                        }
                    )

                    6 -> SelectBusinessCardStylePage(
                        onNavigateToBusinessCard = { scope.launch { ps.scrollToPage(11) } },
                        onNavigateToPersonalCard = { scope.launch { ps.animateScrollToPage(7) } },
                        onBack = {
                            if (ps.currentPage == 6) { // 현재 페이지가 6번인지 확인
                                scope.launch { ps.scrollToPage(4) } // 4번 페이지로 이동
                            } else {
                                Log.e("Navigation", "Unexpected navigation attempt")
                            }
                        }
                    )

                    7 -> SelectPhotoPage(
                        onNext = { scope.launch { ps.animateScrollToPage(8) } },
                        onImageSelected = { image ->
                            selectedImage = image // 선택된 이미지를 상위 상태에 저장
                            Log.d("SelectPhotoPage", "이미지가 선택되었습니다.")
                        }
                    )

                    8 -> LetterPositionPage(
                        onNext = { scope.launch { ps.animateScrollToPage(9) } },
                        onBack = { scope.launch { ps.animateScrollToPage(7) } },
                                onAlignmentSelected = { alignment -> selectedAlignment = alignment }
                    )

                    9 -> ChangeTextColorPage(
                        onNextClick = { scope.launch { ps.animateScrollToPage(10) } },
                        onColorSelected = { color -> selectedColor = color }
                    )

                    10 -> RegisterBusinessCardPage(
                        onCompleteClick = {
                            // 최종 등록 처리, 예를 들어 갤러리에 저장 등
                            selectedImage?.let { saveImageToGallery(context, it) }
                            scope.launch {
                                Log.d("BusinessCardPreviewScreen", "완료 버튼 클릭됨")
                                ps.scrollToPage(0) // 완료 버튼 클릭 시 첫 페이지로 이동
                            }
                        },
                        selectedImage = selectedImage,
                        selectedColor = selectedColor ?: Color.Black,
                        selectedAlignment = selectedAlignment
                    )

                    11 -> {
                        title = "명함 생성"
                        BusinessCardTemplatePage(
                            templates = listOf(
                                R.drawable.bc1,
                                R.drawable.bc2,
                                R.drawable.bc3,
                                R.drawable.bc4,
                                R.drawable.bc5,
                                R.drawable.bc6,
                                R.drawable.bc7,
                                R.drawable.bc8,
                                R.drawable.bc9,
                                R.drawable.bc10,
                                R.drawable.bc11,
                                R.drawable.bc12,
                                R.drawable.bc13,
                                R.drawable.bc14,
                                R.drawable.bc15,
                                R.drawable.bc16,
                                R.drawable.bc17,
                                R.drawable.bc18,
                                R.drawable.bc19,
                                R.drawable.bc20,
                                R.drawable.bc21,
                                R.drawable.bc22,
                                R.drawable.bc23,
                                R.drawable.bc24,
                                R.drawable.bc25,
                                R.drawable.bc26,
                                R.drawable.bc27,
                                R.drawable.bc28,
                                R.drawable.bc29,
                                R.drawable.bc30,
                                R.drawable.bc31,
                                R.drawable.bc32,
                                R.drawable.bc33,
                                R.drawable.bc34,
                            ),
                            onTemplateSelected = { selectedTemplate ->
                                scope.launch {
                                    ps.scrollToPage(12) // 다음 페이지로 이동
                                    selectedTemplateImageRes = selectedTemplate // 선택된 템플릿 저장
                                }
                            },
                            onBackClick = {
                                scope.launch {
                                    ps.scrollToPage(10) // 이전 페이지로 이동
                                }
                            }
                        )
                    }

                    12 -> {
                        title = "명함 생성"
                        selectedTemplateImageRes?.let { imageRes -> // 선택된 이미지 리소스가 있을 경우
                            BusinessCardPreviewPage(
                                backgroundRes = imageRes, // 선택된 템플릿 이미지 리소스 전달
                                onCompleteClick = {
                                    scope.launch {
                                        Log.d("BusinessCardPreviewScreen", "완료 버튼 클릭됨")
                                        ps.scrollToPage(0) // 완료 버튼 클릭 시 첫 페이지로 이동
                                    }
                                },
                                onBackClick = {
                                    scope.launch {
                                        ps.animateScrollToPage(11) // 이전 페이지로 이동
                                    }
                                }
                            )
                        } ?: Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center // 중앙에 텍스트 표시
                        ) {
                            // 선택된 이미지 리소스가 없을 때
                            Text(
                                text = "템플릿이 선택되지 않았습니다.\n다시 시도해주세요.",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}