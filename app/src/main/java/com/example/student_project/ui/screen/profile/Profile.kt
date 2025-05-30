package com.example.student_project.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.student_project.R
import com.example.student_project.data.model.User
import com.example.student_project.data.repo.StudentRepo
import com.example.student_project.ui.navigation.Screens
import com.example.student_project.ui.screen.home.content.BottomNavBar
import com.example.student_project.ui.screen.widgets.ImagePicker
import com.example.student_project.ui.screen.widgets.ProfileCommonButton
import com.example.student_project.ui.theme.ambientShadowColor
import com.example.student_project.ui.theme.buttonColor
import com.example.student_project.ui.theme.cancelButton
import com.example.student_project.ui.theme.editProfileLogoutColor
import com.example.student_project.ui.theme.editProfileTextColor
import com.example.student_project.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, studentRepo: StudentRepo) {
    val selectedItemIndex by rememberSaveable { mutableStateOf(4) }

    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    var dialogKey by remember {
        mutableStateOf(false)
    }
    var student by remember {
        mutableStateOf<User?>(null)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(scope) {
        student = studentRepo.getCurrentStudent()
    }

    Scaffold(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(10.dp),
                    )
                }
//                ,
//                actions = {
//                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp)) {
//                        Icon(
//                            imageVector = ImageVector.vectorResource(id = R.drawable.menu),
//                            contentDescription = "menu list",
//                        )
//                    }
//                },
            )
        },
        bottomBar = { BottomNavBar(selectedItemIndex, navController) },
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {
                Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Box(
                        modifier =
                        Modifier
                            .width(screenWidth * 35 / 100)
                            .height(screenHeight * 160 / 1000)
                            .clip(CircleShape)
                            .padding(top = 15.dp, bottom = 7.5.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {

                        student?.let {
                            ImagePicker(student = it, onImageSelected = {

                            })
                        }
//                        AsyncImage(
//                            model =
//                            ImageRequest.Builder(context)
//                                .data(student?.image)
//                                .crossfade(true)
//                                .transformations(CircleCropTransformation())
//                                .build(),
//                            contentDescription = "profile pic",
//                            modifier = Modifier.fillMaxSize(),
//                        )
//                        IconButton(
//                            onClick = { /*TODO*/ },
//                            modifier = Modifier.align(Alignment.BottomEnd),
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Edit,
//                                contentDescription = "change pic",
//                                tint = Color.White,
//                                modifier = Modifier.background(Color.Black),
//                            )
//                        }
                    }
                    Text(
                        text = student?.firstName.toString() + " " + student?.lastName.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(Constant.verySmallPadding)
                            .align(alignment = Alignment.CenterHorizontally),
                    )
                    Text(
                        text = student?.email.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(top = Constant.smallPadding)
                            .align(alignment = Alignment.CenterHorizontally),
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(
                    top = Constant.paddingTextFromText,
                    bottom = Constant.paddingTextFromText
                )
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                ProfileCommonButton(
                    imgVector = ImageVector.vectorResource(R.drawable.profile_setting),
                    text = "Edit Profile",
                    route = Screens.EditProfileScreen.route,
                    navController = navController,
                    modifier = Modifier,
                )
//                ProfileCommonButton(
//                    imgVector = ImageVector.vectorResource(R.drawable.notification),
//                    text = "Notification",
//                    route = Screens.NotificationScreen.route,
//                    navController = navController,
//                    modifier = Modifier,
//                )
//                ProfileCommonButton(
//                    imgVector = ImageVector.vectorResource(R.drawable.wallet),
//                    text = "Payment",
//                    route = Screens.PaymentScreen.route,
//                    navController = navController,
//                    modifier = Modifier,
//                )
//                ProfileCommonButton(
//                    imgVector = ImageVector.vectorResource(R.drawable.privacy),
//                    text = "Security",
//                    route = Screens.SecurityScreen.route,
//                    navController = navController,
//                    modifier = Modifier,
//                )
//                ProfileCommonButton(
//                    imgVector = ImageVector.vectorResource(R.drawable.lock),
//                    text = "Privacy Policy",
//                    route = Screens.PrivacyPolicyScreen.route,
//                    navController = navController,
//                    modifier = Modifier,
//                )
//                ProfileCommonButton(
//                    imgVector = ImageVector.vectorResource(R.drawable.info_square),
//                    text = "Help Center",
//                    route = Screens.HelpCenterScreen.route,
//                    navController = navController,
//                    modifier = Modifier,
//                )
//                ProfileCommonButton(
//                    imgVector = ImageVector.vectorResource(R.drawable.add_friends),
//                    text = "Invite Friends",
//                    route = Screens.InviteFriendsScreen.route,
//                    navController = navController,
//                    modifier = Modifier,
//                )
            }
            Button(
                onClick = {
                    dialogKey = true
//
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.logout),
                        tint = editProfileLogoutColor,
                        contentDescription = "button icon",
                        modifier = Modifier.padding(start = 10.dp, bottom = 5.dp),
                    )
                    Text(
                        text = "Logout",
                        modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = editProfileLogoutColor,
                    )
                }
            }
            if (dialogKey) {

                AlertDialog(onDismissRequest = {
                    dialogKey = false
                }, title = {
                    Column {

                        Text(
                            text = "Logout ",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 24.sp,
                            color = editProfileLogoutColor,
                            fontWeight = FontWeight(700),
                            modifier = Modifier
                                .padding(
                                    top = Constant.paddingComponentFromScreen,
                                    bottom = Constant.paddingComponentFromScreen
                                )
                                .align(Alignment.CenterHorizontally)
                        )
                        HorizontalDivider()
                    }
                }, text = {
                    Text(
                        text = "Are you sure you want to log out",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 20.sp,
                        color = editProfileTextColor,
                        fontWeight = FontWeight(700),
                        modifier = Modifier.padding(
                            top = Constant.paddingComponentFromScreen,
                            bottom = Constant.paddingComponentFromScreen
                        )
                    )
                }, confirmButton = {
                    Row(horizontalArrangement = Arrangement.Center) {
                        Button(shape = RoundedCornerShape(100.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = cancelButton
                            ), modifier = Modifier
                                .padding(
                                    bottom = Constant.normalPadding,
                                    end = Constant.smallPadding
                                )
                                .shadow(4.dp, RoundedCornerShape(100.dp)),
                            onClick = {
                                dialogKey = false
                            }) {
                            Text(
                                text = "Cancel", style = MaterialTheme.typography.titleLarge,
                                fontSize = 16.sp,
                                color = buttonColor,
                                fontWeight = FontWeight(700),
                            )
                        }

                        Button(shape = RoundedCornerShape(100.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor
                            ), modifier = Modifier
                                .padding(
                                    bottom = Constant.normalPadding,
                                    start = Constant.smallPadding
                                )
                                .shadow(
                                    elevation = 10.dp,
                                    RoundedCornerShape(100.dp),
                                    spotColor = ambientShadowColor.copy(alpha = 0.6f),
                                    ambientColor = ambientShadowColor.copy(alpha = 0.55f),
                                ),
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    studentRepo.deleteAllStudent()
                                }
                                navController.navigate(Screens.LoginScreen.route)
                                dialogKey = false
                            }) {
                            Text(
                                text = "Yes, Logout", style = MaterialTheme.typography.titleLarge,
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight(700),
                            )
                        }


                    }
                })
            }
        }
    }
}
