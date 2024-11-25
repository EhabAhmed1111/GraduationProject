package com.example.student_project.screen

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
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val timeState = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen),
            contentDescription = null,
            modifier = Modifier.align(alignment = Alignment.Center).fillMaxSize(),
        )
        LaunchedEffect(timeState) {
            delay(2000)
            navController.navigate(Screens.NameAndEmailScreen.route)
        }
    }
}
