package com.example.student_project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.student_project.screen.AdditionalInfoScreen
import com.example.student_project.screen.HomeScreen
import com.example.student_project.screen.LearningScreen
import com.example.student_project.screen.LoginScreen
import com.example.student_project.screen.NameAndEmailScreen
import com.example.student_project.screen.ProfileScreen
import com.example.student_project.screen.Screens
import com.example.student_project.screen.SignUpScreen
import com.example.student_project.screen.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    // start from splash
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable(Screens.SplashScreen.route) { SplashScreen(navController) }
        composable(Screens.SignupScreen.route) { SignUpScreen(navController) }
        composable(Screens.LoginScreen.route) { LoginScreen(navController) }
        composable(Screens.NameAndEmailScreen.route) { NameAndEmailScreen(navController) }
        composable(Screens.AdditionalInfoScreen.route) {
            AdditionalInfoScreen(navController = navController)
        }
        composable(Screens.HomeScreen.route) { HomeScreen(navController = navController) }
        composable(Screens.LearningScreen.route) { LearningScreen(navController = navController) }
        composable(Screens.ProfileScreen.route) { ProfileScreen(navController = navController) }
    }
}
