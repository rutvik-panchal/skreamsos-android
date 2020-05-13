package com.rutvik.apps.skreamsos.utils

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FirebaseHelper {

    var auth = Firebase.auth

    fun getUser(): FirebaseUser? = Firebase.auth.currentUser

    fun signOut() = Firebase.auth.signOut()
}
