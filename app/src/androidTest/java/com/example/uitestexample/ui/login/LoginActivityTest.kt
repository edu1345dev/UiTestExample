package com.example.uitestexample.ui.login

import android.content.ComponentName
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.registerIdlingResources
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.uitestexample.R
import com.example.uitestexample.ui.MainActivity
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get: Rule
    var activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setup(){
        Intents.init()
    }

    @Test
    fun errorUserNameButtonDisabled() {
        onView(withId(R.id.username))
            .perform(typeText("jose@gmail"))

        val context = ApplicationProvider.getApplicationContext<Context>()

        onView(withId(R.id.username))
            .check(matches(hasErrorText(context.getString(R.string.invalid_username))))
        onView(withId(R.id.password))
            .check(matches(hasErrorText(context.getString(R.string.invalid_password))))

        onView(withId(R.id.username))
            .check(matches(withText("jose@gmail")))

        onView(withId(R.id.login))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun errorPasswordButtonDisabled() {
        onView(withId(R.id.username))
            .perform(typeText("jose@gmail.com"))

        onView(withId(R.id.password))
            .perform(replaceText("123"), closeSoftKeyboard())

        val context = ApplicationProvider.getApplicationContext<Context>()

        onView(withId(R.id.password))
            .check(matches(hasErrorText(context.getString(R.string.invalid_password))))
        onView(withId(R.id.username))
            .check(matches(hasErrorText(nullValue(String::class.java))))

        onView(withId(R.id.login))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun errorPasswordAndUsernameButtonDisabled() {
        onView(withId(R.id.username))
            .perform(typeText("jose@gmail"), closeSoftKeyboard())

        onView(withId(R.id.password))
            .perform(replaceText("123"), closeSoftKeyboard())

        val context = ApplicationProvider.getApplicationContext<Context>()

        onView(withId(R.id.password))
            .check(matches(hasErrorText(context.getString(R.string.invalid_password))))

        onView(withId(R.id.username))
            .check(matches(hasErrorText(context.getString(R.string.invalid_username))))

        onView(withId(R.id.login))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun validateLoginButtonButtonEnabled() {
        onView(withId(R.id.username))
            .perform(typeText("jose@gmail.com"), closeSoftKeyboard())

        onView(withId(R.id.password))
            .perform(typeText("123456"), closeSoftKeyboard())

        onView(withId(R.id.username))
            .check(matches(hasErrorText(nullValue(String::class.java))))
        onView(withId(R.id.password))
            .check(matches(hasErrorText(nullValue(String::class.java))))

        onView(withId(R.id.login))
            .check(matches(isEnabled()))
        onView(withId(R.id.login))
            .perform(click())

        intended(hasComponent(MainActivity::class.java.name))
    }

    @After
    fun after(){
        Intents.release()
    }
}
