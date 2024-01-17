package ru.shtykin.pdauthapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    startScreenRoute: String,
    navHostController: NavHostController,
    authScreenContent: @Composable () -> Unit,
    mainScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = startScreenRoute
    ) {
        composable(Screen.Auth.route) {
            authScreenContent()
        }
        composable(Screen.Main.route) {
            mainScreenContent()
        }
    }

}