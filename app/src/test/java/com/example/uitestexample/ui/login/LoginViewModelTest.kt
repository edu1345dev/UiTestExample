package com.example.uitestexample.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.uitestexample.R
import com.example.uitestexample.data.LoginRepoInterface
import com.example.uitestexample.data.Result
import com.example.uitestexample.data.model.LoggedInUser
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest{
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginRepo: LoginRepoInterface

    //class to test
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup(){
        viewModel = LoginViewModel(loginRepo)
    }

    @Test
    fun validateWrongEmail(){
        viewModel.loginDataChanged("jose", "")

        assert(viewModel.loginFormState.value!!.usernameError == R.string.invalid_username)
    }

    @Test
    fun validateWrongPassword(){
        viewModel.loginDataChanged("jose@gmail.com", "123")

        assert(viewModel.loginFormState.value!!.passwordError == R.string.invalid_password)
    }

    @Test
    fun validateCorrectPassAndEmail(){
        viewModel.loginDataChanged("jose@gmail.com", "123456")

        assertNull(viewModel.loginFormState.value!!.passwordError)
        assertNull(viewModel.loginFormState.value!!.usernameError)
        assertTrue(viewModel.loginFormState.value!!.isDataValid)
    }

    @Test
    fun loginSuccess(){
        val resultSuccess = Result.Success(LoggedInUser(displayName = "Jose Santos", userId = "1"))
        `when`(loginRepo.login(username = anyString(),password = anyString())).thenReturn(resultSuccess)

        viewModel.login("jose@gmail.com", "123456")

        assert(viewModel.loginResult.value!!.success!!.displayName == resultSuccess.data.displayName)
    }

    @Test
    fun loginError(){
        val resultError = Result.Error(IOException("Error logging in", Exception("error")))
        `when`(loginRepo.login(username = anyString(),password = anyString())).thenReturn(resultError)

        viewModel.login("jose@gmail.com", "123456")

        assert(viewModel.loginResult.value!!.error == R.string.login_failed)
    }
}