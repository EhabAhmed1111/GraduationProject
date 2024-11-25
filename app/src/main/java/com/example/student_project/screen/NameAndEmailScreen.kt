package com.example.student_project.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.student_project.R

@Composable
fun NameAndEmailScreen(navController: NavController) {
    var firstNameState by remember { mutableStateOf("") }
    var lastNameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Text(
                text = "Enter name and email",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 75.dp).align(Alignment.TopCenter),
            )
            Column(modifier = Modifier.align(Alignment.Center).fillMaxWidth()) {
                Row {
                    TextField(
                        modifier = Modifier.weight(0.5f).padding(10.dp),
                        value = firstNameState,
                        onValueChange = { firstNameState = it },
                        label = { Text(text = "first name") },
                        colors =
                            TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                            ),
                    )
                    TextField(
                        modifier = Modifier.weight(0.5f).padding(10.dp),
                        value = lastNameState,
                        onValueChange = { lastNameState = it },
                        label = { Text(text = "last name") },
                        colors =
                            TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                            ),
                    )
                }
                TextField(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    value = emailState,
                    onValueChange = { emailState = it },
                    label = { Text(text = "email") },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_email_24),
                            contentDescription = null,
                        )
                    },
                    colors =
                        TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                        ),
                )
                Row(modifier = Modifier.align(Alignment.End)) {
                    Text(text = "Already have an account?", fontSize = 10.sp)
                    Button(
                        onClick = { navController.navigate(Screens.LoginScreen.route) },
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.light_green)
                            ),
                    ) {
                        Text(text = "login")
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        // here will be nav with parameter
                        navController.navigate(Screens.SignupScreen.route)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.light_green)
                        ),
                ) {
                    Text(text = "Continue")
                }
            }
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                Text(
                    modifier = Modifier.padding(bottom = 50.dp).align(Alignment.CenterHorizontally),
                    text = "Or sign up with social account",
                )

                Button(
                    modifier =
                        Modifier.padding(bottom = 20.dp).border(2.dp, Color.Black, RectangleShape),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.google_img),
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Continue with google", color = Color.Black)
                    }
                }
            }
        }
    }
}
