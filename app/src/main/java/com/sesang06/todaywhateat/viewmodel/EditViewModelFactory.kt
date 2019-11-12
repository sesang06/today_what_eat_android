package com.sesang06.todaywhateat.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class EditViewModelFactory (val pref: SharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return EditViewModel(pref) as T
    }
}