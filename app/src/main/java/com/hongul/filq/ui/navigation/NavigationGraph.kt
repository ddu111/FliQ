package com.hongul.filq.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hongul.filq.R
import com.hongul.filq.ui.CardShareViewModelProvider
import com.hongul.filq.ui.calendar.CalendarScreen
import com.hongul.filq.ui.contact.ContactScreen
import com.hongul.filq.ui.customize.BusinessCardGenerateScreen
//import com.hongul.filq.ui.customize.page.FaceBookURLPage
//import com.hongul.filq.ui.customize.page.InstaGramURLPage
//import com.hongul.filq.ui.customize.page.XURLPage
//import com.hongul.filq.ui.customize.page.YoutubeURLPage
import com.hongul.filq.ui.home.HomeScreen
import com.hongul.filq.ui.home.StickerChangeRoute
import com.hongul.filq.ui.home.StickerChangeScreen
import com.hongul.filq.ui.share.CardShareRoute
import com.hongul.filq.ui.share.CardShareScreen
import com.hongul.filq.ui.share.CardShareViewModel
// SignUpScreens 부분에 필요한 임ㅍ트
import com.hongul.filq.ui.login.SignUpScreens
import com.hongul.filq.ui.login.PhoneInputScreen
import com.hongul.filq.ui.login.EmailInputScreen
import com.hongul.filq.ui.login.PasswordInputScreen
import com.hongul.filq.ui.login.NameInputScreen
import com.hongul.filq.ui.login.SignUpCompleteScreen
import com.hongul.filq.ui.more.MoreScreen

sealed class NavItem(val route: String, val title: String, @DrawableRes val icon: Int) {
    data object Home : NavItem("home", "내 명함", R.drawable.ic_nav_home)
    data object Contact : NavItem("contact", "연락처", R.drawable.ic_nav_contact)
    data object Search : NavItem("search", "명함 찾기", R.drawable.ic_nav_search)
    data object Calendar : NavItem("calendar", "캘린더", R.drawable.ic_nav_calendar)
    data object More : NavItem("more", "더보기", R.drawable.ic_nav_more)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    Column(Modifier.fillMaxSize()) {
        var showNavigationBar by remember { mutableStateOf(false) }

        NavHost(
            navController = navController,
            startDestination = NavItem.Home.route,
            modifier = Modifier.weight(1f)
        ) {
            composable(NavItem.Home.route) {
                showNavigationBar = true
                HomeScreen(navigator = navController)
            }
            composable(NavItem.Contact.route) {
                showNavigationBar = true
                ContactScreen()
            }
            composable(NavItem.Search.route) {
                showNavigationBar = true
                PlaceHolder("search")
            }
            composable(NavItem.Calendar.route) {
                showNavigationBar = true
                CalendarScreen()
            }
            composable(NavItem.More.route) {
                showNavigationBar = true
                MoreScreen()
            }
            composable<StickerChangeRoute> {
                showNavigationBar = false
                val route = it.toRoute<StickerChangeRoute>()
                StickerChangeScreen(route.cardId, navController)
            }
            composable<CardShareRoute> {
                showNavigationBar = false
                val route = it.toRoute<CardShareRoute>()
                val viewModel: CardShareViewModel = viewModel(
                    factory = CardShareViewModelProvider.factory(route.cardId),
                )
                CardShareScreen(viewModel)
            }
            composable("generate") {
                showNavigationBar = false
                BusinessCardGenerateScreen(navController)
            }
            //회원가입 화면
            composable(SignUpScreens.PhoneInput.route) {
                showNavigationBar = false
                PhoneInputScreen(
                    onNext = { navController.navigate(SignUpScreens.EmailInput.route) }
                )
            }
            composable(SignUpScreens.EmailInput.route) {
                showNavigationBar = false
                EmailInputScreen(
                    onNext = { navController.navigate(SignUpScreens.NameInput.route) }
                )
            }
            composable(SignUpScreens.NameInput.route) {
                showNavigationBar = false
                NameInputScreen(
                    onNext = { navController.navigate(SignUpScreens.PasswordInput.route) }
                )
            }
            composable(SignUpScreens.PasswordInput.route) {
                showNavigationBar = false
                PasswordInputScreen(
                    onNext = { navController.navigate(SignUpScreens.Complete.route) }
                )
            }

            composable(SignUpScreens.Complete.route) {
                showNavigationBar = false
                SignUpCompleteScreen(
                    onNext = { navController.navigate("login_screen") } // 로그인 화면으로 인동
                )
            }
        }

        if (showNavigationBar) {
            BottomNavigation(navController = navController)
        }
    }
}


@Composable
private fun PlaceHolder(title: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Text(text = title)
        }
    )
}