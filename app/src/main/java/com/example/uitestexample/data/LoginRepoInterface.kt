package com.example.uitestexample.data

import com.example.uitestexample.data.model.LoggedInUser

interface LoginRepoInterface {
    fun login(username: String, password: String): Result<LoggedInUser>
    fun logout()
}