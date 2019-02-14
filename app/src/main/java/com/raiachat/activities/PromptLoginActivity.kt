package com.raiachat.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raiachat.R
import com.raiachat.util.toast
import kotlinx.android.synthetic.main.login_prompt.*

class PromptLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_prompt)

        loginPromptDismiss.setOnClickListener { finish() }
        loginPromptCloseIcon.setOnClickListener { finish() }

        loginPromptFb.setOnClickListener { toast("TODO: Fb Login :)") }
        loginPromptGoogle.setOnClickListener { toast("TODO: Google Login :)") }
        loginPromptPhone.setOnClickListener { toast("TODO: Phone Login :)") }

    }
}
