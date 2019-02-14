package com.raiachat.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
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

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        setContentView(R.layout.activity_main)

        val homeFragment: HomeFragment = HomeFragment()

        supportFragmentManager.inTransaction {
            add(com.raiachat.R.id.bodyContent, homeFragment)
        }

        initDrawer(savedInstanceState)
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
                background = R.drawable.header
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
                icon = R.drawable.ic_home_grey
                onClick(doToast("Home"))
            }

            primaryItem("Profile") {
                icon = R.drawable.ic_person
                onClick(doToast("Profile"))
            }

            primaryItem("Notification") {
                icon = R.drawable.ic_notification
                onClick(doToast("Notification"))
            }

            primaryItem("Trending") {
                icon = R.drawable.ic_ratings
                onClick(doToast("Trending"))
            }

            sectionHeader("Manage") {
                divider = true
            }

            primaryItem("Settings") {
                icon = R.drawable.ic_settings
                onClick(doToast("Settings"))
            }

            primaryItem("Help") {
                icon = R.drawable.ic_help
                onClick(doToast("Help"))
            }

            primaryItem("Contact") {
                icon = R.drawable.ic_phone
                onClick(doToast("Contact"))
            }

            divider()

            primaryItem("Logout") {
                icon = R.drawable.ic_leave
                onClick(doToast("Logout"))
            }
        }
    }

    private fun doToast(txt: String): (View?) -> Boolean = {
        toast(txt)
        false
    }
}
