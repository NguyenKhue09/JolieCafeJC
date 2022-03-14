package com.khue.joliecafejp.firebase.firebase_authentication.google_signin

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.khue.joliecafejp.utils.Constants.Companion.WEBCLIENT_ID


fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(WEBCLIENT_ID)
        .requestEmail()
        .requestProfile()
        .build()

    return GoogleSignIn.getClient(context, signInOptions)
}