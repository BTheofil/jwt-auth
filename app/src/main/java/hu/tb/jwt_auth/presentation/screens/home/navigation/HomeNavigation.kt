package hu.tb.jwt_auth.presentation.screens.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hu.tb.jwt_auth.presentation.screens.home.HomeScreen

const val HOME_SCREEN_ROUTE = "home_screen_route"

fun NavController.navigateToHomeScreen() {
    this.navigate(HOME_SCREEN_ROUTE)
}

fun NavGraphBuilder.homeScreenNavigation(){
    composable(route = HOME_SCREEN_ROUTE){
        HomeScreen(

        )
    }
}