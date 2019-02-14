package com.raiachat.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.zsmb.materialdrawerkt.builders.drawer
import com.raiachat.R
import com.raiachat.fragments.HomeFragment
import com.raiachat.util.inTransaction


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_main)

        val homeFragment:HomeFragment = HomeFragment()

        supportFragmentManager.inTransaction {
            add(com.raiachat.R.id.bodyContent, homeFragment)
        }

        drawer {}
    }
}
