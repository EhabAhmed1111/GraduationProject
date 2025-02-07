package com.example.student_project.ui.screen.log.forgetpassword

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val VALID_OTP_CODE = "1414"

class OtpViewModel : ViewModel() {
    private val _state = MutableStateFlow(OtpState())
    val state = _state.asStateFlow()

    fun onAction(action: OtpAction) {
        when (action) {
            is OtpAction.OnEnterNumber -> {
                enterNumber(action.number, action.index)
            }

            is OtpAction.OnChangeFieldFocus -> {
                _state.update { it.copy(focusedIndex = action.index) }
            }

            OtpAction.OnKeyboardBack -> {
                val previousIndex = getPreviousFocusedIndex(state.value.focusedIndex)
                _state.update {
                    it.copy(
                        code =
                            it.code.mapIndexed { index, number ->
                                if (index == previousIndex) {
                                    null
                                } else {
                                    number
                                }
                            },
                        focusedIndex = previousIndex,
                    )
                }
            }
            else -> throw Exception()
        }
    }

    private fun enterNumber(number: Int?, index: Int) {
        val newCode =
            state.value.code.mapIndexed { currentindex, currentNumber ->
                if (currentindex == index) {
                    number
                } else {
                    currentNumber
                }
            }
        val wasNumberRemoved = number == null
        _state.update {
            it.copy(
                code = newCode,
                focusedIndex =
                    if (wasNumberRemoved || it.code.getOrNull(index) != null) {
                        it.focusedIndex
                    } else {
                        getNextFocusedTextFieldIndex(
                            currentCode = it.code,
                            currentFocusedIndex = it.focusedIndex,
                        )
                    },
                // none mean that the newCode will never had the value in none
                isValid =
                    if (newCode.none { it == null }) {
                        // here we will change code with the one we got from  backend and check if
                        // it true
                        newCode.joinToString("") == VALID_OTP_CODE
                    } else null,
            )
        }
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(
        currentCode: List<Int?>,
        currentFocusedIndex: Int?,
    ): Int? {
        if (currentFocusedIndex == null) {
            return null
        }
        if (currentFocusedIndex == 3) {
            return currentFocusedIndex
        }
        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex,
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int,
    ): Int? {

        code.forEachIndexed { index, number ->
            if (index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if (number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }
}
