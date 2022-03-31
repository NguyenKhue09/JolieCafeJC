package com.khue.joliecafejp.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _isEdit: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEdit: StateFlow<Boolean> = _isEdit

    fun editProfile(request: Boolean) {
        _isEdit.value = request
    }
}