package com.nohyam.memorial.ui.state

import com.nohyam.memorial.data.entity.RecordEntity

sealed class QuizUiState {

    object Loading : QuizUiState()

    object Empty : QuizUiState()

    data class Success(
        val records: List<RecordEntity>,
        val currentIndex: Int,
        val isSubmitted: Boolean = false,
        val isCompleted: Boolean = false
    ) : QuizUiState()

    data class Error(
        val message: String
    ) : QuizUiState()
}