package com.example.student_project.screen.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
fun SignUpScreen(navController: NavController) {
    var emailState by remember {
        mutableStateOf("")
    }
    var emailError by remember {
        mutableStateOf(false)
    }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var showPassword by remember {
        mutableStateOf(false)
    }
    var showConfirmPassword by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())

            ) {
                IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.padding(top = 20.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = null
                    )
                }
                Text(
                    text = "Create your Account",
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
                    TextField(
                        value = emailState,
                        onValueChange = {
                            emailState = it
                            emailError = it.isEmpty()
                        },
                        modifier = Modifier
                            .fillMaxWidth()

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
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = it.isEmpty()
                        },
                        label = { Text("Password") },
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
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        isError = passwordError,
                        supportingText = {
                            if (passwordError) {
                                Text(
                                    text = "Password cannot be empty",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = textFieldColor,
                            focusedContainerColor = textFieldColor,
                            unfocusedIndicatorColor = textFieldColor,
                            focusedIndicatorColor = textFieldColor
                        )
                    )

                    TextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            confirmPasswordError = it.isEmpty() || it != password
                        },
                        label = { Text("Confirm Password") },
                        isError = confirmPasswordError,
                        supportingText = {
                            if (confirmPasswordError) {
                                Text(
                                    text = "Passwords don't match",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }, leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_lock_24),
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            Button(
                                onClick = {
                                    showConfirmPassword = !showConfirmPassword
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
                        visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = textFieldColor,
                            focusedContainerColor = textFieldColor,
                            unfocusedIndicatorColor = textFieldColor,
                            focusedIndicatorColor = textFieldColor
                        )
                    )


                    Button(
                        onClick = {
                            // Handle sign-up logic here, including validation
                            if (password.isNotEmpty() && confirmPassword == password && emailState.isNotEmpty() && emailState.endsWith(
                                    "@gmail.com"
                                )
                            ) {
                                // Proceed to next screen or perform sign-up actions
                                navController.navigate(Screens.AdditionalInfoScreen.route)
                            } else {
                                // Handle error,  show error message
                            }
                        },
                        shape = RoundedCornerShape(100.dp),
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(
                                id = R.color.button_color
                            )
                        )
                    ) {
                        Text(
                            text = "Sign up",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(alignment = Alignment.BottomCenter)
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
                            text = "Already have an account?",
                            modifier = Modifier.padding(top = 17.dp),
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        TextButton(
                            onClick = {
                                navController.navigate(Screens.LoginScreen.route)
                            }
                        ) {
                            Text(
                                text = "sign in",
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