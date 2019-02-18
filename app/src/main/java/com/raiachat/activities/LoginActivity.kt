package com.raiachat.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import com.raiachat.util.Constants
import com.raiachat.util.toast
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.reflect.KClass


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mPhoneNumberUtil: PhoneNumberUtil
    private lateinit var mResendToken: PhoneAuthProvider.ForceResendingToken

    private var mVerificationInProgress = false
    private var mVerificationId = ""
    private var mPhoneNum = ""

    companion object {
        val TAG = "login"
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null)
            toMain()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(com.raiachat.R.layout.activity_login)

        //firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        initUI()
        checkPermissionReadPhone()

        signinBtn.setOnClickListener { toast("TODO: phone") }
        loginFb.setOnClickListener { toast("TODO: Fb") }
        loginGoogle.setOnClickListener { toast("TODO: Google") }
    }

    private fun initUI() {
        loginGroup.visibility = GONE
    }

    private fun signOnClick(v: View){
        if(validatePhone()){

        }
    }

    private fun validateCode(): Boolean  {

        var valid = true

        val phone = loginPhone.editText?.text.toString()
        if (phone.isEmpty()) {
            loginPhone.error = "invalid code"
            valid = false
        } else {
            loginPhone.error = null
        }

        return valid
    }

    private fun validatePhone(): Boolean {
        var valid = true

        val phone = loginPhone.editText?.text.toString()

        val mPhoneNumberUtil = PhoneNumberUtil.createInstance(this)
        var kenyaNumberProto: Phonenumber.PhoneNumber? = null
        try {
            kenyaNumberProto = mPhoneNumberUtil.parse(phone, "KE")
        } catch (e: NumberParseException) {
            Log.e(TAG, "validate: NumberParseException was thrown: ", e)
        }

        if (phone.isEmpty() || !mPhoneNumberUtil.isValidNumber(kenyaNumberProto)) {
            loginPhone.error = "enter a valid phone number"
            valid = false
        } else {
            loginPhone.error = null
            Log.d(TAG,
                "validatePhone: " + kenyaNumberProto!!.countryCode + "" + kenyaNumberProto.nationalNumber
            )
            mPhoneNum = "+254" + kenyaNumberProto.nationalNumber
        }


        return valid
    }

    private fun <T : Activity> openActivity(activity: KClass<T>) {
        startActivity(Intent(this@LoginActivity, activity.java))
    }

    private fun toMain() {
        val mActivity = MainActivity::class.java
        val intent = Intent(this@LoginActivity, mActivity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }


    @SuppressLint("MissingPermission")
    private fun getPhone(): String {
        val phoneMgr = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return phoneMgr.line1Number
    }
    private fun checkPermissionReadPhone() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_PHONE_STATE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    // permission is granted
                    mPhoneNum = getPhone()
                    loginPhone.editText?.setText(getPhone())
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    // check for permanent denial of permission
                    if (response.isPermanentlyDenied) {
                        // navigate user to app settings

                        val builder = AlertDialog.Builder(this@LoginActivity)
                        builder.setTitle("Need Permissions")
                        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
                        builder.setPositiveButton("GOTO SETTINGS") { dialog, _ ->
                            dialog.cancel()
                            Constants.openSettings(this@LoginActivity)
                        }
                        builder.setNegativeButton("Cancel") { dialog, _ ->
                            dialog.cancel()
                            //todo check and confirm if finish is required here
                            //finish();
                        }
                        builder.show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: com.karumi.dexter.listener.PermissionRequest,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }




}
