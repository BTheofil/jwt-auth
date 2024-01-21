package hu.tb.jwt_auth.presentation.screens.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hu.tb.jwt_auth.presentation.screens.login.LoginScreen

const val LOGIN_SCREEN_ROUTE = "login_screen_route"

fun NavController.navigateToLoginScreen() {
    this.navigate(LOGIN_SCREEN_ROUTE)
}

fun NavGraphBuilder.loginScreenNavigation(
    navigateToHomeScreen: () -> Unit
){
    composable(route = LOGIN_SCREEN_ROUTE){
        LoginScreen(
            loginSuccess = navigateToHomeScreen
        )
    }
}