package com.nyang.dayFlower.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nyang.dayFlower.presentation.feature.home.HomeScreen
import com.nyang.dayFlower.presentation.feature.locker.LockerScreen
import com.nyang.dayFlower.presentation.feature.mainFlower.MainFlowerScreen
import com.nyang.dayFlower.presentation.feature.profile.ProfileScreen
import com.nyang.dayFlower.presentation.feature.search.SearchScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screens.MainFlower.route
    ) {

        composable(route = Screens.MainFlower.route) {
            MainFlowerScreen(onNavigate = { navController.navigateSingleTopTo(it.route) })
        }
    }
}

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {

        composable(route = Screens.Home.route) {
            HomeScreen()
        }
        composable(route = Screens.Search.route) {
            SearchScreen()
        }
        composable(route = Screens.Locker.route) {
            LockerScreen()
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen()
        }

    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id,
        ) {
            //모든화면을 닫은 후 새 화면 열기
            inclusive = true
            //하단 탐색 항목 간 전환시 상태와 백 스택이 올바르게 저장
            saveState = true
        }
        //동일한 항목을 선택할때 여러번 복사를 방지
        launchSingleTop = true
        //하단 탐색 항목 간 전환시 상태와 백 스택이 복원
        restoreState = true
    }
}

fun NavHostController.onNavigateNext(
    nowScreen: Screens,
    argument: String? = null,
) {

    val destination =
        if (argument != null) nowScreen.routeByArgs(argument) else nowScreen.route

    this.navigateSingleTopTo(destination)

}