package com.raiachat.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.view.View
import android.view.Window.FEATURE_CONTENT_TRANSITIONS
import android.view.WindowManager
import android.widget.TextView
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.Drawer
import com.raiachat.R
import com.raiachat.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KClass


class MainActivity : AppCompatActivity() {

    private lateinit var result: Drawer
    private lateinit var headerResult: AccountHeader
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null)
            toLogin()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Making notification bar transparent
        makeTransparent()

        setContentView(com.raiachat.R.layout.activity_main)
        setSupportActionBar(maintoolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        maintoolbar.title = null

        //firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val host: NavHostFragment = navHostFrag as NavHostFragment? ?: return

        // Set up Action Bar
        //val navController = host.navController

        initDrawer(savedInstanceState)


        //setUpNav(host)

    }

    private fun setUpNav(host: NavHostFragment) {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                com.raiachat.R.id.home_dest,
                com.raiachat.R.id.profile_dest,
                com.raiachat.R.id.settings_dest,
                com.raiachat.R.id.trending_dest
            ),
            result.drawerLayout
        )

        setupActionBar(host.navController, appBarConfiguration)
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
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
        return findNavController(com.raiachat.R.id.navHostFrag).navigateUp(appBarConfiguration)
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

    @SuppressLint("SetTextI18n")
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
                onClick(doNav(com.raiachat.R.id.home_dest))
                setToolbarTitle("RaiaChat")
            }

            primaryItem("Profile") {
                icon = com.raiachat.R.drawable.ic_person
                onClick(doNav(com.raiachat.R.id.profile_dest))
                setToolbarTitle("Profile")
            }

            primaryItem("Notification") {
                icon = com.raiachat.R.drawable.ic_notification
                onClick(openActivity(NotifActivity::class))
                setToolbarTitle("RaiaChat")
            }

            primaryItem("Trending") {
                icon = com.raiachat.R.drawable.ic_ratings
                onClick(doNav(com.raiachat.R.id.trending_dest))
                setToolbarTitle("Trending")
            }

            sectionHeader("Manage") {
                divider = true
            }

            primaryItem("Settings") {
                icon = com.raiachat.R.drawable.ic_settings
                onClick(doNav(com.raiachat.R.id.settings_dest))
                setToolbarTitle("Settings")
            }

            primaryItem("Help") {
                icon = com.raiachat.R.drawable.ic_help
                onClick(doNav(com.raiachat.R.id.help_dest))
                setToolbarTitle("Help")
            }

            primaryItem("Contact") {
                icon = com.raiachat.R.drawable.ic_phone
                onClick(doToast("TODO: Contact"))
                setToolbarTitle("Contact")
            }

            divider()

            primaryItem("Logout") {
                icon = com.raiachat.R.drawable.ic_leave
                onClick(openActivity(LoginActivity::class))
                setToolbarTitle("RaiaChat")
            }


        }
    }

    private fun doToast(txt: String): (View?) -> Boolean = {
        toast(txt)
        false
    }

    private fun doNav(@IdRes int: Int): (View?) -> Boolean = {
        //Navigation.createNavigateOnClickListener(int, null)
        findNavController(com.raiachat.R.id.navHostFrag).navigate(int)
        false
    }

    private fun <T : Activity> openActivity(activity: KClass<T>): (View?) -> Boolean = {
        startActivity(Intent(this@MainActivity, activity.java))
        false
    }

    private fun setToolbarTitle(title: String) {
        //val mActionBarToolbar = findViewById<androidx.appcompat.widget.Toolbar?>(R.id.maintoolbar)
        //setSupportActionBar(mActionBarToolbar)
        setSupportActionBar(maintoolbar)
        maintoolbar.title = ""
        supportActionBar?.setDisplayShowTitleEnabled(false)
        maintoolbar.title = null
        maintoolbar!!.findViewById<TextView>(R.id.toolbar_title).text = title

    }

    private fun contactUs() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>("raiachat@gmail.com"))
        setIntent(intent)
    }

    private fun toLogin() {
        val loginActivity = LoginActivity::class.java
        startActivity(Intent(this@MainActivity, loginActivity))
        finish()
    }
}
