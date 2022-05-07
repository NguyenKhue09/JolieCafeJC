package com.khue.joliecafejp.firebase.firebase_authentication.gmail_password_authentication

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseGmailPasswordAuth {
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    fun registerUser(email: String, password: String, name: String, context: Context, registerFunction: (Map<String, String>) -> Unit) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val firebaseUser: FirebaseUser? = task.result!!.user!!
                    firebaseUser?.let {
                        val userId = firebaseUser.uid
                        val data = mapOf(
                            "_id" to userId,
                            "fullname" to name,
                            "email" to email
                        )
                        registerFunction.invoke(data)
                    }

                    Toast.makeText(context, "Register user successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Register user failed!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                println(it)
            }
    }

    fun loginUser(email: String, password: String, context: Context, loginFunction: (String) -> Unit) {
        println("$email $password")
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result.user?.uid
                    userId?.let {
                        loginFunction.invoke(it)
                    }
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