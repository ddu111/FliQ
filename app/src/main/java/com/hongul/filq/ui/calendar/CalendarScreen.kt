package com.hongul.filq.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*
import androidx.compose.ui.res.painterResource
import com.hongul.filq.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    val today = LocalDate.now()
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    val schedules = remember { mutableStateMapOf<LocalDate, MutableList<Schedule>>() }
    var selectedSchedule by remember { mutableStateOf<Schedule?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // 기본 참여자
    val contacts = remember {
        mutableStateListOf(
            Contact(id = 1, name = "홍츄핑구", email = "pinggu@example.com"),
            Contact(id = 2, name = "홍츄핑", email = "ping@example.com")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "캘린더",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                // 캘린더 헤더
                CalendarHeader(currentMonth) { isNext ->
                    currentMonth = if (isNext) {
                        currentMonth.plusMonths(1)
                    } else {
                        currentMonth.minusMonths(1)
                    }
                }

                // 캘린더 본문
                CalendarBody(
                    currentMonth = currentMonth,
                    today = today,
                    selectedDate = selectedDate,
                    schedules = schedules
                ) { date ->
                    selectedDate = date
                }

                // 구분선
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp) // 좌우 여백 추가로 중앙 정렬
                )

                // 일정 목록
                ScheduleList(
                    selectedDate = selectedDate,
                    schedules = schedules,
                    onScheduleClick = { schedule ->
                        selectedSchedule = schedule // 일정 클릭 시 편집 화면으로 이동
                    },
                    onShareClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("URL이 복사되었습니다.")
                        }
                    }
                )
            }

            // 일정 수정 화면
            if (selectedSchedule != null) {
                ScheduleEditScreen(
                    schedule = selectedSchedule!!,
                    contacts = contacts, // 참여자 전달
                    defaultColor = Color.Red.copy(alpha = 0.6f),
                    onClose = { selectedSchedule = null },
                    onDelete = {
                        schedules[selectedDate]?.remove(selectedSchedule)
                        selectedSchedule = null
                    },
                    onColorChange = { color ->
                        selectedSchedule?.color = color
                    }
                )
            }

            // Snackbar (공유 버튼 클릭 시 화면 중앙에서 약간 아래로 표시)
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter) // 화면 하단 중앙 정렬
                    .padding(bottom = 65.dp) // 하단 여백 추가
            )

            // 일정 추가 필드 (스케줄 수정 화면이 활성화되지 않은 경우에만 표시)
            if (selectedDate != null && selectedSchedule == null) {
                ScheduleInput(
                    selectedDate = selectedDate,
                    defaultColor = Color.Red.copy(alpha = 0.6f),
                    schedules = schedules,
                    onAddSchedule = {
                        // 추가 로직 구현 가능
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter) // 하단 고정
                        .fillMaxWidth()
                )
            }
        }
    }
}




// 명함 데이터 클래스
data class Contact(val id: Int, val name: String, val email: String)

// 일정 데이터 클래스
data class Schedule(
    val id: String,
    var title: String,
    var color: Color,
    val participants: MutableList<Contact>
)

@Composable
fun CalendarHeader(currentMonth: YearMonth, onMonthChange: (Boolean) -> Unit) {
    val monthDisplayName = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) // 영어로 월 이름 표시
    val year = currentMonth.year

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp), // 상하/좌우 여백 조정
        horizontalArrangement = Arrangement.SpaceBetween, // 좌우 화살표와 텍스트 간 간격 배치
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onMonthChange(false) }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_ios), // 이전 달 아이콘
                contentDescription = "이전 달",
                tint = Color.Gray // 화살표 색상
            )
        }

        Text(
            text = "$monthDisplayName $year", // 월 이름 영어로 표시
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1B5E20), // 중앙 텍스트 녹색
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        IconButton(onClick = { onMonthChange(true) }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_forward_ios), // 다음 달 아이콘
                contentDescription = "다음 달",
                tint = Color.Gray // 화살표 색상
            )
        }
    }

}


