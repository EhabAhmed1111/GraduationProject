package com.example.student_project.data.repo

import coil.network.HttpException
import com.example.student_project.data.db.StudentDatabaseDao
import com.example.student_project.data.model.AiChatHistoryResponse
import com.example.student_project.data.model.Allin
import com.example.student_project.data.model.ChattingRoom
import com.example.student_project.data.model.InboxChat
import com.example.student_project.data.model.Meeting
import com.example.student_project.data.model.Message
import com.example.student_project.data.model.Student
import com.example.student_project.data.model.User
import com.example.student_project.data.network.ApiClient
import com.example.student_project.data.network.request.ApiBodyForResetPassword
import com.example.student_project.data.network.request.ApiReqForAllinAi
import com.example.student_project.data.network.request.ApiReqForChat
import com.example.student_project.data.network.request.ApiReqForChatBootAi
import com.example.student_project.data.network.request.ApiReqForMessageInChat
import com.example.student_project.data.network.request.ApiReqForSendingMessage
import com.example.student_project.data.network.request.StudentLogin
import com.example.student_project.data.network.request.StudentUpdateRequest
import com.example.student_project.data.network.request.TokenReq
import okhttp3.MultipartBody
import javax.inject.Inject

class StudentRepo
@Inject
constructor(private val studentDatabaseDao: StudentDatabaseDao, private val apiClient: ApiClient) {

    suspend fun checkUser(studentLogin: StudentLogin): Result<User?> {
        // wrapper class to handle error
        val result = Result.runCatching { apiClient.login(studentLogin).data }
        return if (result.isSuccess) {
            result
        } else {
            Result.failure(
                if (result.exceptionOrNull() is HttpException) {
                    HttpException((result.exceptionOrNull() as HttpException).response)
                } else {
                    result.exceptionOrNull() ?: Exception("Unknown error")
                }
            )
        }
    }

    suspend fun addUser(student: Student) {
        apiClient.addStudent(student)
    }

    suspend fun createChat(participantId: String): Result<ChattingRoom> {
        return Result.runCatching { apiClient.createChat(ApiReqForChat(participantId)).data }
    }

    suspend fun getAllChat(): Result<List<InboxChat>> {
        return Result.runCatching { apiClient.getAllChat().data }
    }

    suspend fun getAllMeeting(): Result<List<Meeting>> {
        return Result.runCatching { apiClient.getAllMeeting().data }
    }

    suspend fun getMessage(chatId: String): Result<List<Message>> {
        return Result.runCatching { apiClient.getMessages(ApiReqForMessageInChat(chatId)).data }
    }

    suspend fun sendMessage(chatId: String, content: String): Result<Message> {
        return Result.runCatching {
            apiClient.sendMessage(
                ApiReqForSendingMessage(
                    chatId,
                    content
                )
            ).data
        }
    }

    suspend fun updateProfile(student: StudentUpdateRequest): Result<User?> {
        // we will change this later
        val result = Result.runCatching { apiClient.updateProfile(student).data }
        return if (result.isSuccess) {
            val updatedStudent = result.getOrThrow()
            updatedStudent.token = studentDatabaseDao.getCurrentStudent()?.token
            studentDatabaseDao.updateStudent(updatedStudent)
            result
        } else {
            Result.failure(
                if (result.exceptionOrNull() is HttpException) {
                    HttpException((result.exceptionOrNull() as HttpException).response)
                } else {
                    result.exceptionOrNull() ?: Exception("Unknown error")
                }
            )
        }
    }

    // this one will change
    suspend fun resetPasswordToken(studentEmail: String): Result<String> {
        return Result.runCatching {
            apiClient.resetPasswordToken(TokenReq(studentEmail)).data
        }
    }

    //this will change
    suspend fun resetPassword(apiBodyForResetPassword: ApiBodyForResetPassword): Result<String> {
        return Result.runCatching {
            apiClient.resetPassword(apiBodyForResetPassword).data
        }
    }

    suspend fun addUser(student: User) {
        studentDatabaseDao.addStudent(student)
    }

    suspend fun getCurrentStudent(): User? {
        return studentDatabaseDao.getCurrentStudent()
    }

    suspend fun getStudentById(id: String): User {
        return studentDatabaseDao.getStudent(id)
    }

    suspend fun updateStudent(student: User) {
        studentDatabaseDao.updateStudent(student)
    }

    suspend fun deleteAllStudent() {
        studentDatabaseDao.deleteAllStudents()
    }


    suspend fun codeExplainer(githubURL: String): Result<Allin> {
        return Result.runCatching { apiClient.createProject(ApiReqForAllinAi(githubURL)).data }
    }

    suspend fun codeExplainerHistory(): Result<List<Allin>> {
        return Result.runCatching { apiClient.getProject().data }
    }

    suspend fun createAiChat(message: String): Result<String> {
        return Result.runCatching { apiClient.createAiChat(ApiReqForChatBootAi(message)).data }
    }

    suspend fun getAiChatHistory(): Result<List<AiChatHistoryResponse>> {
        return Result.runCatching { apiClient.getAiChats().data }
    }




    // this one will change
    suspend fun updateUserProfileImage(profileImage: MultipartBody.Part): Result<User> {
//        return Result.runCatching { apiClient.updateUserProfileImage(profileImage).data }
        val result = Result.runCatching { apiClient.updateUserProfileImage(profileImage).data }
        return if (result.isSuccess) {
            val updatedStudent = result.getOrThrow()
            updatedStudent.token = studentDatabaseDao.getCurrentStudent()?.token
            studentDatabaseDao.updateStudent(updatedStudent)
            result
        } else {
            Result.failure(
                if (result.exceptionOrNull() is HttpException) {
                    HttpException((result.exceptionOrNull() as HttpException).response)
                } else {
                    result.exceptionOrNull() ?: Exception("Unknown error")
                }
            )
        }
    }

}
