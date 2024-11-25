package com.example.student_project.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.student_project.R
import com.example.student_project.screen.component.BottomNavItem
import com.example.student_project.screen.component.SubjectRow
import com.example.student_project.screen.component.TopLiveTutorItem
import com.example.student_project.screen.component.TrendingCourseRow
import com.example.student_project.ui.theme.lightGray
import com.example.student_project.ui.theme.lightOrange

@Composable
fun HomeScreen(navController: NavController) {
    // it will be remembered if user rotate the screen
    val selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    var searchState by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val subjectList =
        listOf(
            SubjectRow(R.drawable.math, "Math 116", Color.Red),
            SubjectRow(R.drawable.architect, "Arch 116", Color.Blue),
        )
    val trendingCoursesList =
        listOf(
            TrendingCourseRow(
                "Advanced Front_End Programing Techniques",
                R.drawable.course_image,
                12.99,
                R.drawable.star_filled,
                4.5,
            ),
            TrendingCourseRow(
                "Ultimate CyberSecurity For Beginner",
                R.drawable.second_sourse_image,
                12.99,
                R.drawable.star_filled,
                4.7,
            ),
        )
    val topNewCourses =
        listOf(
            TrendingCourseRow(
                "Intro to PhotoGraphy and Editing",
                R.drawable.top_new_course_img,
                12.99,
                R.drawable.star_filled,
                4.5,
            ),
            TrendingCourseRow(
                "Another Course ",
                R.drawable.second_top_new_course,
                12.99,
                R.drawable.star_filled,
                4.7,
            ),
        )
    val topLiveMentorList =
        listOf(
            TopLiveTutorItem("Albert Flores", "Math 116", R.drawable.mentor_img),
            TopLiveTutorItem("Darrell Steward", "Bio 120", R.drawable.second_mentor_img),
        )
    Scaffold(
        Modifier.fillMaxSize().background(Color.White),
        topBar = { ScaffoldTopAppBar() },
        bottomBar = { BottomNavBar(selectedItemIndex, navController) },
    ) { innerPadding ->
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.padding(innerPadding).fillMaxSize().verticalScroll(scrollState)) {
                TextField(
                    modifier =
                        Modifier.fillMaxWidth()
                            .border(
                                width = 3.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp),
                            ),
                    value = searchState,
                    onValueChange = {
                        // here when value change we will give this value to back end to search
                        // and then we get the result
                        searchState = it
                    },
                    placeholder = { Text(text = "search", fontSize = 22.sp, color = Color.Gray) },
                    leadingIcon = {
                        Image(
                            modifier = Modifier,
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = null,
                        )
                    },
                    colors =
                        TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                        ),
                )
                Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Live Subject Tutoring",
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 15.dp, start = 7.dp),
                    )

                    Button(
                        modifier = Modifier.align(alignment = Alignment.CenterEnd),
                        onClick = {
                            // here we write code to navigate to all subject
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    ) {
                        Row {
                            Text(text = "All subject", fontSize = 10.sp, color = Color.Blue)
                            // here we put image for a right arrow
                        }
                    }
                }

                LazyRow(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
                    // we will change this subject list with another list we will get from api
                    itemsIndexed(subjectList) { index, subject ->
                        Box(
                            modifier =
                                Modifier.width(159.dp)
                                    .height(80.dp)
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(subject.color)
                                    .clickable {
                                        // here i will put code to navigate to the disired course
                                    }
                        ) {
                            Image(
                                painter = painterResource(id = subject.img),
                                contentDescription = null,
                                modifier =
                                    Modifier.size(40.dp)
                                        .align(alignment = Alignment.TopStart)
                                        .padding(10.dp),
                            )
                            Text(
                                text = subject.title,
                                fontSize = 10.sp,
                                modifier =
                                    Modifier.align(Alignment.BottomStart).padding(start = 7.dp),
                            )
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Trending Courses",
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 15.dp, start = 7.dp),
                    )

                    Button(
                        modifier = Modifier.align(alignment = Alignment.CenterEnd),
                        onClick = {
                            // here we write code to navigate to all subject
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    ) {
                        Row {
                            Text(text = "All subject", fontSize = 10.sp, color = Color.Blue)
                            // here we put image for a right arrow
                        }
                    }
                }
                LazyRow(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
                    // we will change this subject list with another list we will get from api
                    // we suppose to modify this size to match all device
                    itemsIndexed(trendingCoursesList) { index, course ->
                        Column(
                            modifier =
                                Modifier.width(200.dp)
                                    .height(200.dp)
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .clickable {
                                        // here i will put code to navigate to the disired course
                                    }
                        ) {
                            Image(
                                painter = painterResource(id = course.imgId),
                                contentDescription = null,
                                modifier = Modifier.width(200.dp).height(120.dp).padding(5.dp),
                            )
                            Text(
                                text = course.title,
                                style = MaterialTheme.typography.headlineLarge,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(start = 7.dp),
                            )
                            Row(modifier = Modifier.padding(top = 5.dp)) {
                                Text(
                                    text = "$" + course.price.toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                Spacer(modifier = Modifier.height(5.dp).width(60.dp))
                                Image(
                                    painter = painterResource(id = course.rankingImg),
                                    contentDescription = null,
                                    Modifier.width(12.dp).height(12.dp),
                                )
                                Text(
                                    text = course.rankingNumber.toString(),
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 4.dp),
                                )
                            }
                        }
                    }
                }
                Text(
                    modifier = Modifier.padding(start = 7.dp, bottom = 5.dp),
                    text = "Weekly TopLive Tutors ",
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.titleMedium,
                )
                LazyRow(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
                    // we will change this subject list with another list we will get from api
                    // we suppose to modify this size to match all device
                    itemsIndexed(topLiveMentorList) { index, mentor ->
                        Box(
                            modifier =
                                Modifier.width(140.dp)
                                    .height(136.dp)
                                    .padding(5.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .border(
                                        width = 2.dp,
                                        color = lightGray,
                                        shape = RoundedCornerShape(14.dp),
                                    )
                                    .background(Color.White)
                                    .clickable {
                                        // here i will put code to navigate to the disired course
                                    }
                        ) {
                            Image(
                                painter = painterResource(id = mentor.tutorImage),
                                contentDescription = null,
                                modifier =
                                    Modifier.width(60.dp)
                                        .height(60.dp)
                                        .padding(10.dp)
                                        .align(alignment = Alignment.TopCenter),
                            )
                            Text(
                                text = mentor.nameOfTutor,
                                style = MaterialTheme.typography.headlineLarge,
                                fontSize = 15.sp,
                                modifier =
                                    Modifier.padding(top = 25.dp)
                                        .align(alignment = Alignment.Center),
                            )
                            Text(
                                modifier =
                                    Modifier.align(Alignment.BottomCenter).padding(bottom = 25.dp),
                                text = mentor.theCourseName,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Gray,
                            )
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Top New Courses",
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 15.dp, start = 7.dp),
                    )

                    Button(
                        modifier = Modifier.align(alignment = Alignment.CenterEnd),
                        onClick = {
                            // here we write code to navigate to all subject
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    ) {
                        Row {
                            Text(text = "All subject", fontSize = 10.sp, color = Color.Blue)
                            // here we put image for a right arrow
                        }
                    }
                }
                LazyRow(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
                    // we will change this subject list with another list we will get from api
                    // we suppose to modify this size to match all device
                    itemsIndexed(topNewCourses) { index, course ->
                        Column(
                            modifier =
                                Modifier.width(200.dp)
                                    .height(200.dp)
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .clickable {
                                        // here i will put code to navigate to the disired course
                                    }
                        ) {
                            Image(
                                painter = painterResource(id = course.imgId),
                                contentDescription = null,
                                modifier = Modifier.width(200.dp).height(120.dp).padding(5.dp),
                            )
                            Text(
                                text = course.title,
                                style = MaterialTheme.typography.headlineLarge,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(start = 7.dp),
                            )
                            Row(modifier = Modifier.padding(top = 5.dp)) {
                                Text(
                                    text = "$" + course.price.toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                Spacer(modifier = Modifier.height(5.dp).width(60.dp))
                                Image(
                                    painter = painterResource(id = course.rankingImg),
                                    contentDescription = null,
                                    Modifier.width(12.dp).height(12.dp),
                                )
                                Text(
                                    text = course.rankingNumber.toString(),
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 4.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldTopAppBar() {

    TopAppBar(
        title = {
            Column(
                modifier =
                    Modifier.background(
                            brush =
                                Brush.radialGradient(
                                    // we need to modify this
                                    listOf(lightOrange, Color.Transparent)
                                )
                        )
                        .padding(20.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = null,
                        Modifier.padding(10.dp)
                            .size(40.dp)
                            .border(2.dp, color = Color.White, shape = CircleShape),
                    )
                    Column {
                        Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))
                        Text(
                            text = "Hello",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Gray,
                            fontWeight = FontWeight(400),
                        )
                        Text(text = "Tarek", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    )
}

@Composable
fun BottomNavBar(selectedState: Int, navController: NavController) {
    val items =
        listOf(
            BottomNavItem(
                route = "home_screen",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                label = "Home",
            ),
            BottomNavItem(
                route = "learning_screen",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search,
                label = "Learning",
            ),
            BottomNavItem(
                route = "profile_screen",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
                label = "Profile",
            ),
        )
    var selectedItemIndex by rememberSaveable { mutableStateOf(selectedState) }

    NavigationBar {
        items.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(bottomNavItem.route)
                },
                label = { Text(text = bottomNavItem.label) },
                icon = {
                    Icon(
                        imageVector =
                            if (index == selectedItemIndex) {
                                bottomNavItem.selectedIcon
                            } else bottomNavItem.unselectedIcon,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
