package com.example.borys.boryschajdas

class Validator(private val email: String, private val password: String) {

    private companion object {
        const val REGEX_EMAIL_VALIDATION = "^(.)+@(.)+\\.(\\w)+$"
        const val REGEX_PASSWORD_VALIDATION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(.)*$"
        const val PASSWORD_LENGTH = 8
    }

    var validationState: Boolean = false
        private set
    var emailValidationState: Boolean = false
        private set
    var passwordValidationState: Boolean = false
        private set

    init {

        validate()
    }

    private fun validateEmail(): Boolean {

        return email.length >= 5 && email.matches(REGEX_EMAIL_VALIDATION.toRegex())
    }

    private fun validatePassword(): Boolean {

        return password.length >= PASSWORD_LENGTH && password.matches(REGEX_PASSWORD_VALIDATION.toRegex())
    }

    private fun validate(){

        emailValidationState = validateEmail()
        passwordValidationState = validatePassword()

        validationState = emailValidationState and passwordValidationState
    }
}