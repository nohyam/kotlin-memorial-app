package com.nohyam.memorial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohyam.memorial.data.repository.RecordRepository
import com.nohyam.memorial.ui.state.QuizUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class QuizViewModel(private val recordRepository: RecordRepository) : ViewModel() {
    private val quizListFlow = recordRepository.getYesterdayRecordList(LocalDate.now())
    private val currentIndexFlow = MutableStateFlow(0)
    private val isSubmitted = MutableStateFlow(false)
    private val results = mutableMapOf<Long, Boolean>()
    private val completed = MutableStateFlow(false)
    private val quizUiState = combine(
        quizListFlow,
        currentIndexFlow,
        isSubmitted,
        completed
    ) { records, currentIndex, isSubmitted, completed ->
        if (records.isEmpty()) {
            QuizUiState.Empty
        } else {
            QuizUiState.Success(records, currentIndex, isSubmitted, completed)
        }
    }.catch { e ->
        emit(QuizUiState.Error(e.message ?: "알 수 없는 오류"))
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = QuizUiState.Loading
        )

    val quizState = quizUiState

    fun evaluateCurrentQuiz(isRemembered: Boolean) {

        val state = quizState.value

        if (state is QuizUiState.Success) {
            val quizId = state.records[state.currentIndex].id

            results[quizId] = isRemembered

            if (state.currentIndex == state.records.lastIndex) {
                viewModelScope.launch {
                    val updatesRecords = state.records.map { record ->
                        record.copy(
                            isRemembered = results[record.id] ?: record.isRemembered
                        )
                    }

                    recordRepository.saveRecordList(updatesRecords)

                    completed.value = true
                }
            } else {
                isSubmitted.value = false
                currentIndexFlow.value++
            }
        }
    }

    fun submitQuiz() {
        isSubmitted.value = true
    }

    fun previousQuiz() {
        if (currentIndexFlow.value > 0) {
            currentIndexFlow.value--
            isSubmitted.value = false
        }
    }
}