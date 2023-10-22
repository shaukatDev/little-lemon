package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = OnBoarding.route
    ) {
        composable(OnBoarding.route) {
           //TODO add onBoarding composable
        }
        composable(Home.route) {
            //TODO add Home composable
        }
        composable(Profile.route) {
            //TODO add Profile composable
        }
    }
}
