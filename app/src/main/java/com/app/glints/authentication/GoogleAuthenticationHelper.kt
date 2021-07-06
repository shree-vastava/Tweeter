package com.app.glints.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.app.glints.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

/**
 * This class handles the Authentication using
 * Google Login. The class needs a context and
 * uses it to get the Google Sign in client
 * The Google signin client and Firebase handle
 * the authentication UI and validations. Once completed,
 * we get the result in the callback.
 *
 * We can have similar helper classes for other authentication
 * mechanisms if needed in future (Eg: Faccebook, linkedin etc)
 */

class GoogleAuthenticationHelper (private val context: Context) {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var googleSignInClient: GoogleSignInClient
    var googleIntentInitiated = MutableLiveData<Intent>()
    var isAuthSuccessful = MutableLiveData<Boolean> ()

    /**
     * initialize the google signin client
     * and starts the signin process with
     * the Google UI
     */
    fun initLogin(){
        setupGoogleSignin()
        signIn()
    }

    private fun setupGoogleSignin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    private fun signIn() {
        googleIntentInitiated.postValue(googleSignInClient.signInIntent)
    }


    /**
     * Once we get the response from Google,
     * we use the token to auth against the
     * Firebase.
     */
    fun processGoogleLogin(data: Intent){
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            println("firebaseAuthWithGoogle: " + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            println("Google sign in failed: $e")
        }
    }


    /**
     * This handles the firebase auth
     * using the Google credentials. Once the google
     * returns OK for the credentials, we do this
     * process to finally authenticate using Firebase
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(context as Activity) { task ->
                    if (task.isSuccessful) {
                        println("signInWithCredential:success")
                        isAuthSuccessful.postValue(true)
                    } else {
                        // If sign in fails, display a message to the user.
                        println("signInWithCredential:failure: "+task.exception.toString())
                        isAuthSuccessful.postValue(false)
                    }
                }
    }

    /**
     * Returns current user.
     * If no user is logged in, it will
     * return null
     */
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logout() {
        auth.signOut()
        googleSignInClient.signOut()
    }
}