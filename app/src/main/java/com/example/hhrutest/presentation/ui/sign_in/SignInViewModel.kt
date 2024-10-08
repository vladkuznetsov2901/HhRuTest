package com.example.hhrutest.presentation.ui.sign_in

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class SignInViewModel @Inject constructor() : ViewModel() {

    private val _isSignIn = MutableStateFlow<Boolean>(false)
    val isSignIn: MutableStateFlow<Boolean> = _isSignIn

    private val _isEditTextNotEmpty =
        MutableStateFlow<MutableList<Boolean>>(emptyList<Boolean>().toMutableList())
    val isEditTextNotEmpty = _isEditTextNotEmpty

    fun isValidEmail(email: String): Boolean {
        // Определение регулярного выражения для проверки email
        val emailRegex = Regex(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        )

        return email.isNotEmpty() && email.matches(emailRegex)
    }


    fun requestFocusOnEditText(
        currentEditText: EditText,
        nextEditText: EditText?,
        accessButton: AppCompatButton,
        editTexts: List<EditText>
    ) {
        currentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    nextEditText?.requestFocus()
                }
                checkIfAllFieldsAreFilled(editTexts, accessButton)
            }
        })
    }

    private fun checkIfAllFieldsAreFilled(editTexts: List<EditText>, accessButton: AppCompatButton) {
        val allFilled = editTexts.all { it.text.toString().isNotEmpty() }
        accessButton.isEnabled = allFilled
        accessButton.isClickable = allFilled
    }


}