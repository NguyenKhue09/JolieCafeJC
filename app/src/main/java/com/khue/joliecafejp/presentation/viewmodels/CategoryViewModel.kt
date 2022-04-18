package com.khue.joliecafejp.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.khue.joliecafejp.utils.Constants.Companion.CATEGORY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(){


    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _selectedCategory: MutableState<String> =
        mutableStateOf(value = "All")
    val selectedCategory: State<String> = _selectedCategory

    init {
        val category = savedStateHandle.get<String>(CATEGORY)
        updateSelectedCategory(category?: "All")
        TODO("Call api load new item for category")
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun updateSelectedCategory(newValue: String) {
        _selectedCategory.value = newValue
    }
}