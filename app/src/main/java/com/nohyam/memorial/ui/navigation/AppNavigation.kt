package com.nohyam.memorial.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nohyam.memorial.ui.route.HomeRoute
import com.nohyam.memorial.ui.route.QuizRoute
import com.nohyam.memorial.ui.route.RecordRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeRoute(onQuizClick = {
                navController.navigate("quiz")
            }, onRecordClick = { navController.navigate("record") })
        }

        composable("record") {
            RecordRoute(onBackClick = { navController.popBackStack() })
        }
        composable("quiz") {
            QuizRoute(onHomeClick = {navController.popBackStack()})
        }
    }
}