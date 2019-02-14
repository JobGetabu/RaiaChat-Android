package com.raiachat.activities

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.view.View
import android.view.Window.FEATURE_CONTENT_TRANSITIONS
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
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
import com.raiachat.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KClass


class MainActivity : AppCompatActivity() {

    private lateinit var result: Drawer
    private lateinit var headerResult: AccountHeader
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Making notification bar transparent
        makeTransparent()

        setContentView(R.layout.activity_main)

        val host: NavHostFragment = navHostFrag as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        val homeFragment = HomeFragment()

        /*supportFragmentManager.inTransaction {
            add(com.raiachat.R.id.bodyContent, homeFragment)
        }*/

        initDrawer(savedInstanceState)

    }

    private fun setUpNav(host: NavHostFragment){
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_dest, R.id.profile_dest, R.id.settings_dest, R.id.trending_dest),
            result.drawerLayout )

        setupActionBar(host.navController , appBarConfiguration)
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        // TODO STEP 9.6 - Have NavigationUI handle what your ActionBar displays
        // This allows NavigationUI to decide what label to show in the action bar
        // By using appBarConfig, it will also determine whether to
        // show the up arrow or drawer menu icon
        setupActionBarWithNavController(navController, appBarConfig)
        // TODO END STEP 9.6
    }

    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return findNavController(R.id.navHostFrag).navigateUp(appBarConfiguration)
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
                onClick(doNav(R.id.home_dest))

            }

            primaryItem("Profile") {
                icon = com.raiachat.R.drawable.ic_person
                onClick(doNav(R.id.profile_dest))
            }

            primaryItem("Notification") {
                icon = com.raiachat.R.drawable.ic_notification
                onClick(openActivity(NotifActivity::class))
            }

            primaryItem("Trending") {
                icon = com.raiachat.R.drawable.ic_ratings
                onClick(doNav(R.id.trending_dest))
            }

            sectionHeader("Manage") {
                divider = true
            }

            primaryItem("Settings") {
                icon = com.raiachat.R.drawable.ic_settings
                onClick(doNav(R.id.settings_dest))
            }

            primaryItem("Help") {
                icon = com.raiachat.R.drawable.ic_help
                onClick(doNav(R.id.help_dest))
            }

            primaryItem("Contact") {
                icon = com.raiachat.R.drawable.ic_phone
                onClick(doToast("TODO: Contact"))
            }

            divider()

            primaryItem("Logout") {
                icon = com.raiachat.R.drawable.ic_leave
                onClick(doToast("TODO: Logout"))
            }


        }
    }

    private fun doToast(txt: String): (View?) -> Boolean = {
        toast(txt)
        false
    }

    private fun doNav(@IdRes int: Int): (View?) -> Boolean ={
        //Navigation.createNavigateOnClickListener(int, null)
        findNavController(R.id.navHostFrag).navigate(int)
        false
    }

    private fun <T : Activity> openActivity(activity: KClass<T>): (View?) -> Boolean = {
        startActivity(Intent(this@MainActivity, activity.java))
        false
    }
}
