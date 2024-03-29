package com.khue.joliecafejp.firebase.firebase_authentication.face_book_signin

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.result.ActivityResultRegistryOwner
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth


class FirebaseFacebookLogin {

    fun facebookLogin(context: Context, callbackManager: CallbackManager, auth: FirebaseAuth, loginFunction: (String) -> Unit) {
        LoginManager.getInstance().logInWithReadPermissions(
            context as ActivityResultRegistryOwner,
            callbackManager = callbackManager,
            listOf("email", "public_profile")
        )

        println("Facebook Login")
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                println("facebook:onSuccess:$result")
                handleFacebookAccessToken(result.accessToken, context, auth, loginFunction)
            }

            override fun onCancel() {
                println("facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                println("facebook:onError")
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken, context: Context, auth: FirebaseAuth, loginFunction: (String) -> Unit) {
        println("handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    println("signInWithCredential:success")
                    val user = auth.currentUser
                    println(user?.displayName)
                    Toast.makeText(
                        context, "Welcome back ${user?.displayName}",
                        Toast.LENGTH_SHORT
                    ).show()
                    val userId = user?.uid
                    userId?.let {
                        loginFunction.invoke(it)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    println("signInWithCredential:failure  ${task.exception}")
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun facebookLoginSignOut() {
        LoginManager.getInstance().logOut()
    }
}