package com.example.student_project.ui.navigation

sealed class Screens(val route: String) {
    data object SplashScreen : Screens("splash_screen")

    data object LoginScreen : Screens("login_screen")

    data object SignupScreen : Screens("signup_screen")

    data object EmailAndPhoneScreen : Screens("email_and_phone_screen")

    data object NewPasswordScreen : Screens("new_password_screen")

    data object OtpTokenScreen : Screens("otp_token_screen")

    data object HomeScreen : Screens("home_screen")

    data object CourseFilterResultScreen : Screens("course_filter_result_screen")

    data object CourseDetailScreen : Screens("course_detail_screen")

    data object MentorFilterResultScreen : Screens("mentor_filter_result_screen")

    data object MentorFilterScreen : Screens("mentor_filter_screen")

    data object CourseFilterScreen : Screens("course_filter_screen")

    data object LearningScreen : Screens("learning_screen")

    data object ProfileScreen : Screens("profile_screen")

    data object EditProfileScreen : Screens("edit_profile_screen")

    data object NotificationScreen : Screens("notification_screen")

    data object PaymentScreen : Screens("payment_screen")

    data object SecurityScreen : Screens("security_screen")

    data object PrivacyPolicyScreen : Screens("privacy_policy_screen")

    data object HelpCenterScreen : Screens("help_center_screen")

    data object InviteFriendsScreen : Screens("invite_friends_screen")

    data object CourseLessonScreen : Screens("course_lesson_screen")

    data object MentorDetailsScreen : Screens("mentor_details_screen")

    data object AllCourseScreen : Screens("all_course_screen")

    data object TrendingCourseScreen : Screens("trending_course_screen")

    data object GitHubAiScreen : Screens("github_ai_screen")

    data object GitHubAiHistoryScreen : Screens("github_ai_history_screen")

    data object ChatBootScreen : Screens("chat_boot_screen")

    data object ChatBootHistoryScreen : Screens("chat_boot_history_screen")

    data object InboxScreen : Screens("inbox_screen")

    data object InboxChatScreen : Screens("inbox_chat_screen")

    data object AllMentorScreen : Screens("all_mentor_screen")
}
