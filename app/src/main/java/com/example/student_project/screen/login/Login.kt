package com.example.student_project.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.student_project.R
import com.example.student_project.screen.Screens
import com.example.student_project.ui.theme.borderButton
import com.example.student_project.ui.theme.buttonColor
import com.example.student_project.ui.theme.headLineColor
import com.example.student_project.ui.theme.textFieldColor

@Composable
fun LoginScreen(navController: NavController) {
    var emailState by remember {
        mutableStateOf("")
    }
    var emailError by remember {
        mutableStateOf(false)
    }
    var passwordState by remember {
        mutableStateOf("")
    }
    var passwordError by remember {
        mutableStateOf(false)
    }
    var showPassword by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Login to your Account",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 30.sp,
                color = headLineColor,
                modifier = Modifier
                    .padding(top = 100.dp, start = 10.dp)
                    .align(alignment = Alignment.TopCenter)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
            ) {

//we need to make shadow
                TextField(
                    value = emailState,
                    onValueChange = {
                        emailState = it
                        emailError = it.isEmpty()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                    //  .shadow(elevation = 2.dp, ambientColor = Color.Gray),
                    ,
                    label = { Text(text = "Email") },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_email_24),
                            contentDescription = null
                        )
                    },
                    isError = emailError,
                    singleLine = true,
                    supportingText = {
                        if (emailError) {
                            Text(
                                text = "Email cannot be empty",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = textFieldColor,
                        focusedContainerColor = textFieldColor,
                        unfocusedIndicatorColor = textFieldColor,
                        focusedIndicatorColor = textFieldColor
                    )
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                    // .shadow(elevation = 2.dp, ambientColor = Color.Gray)
                    //      .border(width = 1.dp, color = Color.Transparent),
                    ,
                    value = passwordState,
                    onValueChange = {
                        passwordState = it
                        passwordError = it.isEmpty()
                    },
                    label = { Text(text = "Password") },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_lock_24),
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        Button(
                            onClick = {
                                showPassword = !showPassword
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.show_pass),
                                modifier = Modifier.size(17.dp),
                                contentDescription = null
                            )
                        }
                    },
                    singleLine = true,
                    isError = passwordError,
                    supportingText = {
                        if (passwordError) {
                            Text(
                                text = "Password cannot be empty",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = textFieldColor,
                        focusedContainerColor = textFieldColor,
                        unfocusedIndicatorColor = textFieldColor,
                        focusedIndicatorColor = textFieldColor
                    ),

                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
                Button(
                    onClick = {
                        if (emailState.isNotEmpty() && passwordState.isNotEmpty() && emailState.endsWith(
                                "@gmail.com"
                            )
                        ) {

                            //we check on user data
                            //we will send data to back to check if true the move to next false make error

                            //will navigate to Home screen
                            navController.navigate(Screens.HomeScreen.route)
                        }
                        //else show some error massage

                    }, shape = RoundedCornerShape(100.dp), modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(
                            id = R.color.button_color
                        )
                    )
                ) {
                    Text(text = "Sign in", style = MaterialTheme.typography.headlineLarge, color = Color.White)
                }
                TextButton(
                    onClick = {
                        //will nav to the forget screen
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Forgot the password?",
                        fontSize = 16.sp,
                        color = buttonColor,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .align(alignment = Alignment.CenterHorizontally), text = "Or continue with"
                )
                Row {
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .border(
                                1.dp, borderButton,
                                RoundedCornerShape(16.dp)
                            ), onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        )
                    ) {
                        Image(
                            modifier = Modifier.size(17.dp),
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = null
                        )

                    }
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .border(
                                1.dp, borderButton,
                                RoundedCornerShape(16.dp)
                            ), onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_img),
                            contentDescription = null
                        )

                    }
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .border(
                                1.dp, borderButton,
                                RoundedCornerShape(16.dp)
                            ), onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        )
                    ) {
                        Image(
                            modifier = Modifier.size(17.dp),
                            painter = painterResource(id = R.drawable.apple),
                            contentDescription = null
                        )

                    }
                }

                Row(modifier = Modifier.align(Alignment.End)) {
                    Text(
                        text = "Don't have an account?",
                        modifier = Modifier.padding(top = 17.dp),
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    TextButton(
                        onClick = {
                            navController.navigate(Screens.SignupScreen.route)
                        }
                    ) {
                        Text(
                            text = "sign up",
                            fontSize = 14.sp,
                            color = buttonColor,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }

            }
        }
    }
}