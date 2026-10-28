package com.nohyam.memorial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohyam.memorial.data.repository.RecordRepository
import com.nohyam.memorial.ui.state.HomeUiState
import com.nohyam.memorial.ui.state.QuizState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(private val recordRepository: RecordRepository) : ViewModel() {

    private val currentDate = MutableStateFlow(LocalDate.now())

    fun refreshDate() {
        currentDate.value = LocalDate.now()
    }

    private val todayRecords = currentDate.flatMapLatest { date ->
        recordRepository.getTodayRecordList(date)
    }
    private val yesterdayRecords = currentDate.flatMapLatest { date ->
        recordRepository.getYesterdayRecordList(date)
    }

    private val homeUiState = combine(todayRecords, yesterdayRecords) { today, yesterday ->
        val recordCount = today.size
        val quizState = when {
            yesterday.isEmpty() -> QuizState.NONE
            yesterday.any { it.isRemembered == null } -> QuizState.AVAILABLE
            else -> QuizState.COMPLETED
        }

        HomeUiState(recordCount, quizState)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState(0, QuizState.NONE)

    )

    val state = homeUiState


}