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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.student_project.R

@Composable
fun SignUpScreen(navController: NavController) {

    var passwordState by remember { mutableStateOf("") }
    var confirmPasswordState by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Text(
                text = "Create an account",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 75.dp).align(Alignment.TopCenter),
            )
            Column(modifier = Modifier.align(Alignment.Center).fillMaxWidth()) {
                TextField(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    value = passwordState,
                    onValueChange = { passwordState = it },
                    label = { Text(text = "password") },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_lock_24),
                            contentDescription = null,
                        )
                    },
                    colors =
                        TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                        ),
                    visualTransformation = PasswordVisualTransformation(),
                )
                TextField(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    value = confirmPasswordState,
                    onValueChange = { confirmPasswordState = it },
                    label = { Text(text = "confirm password") },
                    colors =
                        TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                        ),
                )

                Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                Button(
                    onClick = {
                        // and we will make check if user but empty data
                        // there will be code to send data to back

                        navController.navigate(Screens.AdditionalInfoScreen.route)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.light_green)
                        ),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                ) {
                    Text(text = "signup", fontSize = 20.sp)
                }
            }
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                Text(
                    modifier = Modifier.padding(bottom = 50.dp).align(Alignment.CenterHorizontally),
                    text = "Or sign up with social account",
                )

                Button(
                    modifier =
                        Modifier.padding(bottom = 20.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                            .border(2.dp, Color.Black, RectangleShape),
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
