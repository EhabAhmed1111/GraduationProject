package com.example.student_project.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.student_project.R
import com.example.student_project.data.repo.StudentRepo
import com.example.student_project.ui.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, studentRepo: StudentRepo) {
    val timeState = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .fillMaxSize(),
        )
        LaunchedEffect(timeState) {
            delay(2000)
            if (studentRepo.getCurrentStudent() == null) {
                navController.navigate(Screens.LoginScreen.route)
            } else {
                navController.navigate(Screens.HomeScreen.route)
            }
        }
    }
}
