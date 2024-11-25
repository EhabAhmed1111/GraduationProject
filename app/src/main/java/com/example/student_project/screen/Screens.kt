package com.example.student_project.screen

sealed class Screens(val route: String) {
    data object LoginScreen : Screens("login_screen")

    data object SignupScreen : Screens("signup_screen")

    data object SplashScreen : Screens("splash_screen")

    data object NameAndEmailScreen : Screens("name_email_screen")

    data object AdditionalInfoScreen : Screens("additional_info_screen")

    data object HomeScreen : Screens("home_screen")

    data object LearningScreen : Screens("learning_screen")

    data object ProfileScreen : Screens("profile_screen")
}