// 캘린더 본문 수정
@Composable
fun CalendarBody(
    currentMonth: YearMonth,
    today: LocalDate,
    selectedDate: LocalDate?,
    schedules: Map<LocalDate, List<Schedule>>,
    onDateSelected: (LocalDate) -> Unit
) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7
    val lastDayOfMonth = (firstDayOfMonth + daysInMonth - 1) % 7

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 요일 헤더
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
            daysOfWeek.forEach { day ->
                Text(text = day, fontSize = 12.sp, color = Color.Gray)
            }
        }

        // 날짜 표시
        val days = (1..daysInMonth).toList()
        val emptyDaysBefore = List(firstDayOfMonth) { "" }
        val emptyDaysAfter = List(6 - lastDayOfMonth) { "" }
        val dayChunks = (emptyDaysBefore + days.map { it.toString() } + emptyDaysAfter).chunked(7)

        dayChunks.forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                week.forEach { day ->
                    val date = day.toIntOrNull()?.let { currentMonth.atDay(it) }
                    val isToday = date == today
                    val isSelected = date == selectedDate

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = if (isSelected && selectedDate != null) Color(0xFF1B5E20) else Color.Transparent,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable { date?.let(onDateSelected) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day,
                                fontSize = 16.sp,
                                fontWeight = when {
                                    isToday -> FontWeight.Bold
                                    else -> FontWeight.Normal
                                },
                                color = when {
                                    isSelected -> Color.White // 선택된 날짜는 흰색
                                    isToday -> Color(0xFF1B5E20) // 오늘 날짜는 녹색
                                    else -> Color.Black // 기본 검정색
                                }
                            )
                        }
                        // 날짜 아래 일정 색상 점
                        if (date != null && schedules.containsKey(date)) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                schedules[date]!!.take(3).forEach { schedule ->
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .background(schedule.color, shape = CircleShape)
                                            .padding(2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ScheduleList(
    selectedDate: LocalDate?,
    schedules: Map<LocalDate, List<Schedule>>,
    onScheduleClick: (Schedule) -> Unit, // 일정 클릭 동작
    onShareClick: () -> Unit // 공유 버튼 클릭 동작
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (selectedDate != null) {
                Text(
                    text = "${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

                val daySchedules = schedules[selectedDate]
                if (daySchedules.isNullOrEmpty()) {
                    Text(
                        text = "일정이 없습니다",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                } else {
                    daySchedules.forEach { schedule ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onScheduleClick(schedule) } // 일정 클릭 시 편집 화면으로 이동
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically // 요소를 수직 중앙 정렬
                        ) {
                            // 일정 색상 원
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(schedule.color, shape = CircleShape) // 색상 원
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp) // 텍스트 간격 조정
                            ) {
                                Text(
                                    text = schedule.title,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                                if (schedule.participants.isNotEmpty()) {
                                    Text(
                                        text = "참여자: ${schedule.participants.joinToString(", ") { "${it.name} (${it.email})" }}",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }

                            // 공유 아이콘
                            IconButton(
                                onClick = onShareClick // 공유 버튼 클릭 시 동작
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.share), // 공유 아이콘 리소스
                                    contentDescription = "공유",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun ToastMessage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Black.copy(alpha = 0.8f), shape = RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleInput(
    selectedDate: LocalDate?,
    defaultColor: Color,
    schedules: MutableMap<LocalDate, MutableList<Schedule>>,
    onAddSchedule: (Schedule) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = {
                Text(text = "${selectedDate?.monthValue}월 ${selectedDate?.dayOfMonth}일에 일정 추가")
            },
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFF0F0F0), RoundedCornerShape(24.dp)),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {
                if (inputText.isNotBlank() && selectedDate != null) {
                    val newSchedule = Schedule(
                        id = UUID.randomUUID().toString(),
                        title = inputText,
                        color = defaultColor,
                        participants = mutableListOf()
                    )
                    schedules.getOrPut(selectedDate) { mutableListOf() }.add(newSchedule)
                    onAddSchedule(newSchedule)
                    inputText = "" // 입력 초기화
                }
            },
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF1B5E20), shape = CircleShape)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "일정 추가", tint = Color.White)
        }
    }
}



@Composable
fun ColorPickerDialog(
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    // 맨 왼쪽 위 색상을 기본값으로 설정
    val defaultBasicColor = Color.Red.copy(alpha = 0.6f)
    var currentColor by remember { mutableStateOf(defaultBasicColor) }

    val basicColors = listOf(
        defaultBasicColor,
        Color(0xFFFF7F50).copy(alpha = 0.6f), // Coral
        Color(0xFFFFA500).copy(alpha = 0.6f), // Orange
        Color.Yellow.copy(alpha = 0.6f),
        Color(0xFFADFF2F).copy(alpha = 0.6f), // Green Yellow
        Color.Green.copy(alpha = 0.6f),
        Color.Cyan.copy(alpha = 0.6f),
        Color.Blue.copy(alpha = 0.6f),
        Color(0xFF8A2BE2).copy(alpha = 0.6f), // Blue Violet
        Color.Magenta.copy(alpha = 0.6f),
        Color(0xFFFF69B4).copy(alpha = 0.6f), // Hot Pink
        Color(0xFFA52A2A).copy(alpha = 0.6f), // Brown
        Color.Gray.copy(alpha = 0.6f),
        Color.DarkGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.6f),
        Color(0xFF556B2F).copy(alpha = 0.6f), // Dark Olive Green
        Color(0xFF708090).copy(alpha = 0.6f), // Slate Gray
        Color(0xFFB0C4DE).copy(alpha = 0.6f), // Light Steel Blue
        Color(0xFFD2B48C).copy(alpha = 0.6f), // Tan
        Color.Black.copy(alpha = 0.6f)
    )

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("색상 선택", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 기본 색상 버튼
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    basicColors.chunked(5).forEach { rowColors ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            rowColors.forEach { color ->
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(color, shape = CircleShape)
                                        .clickable { currentColor = color }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 선택된 색상 미리보기
                Text("선택된 색상", fontSize = 12.sp, fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20))
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(currentColor, shape = CircleShape)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onColorSelected(currentColor) // 선택된 색상을 전달
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B5E20), // 버튼 배경색
                    contentColor = Color.White // 텍스트 색상
                ),
                shape = RoundedCornerShape(20), // 버튼을 둥글게 만듦
                modifier = Modifier
                    .height(40.dp) // 버튼 높이
                    .padding(horizontal = 5.dp) // 버튼 여백 줄임
            ) {
                Text(
                    "확인",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold // 강조 텍스트
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // 버튼 배경 투명
                    contentColor = Color(0xFF1B5E20) // 텍스트 색상 녹색
                ),
                shape = RoundedCornerShape(20), // 둥근 모양 유지
                elevation = ButtonDefaults.buttonElevation(0.dp), // 버튼 그림자 제거
                modifier = Modifier
                    .height(40.dp) // 버튼 높이
                    .padding(horizontal = 5.dp) // 버튼 여백 줄임
            ) {
                Text(
                    "취소",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal // 일반 텍스트
                )
            }
        },
        containerColor = Color.White // 배경 색상을 흰색으로 설정
    )
}




@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}





@Preview(showBackground = true)
@Composable
fun ColorPickerDialogPreview() {
    ColorPickerDialog(onColorSelected = {}, onDismiss = {})
}

