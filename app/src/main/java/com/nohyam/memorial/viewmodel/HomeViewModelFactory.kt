package com.nohyam.memorial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nohyam.memorial.data.repository.RecordRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val recordRepository: RecordRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(recordRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}