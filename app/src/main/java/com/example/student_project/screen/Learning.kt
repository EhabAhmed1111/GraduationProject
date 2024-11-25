package com.example.student_project.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun LearningScreen(navController: NavController) {
    val selectedItemIndex by rememberSaveable { mutableStateOf(1) }
    Scaffold(
        Modifier.fillMaxSize().background(Color.White),
        topBar = { ScaffoldTopAppBar() },
        bottomBar = { BottomNavBar(selectedItemIndex, navController) },
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {}
    }
}
