package com.app.glints.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.glints.authentication.GoogleAuthenticationHelper
import com.app.glints.R
import com.app.glints.ui.tweet.MainActivity
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var googleAuthenticationHelper: GoogleAuthenticationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        /**
         * Initializing the Google Authentication helper class.
         * The class needs a context to get the Google Signin client.
         */
        googleAuthenticationHelper = GoogleAuthenticationHelper(this)


        /**
         * On click of button, we init the login
         * and Firebase handles the UI for google login
         */
        btn_login.setOnClickListener {
            googleAuthenticationHelper.initLogin()
        }

        /**
         * If the user already exists, it means it is logged in
         * Moving to the main activity if user logged in
         */
        if(googleAuthenticationHelper.getCurrentUser()!=null){
            moveToNextActivity(googleAuthenticationHelper.getCurrentUser())
        }

        /**
         * Starting the google authentication intent.
         */
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    googleAuthenticationHelper.processGoogleLogin(data)
                }
            }
        }

        googleAuthenticationHelper.googleIntentInitiated.observe(this, Observer {
            resultLauncher.launch(it)
        })

        /**
         * When the login process is complete,
         * going to main activity if success,
         * showing toast if fails
         */
        googleAuthenticationHelper.isAuthSuccessful.observe(this, Observer {
            if (it){
                moveToNextActivity(googleAuthenticationHelper.getCurrentUser())
            }
            else{
                showErrorToast()
            }
        })
    }


    private fun moveToNextActivity(user: FirebaseUser?) {
        if(user!=null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun showErrorToast(){
        Toast.makeText(this, getString(R.string.error_in_login), Toast.LENGTH_LONG).show()
    }
}