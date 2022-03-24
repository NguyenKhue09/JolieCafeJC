package com.khue.joliecafejp.firebase.firebase_authentication.gmail_password_authentication

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseGmailPasswordAuth {
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    fun registerUser(email: String, password: String, context: Context, navController: NavHostController) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val firebaseUser: FirebaseUser? = task.result!!.user!!

                    Toast.makeText(context, "Register user successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Register user failed!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                println(it)
            }
    }

    fun loginUser(email: String, password: String, context: Context, navController: NavHostController) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                println(it)
            }
    }

    fun forgotPassword(email: String, context: Context, navController: NavHostController) {
        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Check your email to reset password", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Some thing went wrong", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnCompleteListener {
                println(it)
            }
    }
}