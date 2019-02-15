package com.raiachat.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.raiachat.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.reflect.KClass

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_login)

        signinBtn.setOnClickListener { openActivity(MainActivity::class) }
    }


    private fun <T : Activity> openActivity(activity: KClass<T>) {
        startActivity(Intent(this@LoginActivity, activity.java))
    }
}
