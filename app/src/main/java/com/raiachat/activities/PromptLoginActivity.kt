package com.raiachat.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raiachat.R
import com.raiachat.util.toast
import kotlinx.android.synthetic.main.login_prompt.*
import kotlin.reflect.KClass

class PromptLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_prompt)

        loginPromptDismiss.setOnClickListener { finish() }
        loginPromptCloseIcon.setOnClickListener { finish() }

        loginPromptFb.setOnClickListener { toast("TODO: Fb Login :)") }
        loginPromptGoogle.setOnClickListener { toast("TODO: Google Login :)") }
        loginPromptPhone.setOnClickListener { openActivity(LoginActivity::class) }

    }

    private fun <T : Activity> openActivity(activity: KClass<T>) {
        startActivity(Intent(this@PromptLoginActivity, activity.java))
    }
}
