package com.khue.joliecafejp.firebase.firebase_storage

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage

class FirebaseStorageImpl {

    companion object {
        private lateinit var instance: FirebaseStorageImpl

        fun getInstance(): FirebaseStorageImpl {
            if(!::instance.isInitialized) {
               instance = FirebaseStorageImpl()
            }
            return instance
        }
    }
    
    private val mFireStorage = FirebaseStorage.getInstance()

    fun uploadFile(file: Uri, fileName: String, context: Context, root: String, updateUserData: (String) -> Unit) {
        val ref = mFireStorage.reference
            .child("$root/$fileName")
        val uploadTask = ref.putFile(file)

        try {
            uploadTask.addOnPausedListener {
                println("Upload image paused")
            }.addOnFailureListener {
                println(it.message)
                Toast.makeText(context, "Upload image failed", Toast.LENGTH_LONG).show()
            }.addOnSuccessListener { _ ->
                Toast.makeText(context, "Upload image successfully", Toast.LENGTH_LONG).show()
                ref.downloadUrl.addOnSuccessListener {
                    updateUserData(it.toString())
                }.addOnFailureListener {
                    println(it.message)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}