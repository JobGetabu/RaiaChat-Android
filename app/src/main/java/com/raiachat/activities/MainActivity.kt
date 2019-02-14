package com.raiachat.activities

import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.view.View
import android.view.Window.FEATURE_CONTENT_TRANSITIONS
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import co.zsmb.materialdrawerkt.builders.accountHeader
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.divider
import co.zsmb.materialdrawerkt.draweritems.profile.profile
import co.zsmb.materialdrawerkt.draweritems.sectionHeader
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.Drawer
import com.raiachat.R
import com.raiachat.fragments.HomeFragment
import com.raiachat.util.inTransaction
import com.raiachat.util.toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var result: Drawer
    private lateinit var headerResult: AccountHeader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Making notification bar transparent
        makeTransparent()

        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()

        supportFragmentManager.inTransaction {
            add(com.raiachat.R.id.bodyContent, homeFragment)
        }

        initDrawer(savedInstanceState)

    }

    private fun makeTransparent() {
        with(window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                requestFeature(FEATURE_CONTENT_TRANSITIONS)
                // set an exit transition
                exitTransition = Explode()
                enterTransition = Explode()
            }
        }

        val winParams = window.attributes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()

        }
        window.attributes = winParams
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LOW_PROFILE
        }

    }

    override fun onBackPressed() {
        if (result.isDrawerOpen)
            result.closeDrawer()
        else
            super.onBackPressed()
    }

    private fun initDrawer(savedInstanceState: Bundle?) {
        result = drawer {
            toolbar = maintoolbar
            hasStableIds = true
            savedInstance = savedInstanceState
            showOnFirstLaunch = true

            headerResult = accountHeader {
                background = com.raiachat.R.drawable.header
                savedInstance = savedInstanceState
                translucentStatusBar = true

                profile("Guest User", "guest@gmail.com") {
                    iconUrl = "https://avatars33.githubusercontent.com/u/1476232?v=3&s=4600"
                    identifier = 100
                }

            }

            sectionHeader("Raia Chat") {
                divider = false
            }

            primaryItem("Home") {
                icon = com.raiachat.R.drawable.ic_home_grey
                onClick(doToast("Home"))
            }

            primaryItem("Profile") {
                icon = com.raiachat.R.drawable.ic_person
                onClick(doToast("Profile"))
            }

            primaryItem("Notification") {
                icon = com.raiachat.R.drawable.ic_notification
                onClick(doToast("Notification"))
            }

            primaryItem("Trending") {
                icon = com.raiachat.R.drawable.ic_ratings
                onClick(doToast("Trending"))
            }

            sectionHeader("Manage") {
                divider = true
            }

            primaryItem("Settings") {
                icon = com.raiachat.R.drawable.ic_settings
                onClick(doToast("Settings"))
            }

            primaryItem("Help") {
                icon = com.raiachat.R.drawable.ic_help
                onClick(doToast("Help"))
            }

            primaryItem("Contact") {
                icon = com.raiachat.R.drawable.ic_phone
                onClick(doToast("Contact"))
            }

            divider()

            primaryItem("Logout") {
                icon = com.raiachat.R.drawable.ic_leave
                onClick(doToast("Logout"))
            }


        }
    }

    private fun doToast(txt: String): (View?) -> Boolean = {
        toast(txt)
        false
    }
}
