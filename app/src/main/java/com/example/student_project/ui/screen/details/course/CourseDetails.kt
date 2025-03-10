package com.example.student_project.ui.screen.details.course

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.student_project.R
import com.example.student_project.data.model.Course
import com.example.student_project.data.network.request.CapturePayment
import com.example.student_project.data.network.request.CreateRatingReq
import com.example.student_project.data.repo.CourseRepo
import com.example.student_project.ui.navigation.Screens
import com.example.student_project.ui.screen.widgets.LessonsColumn
import com.example.student_project.ui.screen.widgets.ReviewColumn
import com.example.student_project.ui.theme.addReviewTextColor
import com.example.student_project.ui.theme.ambientShadowColor
import com.example.student_project.ui.theme.anotherColorForFillingStar
import com.example.student_project.ui.theme.buttonColor
import com.example.student_project.ui.theme.cardContainerColor
import com.example.student_project.ui.theme.editProfileTextColor
import com.example.student_project.ui.theme.headLineColor
import com.example.student_project.ui.theme.jopTitleColor
import com.example.student_project.ui.theme.spotShadowColor
import com.example.student_project.ui.theme.starFillingColor
import com.example.student_project.ui.theme.unselectedButton
import com.example.student_project.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CourseDetailsScreen(navController: NavController, courseId: String?, courseRepo: CourseRepo) {
    var courseDetailsState by remember { mutableStateOf<Result<Course?>?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    var aboutVisibilityState by remember { mutableStateOf(true) }
    var lessonVisibilityState by remember { mutableStateOf(false) }
    var reviewsVisibilityState by remember { mutableStateOf(false) }

    var rateNumber by remember {
        mutableStateOf(0.0)
    }

    var paymentState by remember {
        mutableStateOf<Result<String?>?>(null)
    }
    var dialogKeyForPayment by remember {
        mutableStateOf(false)
    }
    var dialogKeyForReview by remember {
        mutableStateOf(false)
    }

    var rateTextState by remember {
        mutableStateOf("")
    }
    var unLock by remember { mutableStateOf<Result<Boolean?>?>(null) }
    var lock by remember { mutableStateOf(false) }
    LaunchedEffect(scope) {
        val courseDetails = courseRepo.getFullCourseDetails(courseId.toString())
        courseDetailsState = courseDetails
    }
    courseDetailsState
        ?.onSuccess { course ->
            CoroutineScope(Dispatchers.IO).launch {
                unLock = courseRepo.verifyPayment(listOf(course?.id.toString()))
            }
            unLock?.onSuccess { it ->
                it?.let { unLockState ->
                    lock = unLockState
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                modifier = Modifier.padding(top = 10.dp),
                                title = {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                            contentDescription = "Arrow Back",
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(
                                        onClick = {
                                            // here we save this course
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier,
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "we do it latter",
                                        )
                                    }
                                },
                                backgroundColor = Color.Transparent,
                            )
                        },
                        bottomBar = {
                            AnimatedVisibility(visible = lock) {
                                BottomAppBar(containerColor = Color.White) {
                                    Button(
                                        onClick = {
                                            dialogKeyForReview = true
                                        },
                                        shape = RoundedCornerShape(100.dp),
                                        modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(start = 15.dp, end = 15.dp)
                                            // .clip(RoundedCornerShape(100.dp))
                                            // .border(width = 1.dp, color = buttonColor, shape =
                                            // RoundedCornerShape(99.dp))
                                            .shadow(
                                                elevation = 10.dp,
                                                RoundedCornerShape(100.dp),
                                                spotColor = spotShadowColor.copy(alpha = 0.4f),
                                                ambientColor = ambientShadowColor.copy(alpha = 0.35f),
                                            ),
                                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                                    ) {
                                        Text(
                                            text = "Add Review",
                                            color = Color.White,
                                            style = MaterialTheme.typography.titleLarge,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight(700),
                                        )
                                    }
                                }
                            }
                            AnimatedVisibility(visible = !lock) {
                                BottomAppBar(containerColor = Color.White) {
                                    Button(
                                        onClick = {
                                            dialogKeyForPayment = true
                                        },
                                        shape = RoundedCornerShape(100.dp),
                                        modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(start = 15.dp, end = 15.dp)
                                            // .clip(RoundedCornerShape(100.dp))
                                            // .border(width = 1.dp, color = buttonColor, shape =
                                            // RoundedCornerShape(99.dp))
                                            .shadow(
                                                elevation = 10.dp,
                                                RoundedCornerShape(100.dp),
                                                spotColor = spotShadowColor.copy(alpha = 0.4f),
                                                ambientColor = ambientShadowColor.copy(alpha = 0.35f),
                                            ),
                                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                                    ) {
                                        Text(
                                            text = "Enroll Course - $ ${course?.price.toString()}",
                                            color = Color.White,
                                            style = MaterialTheme.typography.titleLarge,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight(700),
                                        )
                                    }
                                }
                            }
                        },
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            if (dialogKeyForReview) {
                                Dialog(onDismissRequest = { dialogKeyForReview = false }) {
                                    Card(
                                        modifier = Modifier.fillMaxSize(),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        )
                                    ) {

                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(top = Constant.paddingComponentFromScreen),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {

                                            Image(
                                                painter = painterResource(id = R.drawable.add_review),
                                                contentDescription = "add_review_img",
                                                modifier = Modifier
                                                    .padding(top = Constant.paddingComponentFromScreen)
                                                    .width(screenWidth * 44 / 100)
                                                    .height(screenHeight * 21 / 100)
                                            )
                                            Text(
                                                text = "Course Completed!",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight(700),
                                                fontSize = 27.sp,
                                                color = buttonColor,
                                                modifier = Modifier.padding(
                                                    top = Constant.normalPadding,
                                                    bottom = Constant.normalPadding
                                                )
                                            )
                                            Text(
                                                text = "please leave a review for your course",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight(400),
                                                fontSize = 20.sp,
                                                color = headLineColor,
                                                modifier = Modifier.padding(
                                                    bottom = Constant.normalPadding
                                                )
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        start = 25.dp,
                                                        end = Constant.paddingComponentFromScreen
                                                    )
                                            ) {
                                                (1..5).forEach { number ->
                                                    IconButton(onClick = {
                                                        rateNumber = number.toDouble()
                                                    }) {
                                                        Icon(
                                                            //here we need to add green border
                                                            imageVector = if (number.toDouble() <= rateNumber) Icons.Sharp.Star else Icons.Default.Star,
                                                            tint = anotherColorForFillingStar,
                                                            contentDescription = "rating icon"
                                                        )
                                                    }
                                                }
                                            }
                                            OutlinedTextField(
                                                value = rateTextState,
                                                onValueChange = {
                                                    rateTextState = it
                                                },
                                                colors = TextFieldDefaults.colors(
                                                    focusedContainerColor = addReviewTextColor,
                                                    unfocusedContainerColor = addReviewTextColor
                                                ),
                                                textStyle = MaterialTheme.typography.titleMedium,
                                                shape = RoundedCornerShape(12.dp),
                                                modifier = Modifier
                                                    .padding(
                                                        top = Constant.normalPadding,
                                                        start = Constant.paddingComponentFromScreen,
                                                        end = Constant.paddingComponentFromScreen,
                                                        bottom = Constant.normalPadding
                                                    )
                                                    .border(
                                                        2.dp,
                                                        color = buttonColor,
                                                        RoundedCornerShape(12.dp)
                                                    )
                                            )
                                            Button(
                                                shape = RoundedCornerShape(100.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = buttonColor
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        start = Constant.paddingComponentFromScreen,
                                                        end = Constant.paddingComponentFromScreen,
                                                        bottom = Constant.normalPadding
                                                    )
                                                    .shadow(
                                                        elevation = 4.dp,
                                                        RoundedCornerShape(100.dp)
                                                    ),
                                                onClick = {
                                                    val studentReview = CreateRatingReq(
                                                        course?.id.toString(),
                                                        rateNumber.toInt(),
                                                        rateTextState
                                                    )
                                                    CoroutineScope(Dispatchers.IO).launch {
                                                        courseRepo.createRating(studentReview)
                                                        //here we need to handle exception
                                                        //to save app from crashing
                                                    }
                                                    dialogKeyForReview = false
                                                }) {
                                                Text(
                                                    text = "Write Review",
                                                    style = MaterialTheme.typography.titleLarge,
                                                    fontWeight = FontWeight(700),
                                                    fontSize = 16.sp,
                                                    color = Color.White
                                                )
                                            }

                                            Button(shape = RoundedCornerShape(100.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = addReviewTextColor
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        start = Constant.paddingComponentFromScreen,
                                                        end = Constant.paddingComponentFromScreen,
                                                        bottom = Constant.paddingComponentFromScreen
                                                    )
                                                    .shadow(
                                                        elevation = 4.dp,
                                                        RoundedCornerShape(100.dp)
                                                    ),
                                                onClick = {
                                                    dialogKeyForReview = false
                                                }) {
                                                Text(
                                                    text = "Cancel",
                                                    style = MaterialTheme.typography.titleLarge,
                                                    fontWeight = FontWeight(700),
                                                    fontSize = 16.sp,
                                                    color = buttonColor
                                                )
                                            }

                                        }
                                    }
                                }
                            }
                            if (dialogKeyForPayment) {
                                AlertDialog(
                                    title = {
                                        Text(
                                            text = "continue payment",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight(600),
                                            color = buttonColor
                                        )
                                    },
                                    text = {
                                        Text(
                                            text = "click here to end payment process",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight(500)
                                        )
                                    },
                                    onDismissRequest = { dialogKeyForPayment = false },
                                    buttons = {
                                        Button(
                                            shape = RoundedCornerShape(100.dp),
                                            modifier = Modifier
                                                .align(Alignment.CenterHorizontally)
                                                .padding(Constant.mediumPadding)
                                                .shadow(
                                                    elevation = 4.dp,
                                                    RoundedCornerShape(100.dp)
                                                ),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = buttonColor
                                            ),
                                            onClick = {

                                                val capturePayment = CapturePayment(
                                                    course?.price!!,
                                                    courseId.toString()
                                                )
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    paymentState = courseRepo.initiatePayment(
                                                        capturePayment
                                                    )
                                                }
                                                paymentState?.onSuccess {
                                                    //intent to payment screen
                                                    //and when he return to this screen
                                                    //we will make get Transaction
                                                    val intent =
                                                        Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                                    context.startActivity(intent)
                                                    lock = !unLockState
                                                    dialogKeyForPayment = false
                                                }?.onFailure {
                                                    Toast.makeText(
                                                        context,
                                                        "failed on your data",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }

                                            }
                                        ) {
                                            Text(
                                                text = "Submit",
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight(600),
                                                style = MaterialTheme.typography.titleMedium,
                                                color = Color.White
                                            )
                                        }

                                    })
                            }
                            AsyncImage(
                                modifier = Modifier
                                    .width(screenWidth)
                                    .height(screenHeight * 34 / 100),
                                // this will change course?.thumbnail
                                model = ImageRequest.Builder(context).crossfade(true)
                                    .data(course?.thumbnail.toString()).build(),
                                contentDescription = "course image",
                            )
                            Column(modifier = Modifier.padding(innerPadding)) {
                                Row(Modifier.fillMaxWidth()) {
                                    Text(
                                        modifier =
                                        Modifier.padding(
                                            bottom = 15.dp,
                                            start = 15.dp,
                                            end = 15.dp
                                        ),
                                        text = course?.courseName.toString(),
                                        fontSize = 26.sp,
                                        color = headLineColor,
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight(700),
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                }

                                LazyRow(modifier = Modifier.fillMaxWidth()) {
                                    course?.let {
                                        items(it.tag) { item ->
                                            Card(
                                                modifier = Modifier.padding(
                                                    bottom = 15.dp,
                                                    start = 15.dp
                                                ),
                                                shape = RoundedCornerShape(6.dp),
                                                colors =
                                                CardDefaults.cardColors(
                                                    containerColor = cardContainerColor
                                                ),
                                            ) {
                                                Text(
                                                    modifier =
                                                    Modifier.padding(
                                                        top = 6.dp,
                                                        bottom = 6.dp,
                                                        start = 10.dp,
                                                        end = 10.dp,
                                                    ),
                                                    text = item,
                                                    color = buttonColor,
                                                    fontSize = 15.sp,
                                                    style = MaterialTheme.typography.headlineMedium,
                                                    fontWeight = FontWeight(600),
                                                )
                                            }
                                        }
                                    }
                                }
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "$" + course?.price.toString(),
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontSize = 32.sp,
                                        color = buttonColor,
                                        fontWeight = FontWeight(700),
                                        modifier =
                                        Modifier.padding(
                                            top = 7.5.dp,
                                            bottom = 10.dp,
                                            start = 15.dp,
                                            end = 15.dp,
                                        ),
                                    )
                                    Spacer(modifier = Modifier.width(30.dp))
                                    Icon(
                                        modifier =
                                        Modifier.padding(
                                            start = 15.dp,
                                            bottom = 15.dp,
                                            top = 15.dp
                                        ),
                                        imageVector = Icons.Filled.Star,
                                        tint = starFillingColor,
                                        contentDescription = "rating icon",
                                    )
                                    Text(
                                        modifier =
                                        Modifier.padding(
                                            start = 7.5.dp,
                                            bottom = 15.dp,
                                            top = 20.dp
                                        ),
                                        text = "4.5",
                                        color = editProfileTextColor,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontSize = 16.sp,
                                    )
                                }
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Icon(
                                        modifier =
                                        Modifier.padding(
                                            start = 15.dp,
                                            bottom = 15.dp,
                                            end = 7.5.dp
                                        ),
                                        imageVector =
                                        ImageVector.vectorResource(id = R.drawable.add_friends),
                                        tint = buttonColor,
                                        contentDescription = "participant",
                                    )
                                    Text(
                                        text = course?.studentsEnrolled?.size.toString() + " students",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = editProfileTextColor,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(500),
                                        modifier = Modifier.padding(
                                            top = 5.dp,
                                            bottom = 15.dp,
                                            end = 15.dp
                                        ),
                                    )
                                    // this icon need to change
                                    Icon(
                                        modifier = Modifier.padding(bottom = 15.dp, end = 7.5.dp),
                                        imageVector = Icons.Filled.AddCircle,
                                        contentDescription = "time icon",
                                    )
                                    Text(
                                        text = course?.totalDuration.toString() + " Hours",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = editProfileTextColor,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(500),
                                        modifier = Modifier.padding(
                                            top = 2.dp,
                                            bottom = 17.dp,
                                            end = 15.dp
                                        ),
                                    )
                                }
                                HorizontalDivider(
                                    modifier = Modifier.padding(
                                        start = 15.dp,
                                        end = 15.dp,
                                        bottom = 12.dp
                                    )
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement =
                                    Arrangement.spacedBy(
                                        10.dp,
                                        alignment = Alignment.CenterHorizontally,
                                    ),
                                ) {
                                    Button(
                                        onClick = {
                                            if (!aboutVisibilityState) {
                                                aboutVisibilityState = true
                                                lessonVisibilityState = false
                                                reviewsVisibilityState = false
                                            } else {
                                                lessonVisibilityState = false
                                                reviewsVisibilityState = false
                                            }
                                        },
                                        colors =
                                        ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                    ) {
                                        Text(
                                            text = "About",
                                            fontSize = 18.sp,
                                            style = MaterialTheme.typography.titleMedium,
                                            color =
                                            if (aboutVisibilityState) buttonColor else unselectedButton,
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            if (!lessonVisibilityState) {
                                                aboutVisibilityState = false
                                                lessonVisibilityState = true
                                                reviewsVisibilityState = false
                                            } else {
                                                aboutVisibilityState = false
                                                reviewsVisibilityState = false
                                            }
                                        },
                                        colors =
                                        ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                    ) {
                                        Text(
                                            text = "Lessons",
                                            fontSize = 18.sp,
                                            style = MaterialTheme.typography.titleMedium,
                                            color =
                                            if (lessonVisibilityState) buttonColor else unselectedButton,
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            if (!reviewsVisibilityState) {
                                                aboutVisibilityState = false
                                                lessonVisibilityState = false
                                                reviewsVisibilityState = true
                                            } else {
                                                lessonVisibilityState = false
                                                aboutVisibilityState = false
                                            }
                                        },
                                        colors =
                                        ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                    ) {
                                        Text(
                                            text = "Reviews",
                                            fontSize = 18.sp,
                                            style = MaterialTheme.typography.titleMedium,
                                            color =
                                            if (reviewsVisibilityState) buttonColor
                                            else unselectedButton,
                                        )
                                    }
                                }
                                HorizontalDivider(
                                    modifier = Modifier.padding(
                                        start = 15.dp,
                                        end = 15.dp,
                                        bottom = 15.dp
                                    )
                                )

                                AnimatedVisibility(visible = aboutVisibilityState) {
                                    Column(modifier = Modifier) {
                                        Text(
                                            modifier = Modifier.padding(
                                                start = 15.dp,
                                                bottom = 10.dp
                                            ),
                                            text = "Mentor",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight(700),
                                        )
                                        Button(
                                            modifier = Modifier.padding(
                                                start = 10.dp,
                                                bottom = 10.dp
                                            ),
                                            colors =
                                            ButtonDefaults.buttonColors(
                                                containerColor = Color.Transparent
                                            ),
                                            onClick = {
                                                // this will move us to mentor page
                                            },
                                        ) {
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                AsyncImage(
                                                    model =
                                                    ImageRequest.Builder(context)
                                                        .crossfade(true)
                                                        // this will be photo
                                                        // course?.instructor?.image.toString()
                                                        .data(course?.instructor?.image.toString())
                                                        .transformations(CircleCropTransformation())
                                                        .build(),
                                                    contentDescription = "mentor image",
                                                    modifier =
                                                    Modifier
                                                        .padding(end = 7.5.dp)
                                                        .align(Alignment.CenterVertically)
                                                        .width(screenWidth * 14 / 100)
                                                        .height(screenHeight * 7 / 100),
                                                )
                                                Column(modifier = Modifier) {
                                                    Text(
                                                        text =
                                                        course?.instructor?.firstName.toString() +
                                                                " " +
                                                                course?.instructor?.lastName.toString(),
                                                        style = MaterialTheme.typography.titleMedium,
                                                        color = headLineColor,
                                                        fontWeight = FontWeight(700),
                                                        fontSize = 18.sp,
                                                        modifier =
                                                        Modifier.padding(
                                                            start = 2.5.dp,
                                                            bottom = 5.dp
                                                        ),
                                                    )
                                                    Text(
                                                        text =
                                                        course
                                                            ?.instructor
                                                            ?.additionalDetails
                                                            ?.about
                                                            .toString(),
                                                        color = jopTitleColor,
                                                        style = MaterialTheme.typography.titleMedium,
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight(500),
                                                        //                                          maxLines
                                                        // = 2,
                                                        //                                          overflow
                                                        // = TextOverflow.Ellipsis,
                                                        //  softWrap = true,
                                                        modifier = Modifier.padding(start = 2.5.dp),
                                                    )
                                                }
                                            }
                                        }
                                        Text(
                                            modifier = Modifier.padding(
                                                start = 15.dp,
                                                bottom = 15.dp
                                            ),
                                            text = "About Course",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight(700),
                                        )
                                        Text(
                                            text = course?.courseDescription.toString(),
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontSize = 14.sp,
                                            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                                        )
                                    }
                                }
                                AnimatedVisibility(visible = lessonVisibilityState) {
                                    Column {
                                        // size -> no of section
                                        Text(
                                            text =
                                            course?.courseContent?.size.toString() + " " + "Sections",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = headLineColor,
                                            fontWeight = FontWeight(700),
                                            fontSize = 20.sp,
                                            modifier = Modifier.padding(15.dp),
                                        )

                                        LazyColumn(modifier = Modifier.height(500.dp)) {
                                            course?.let { courses ->
                                                itemsIndexed(courses.courseContent) { index, item ->
                                                    Text(
                                                        text =
                                                        "Section" +
                                                                " " +
                                                                (index + 1).toString() +
                                                                " " +
                                                                item.sectionName,
                                                        style = MaterialTheme.typography.titleMedium,
                                                        color = jopTitleColor,
                                                        fontWeight = FontWeight(700),
                                                        fontSize = 18.sp,
                                                        modifier =
                                                        Modifier.padding(
                                                            start = 15.dp,
                                                            bottom = 12.5.dp,
                                                        ),
                                                    )
                                                    LazyColumn(modifier = Modifier.height(250.dp)) {
                                                        itemsIndexed(item.subSection) { subsectionIndex,
                                                                                        subSection ->
                                                            Card(onClick = {}) {
                                                                LessonsColumn(
                                                                    subSection = subSection,
                                                                    index = subsectionIndex,
                                                                    lock = !lock,
                                                                    context = context,
                                                                ) {
                                                                    val encodedUrl =
                                                                        URLEncoder.encode(
                                                                            subSection.videoUrl,
                                                                            StandardCharsets.UTF_8
                                                                                .toString(),
                                                                        )
                                                                    navController.navigate(
                                                                        Screens.CourseLessonScreen.route +
                                                                                "/${encodedUrl}"
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                AnimatedVisibility(visible = reviewsVisibilityState) {
                                    Column {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(15.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                tint = starFillingColor,
                                                contentDescription = "rating icon",
                                                modifier = Modifier.padding(end = 5.dp),
                                            )
                                            Text(
                                                text = course?.averageRating.toString(),
                                                style = MaterialTheme.typography.titleLarge,
                                                fontWeight = FontWeight(700),
                                                color = buttonColor,
                                            )
                                        }
                                        LazyColumn(modifier = Modifier.height(500.dp)) {
                                            course?.let { item ->
                                                items(item.ratingAndReviews) { rate ->
                                                    ReviewColumn(
                                                        ratingAndReview = rate,
                                                        context = context
                                                    )
                                                    HorizontalDivider(
                                                        modifier =
                                                        Modifier.padding(start = 15.dp, end = 15.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        ?.onFailure {
            Log.d("details", it.message.toString())
            Toast.makeText(context, "failed to load data", Toast.LENGTH_SHORT).show()
        }
}

