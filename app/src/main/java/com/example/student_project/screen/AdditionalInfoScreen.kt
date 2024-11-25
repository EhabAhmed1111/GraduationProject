package com.example.student_project.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.student_project.R

@Composable
fun AdditionalInfoScreen(navController: NavController) {
    DropDown(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(navController: NavController) {
    val major = listOf("cs", "it", "other")
    val university = listOf("tanta", "other")
    var isExpandedForMajor by remember { mutableStateOf(false) }
    var isExpandedForUniversity by remember { mutableStateOf(false) }
    var universityChoiceState by remember { mutableStateOf("Pick your University") }
    var majorChoiceState by remember { mutableStateOf("Major") }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Your Education",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(alignment = Alignment.TopCenter).padding(75.dp),
        )
        Text(
            text = "provide Your Educational information",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(alignment = Alignment.TopCenter).padding(top = 100.dp),
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            ExposedDropdownMenuBox(
                expanded = isExpandedForMajor,
                onExpandedChange = { isExpandedForMajor = !isExpandedForMajor },
            ) {
                TextField(
                    value = majorChoiceState,
                    onValueChange = { majorChoiceState = it },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedForMajor)
                    },
                    colors =
                        ExposedDropdownMenuDefaults.textFieldColors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                        ),
                    modifier =
                        Modifier.menuAnchor()
                            .width(350.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(10.dp)),
                )
                ExposedDropdownMenu(
                    expanded = isExpandedForMajor,
                    onDismissRequest = { isExpandedForMajor = false },
                ) {
                    major.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = { Text(text = text) },
                            onClick = {
                                majorChoiceState = major[index]
                                isExpandedForMajor = false
                            },
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
            ExposedDropdownMenuBox(
                expanded = isExpandedForUniversity,
                onExpandedChange = { isExpandedForUniversity = !isExpandedForUniversity },
            ) {
                TextField(
                    value = universityChoiceState,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedForUniversity)
                    },
                    colors =
                        ExposedDropdownMenuDefaults.textFieldColors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                        ),
                    modifier =
                        Modifier.menuAnchor()
                            .width(350.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(10.dp)),
                )
                ExposedDropdownMenu(
                    expanded = isExpandedForUniversity,
                    onDismissRequest = { isExpandedForUniversity = false },
                ) {
                    university.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = { Text(text = text) },
                            onClick = {
                                universityChoiceState = university[index]
                                isExpandedForUniversity = false
                            },
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(327.dp).height(44.dp))
            Button(
                onClick = {
                    // there will be code to send data to back
                    // this data is the state of university and major
                    // will nav to dashboard screen

                    navController.navigate(Screens.HomeScreen.route)
                },
                shape = RoundedCornerShape(10.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.light_green)
                    ),
                modifier = Modifier.fillMaxWidth().height(50.dp),
            ) {
                Text(text = "Continue", fontSize = 20.sp)
            }
        }
    }
}
