package hu.tb.jwt_auth.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import hu.tb.jwt_auth.presentation.screens.home.navigation.homeScreenNavigation
import hu.tb.jwt_auth.presentation.screens.home.navigation.navigateToHomeScreen
import hu.tb.jwt_auth.presentation.screens.login.navigation.LOGIN_SCREEN_ROUTE
import hu.tb.jwt_auth.presentation.screens.login.navigation.loginScreenNavigation

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LOGIN_SCREEN_ROUTE) {
        homeScreenNavigation(

        )

        loginScreenNavigation(
            navigateToHomeScreen = navController::navigateToHomeScreen
        )
    }
}