package com.hongul.filq.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hongul.filq.ui.theme.PrimaryDark

@Composable
fun BottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(72.dp)
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color(0xFFD7D7D7),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1f,
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val navItemList = listOf(
            NavItem.Home,
            NavItem.Contact,
            NavItem.Search,
            NavItem.Calendar,
            NavItem.More
        )

        for (item in navItemList) {
            BottomNavigationItem(
                icon = item.icon,
                title = item.title,
                isSelected = item.route == navBackStackEntry?.destination?.route,
            ) {
                navController.navigate(item.route) {
                    navController.graph.startDestinationRoute?.let {
                        popUpTo(it) { saveState = true }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}

@Composable
fun BottomNavigationItem(
    @DrawableRes icon: Int,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val baseColor = if (isSelected) PrimaryDark else Color(0xFFBEBEBE)

    Column(
        modifier = Modifier
            .size(48.dp)
            .border(0.dp, Color.Transparent, CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            ImageVector.vectorResource(id = icon),
            contentDescription = title,
            modifier = Modifier.scale(3f / 4f),
            tint = baseColor
        )
        Text(
            title,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = baseColor
        )
    }
}