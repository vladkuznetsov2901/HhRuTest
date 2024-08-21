package com.example.hhrutest.ui.sign_in

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SignInViewModel : ViewModel() {

    private val _isSignIn = MutableStateFlow<Boolean>(false)
    val isSignIn: MutableStateFlow<Boolean> = _isSignIn

    private val _isEditTextNotEmpty =
        MutableStateFlow<MutableList<Boolean>>(emptyList<Boolean>().toMutableList())
    val isEditTextNotEmpty = _isEditTextNotEmpty

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
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