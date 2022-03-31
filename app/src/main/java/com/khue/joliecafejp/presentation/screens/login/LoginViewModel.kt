package com.khue.joliecafejp.presentation.screens.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.khue.joliecafejp.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user

    private val _count: MutableStateFlow<Int> = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    fun signIn(email: String, displayName: String){
        _user.value = User(email, displayName)
        println(user.value)
    }

    fun increaseCount() {
        _count.value = _count.value.plus(1)
    }
}