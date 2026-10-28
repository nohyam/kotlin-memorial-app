package com.nohyam.memorial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohyam.memorial.data.entity.RecordEntity
import com.nohyam.memorial.data.repository.RecordRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class RecordViewModel(private val recordRepository: RecordRepository) : ViewModel() {

    private val currentDate = MutableStateFlow(LocalDate.now())

    fun refreshDate(){
        currentDate.value = LocalDate.now()
    }
    private val todayRecordFlow= currentDate.flatMapLatest {
        date -> recordRepository.getTodayRecordList(date)
    }

    val todayRecords = todayRecordFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    fun addRecord(question : String, answer : String){
        viewModelScope.launch { 
            recordRepository.addRecord(
                recordEntity = RecordEntity(
                    question = question,
                    answer = answer,
                    createdAt = System.currentTimeMillis(),
                )
            )
        }
    }

    fun editRecord(recordEntity: RecordEntity, question: String, answer: String){
        viewModelScope.launch {
            recordRepository.editRecord(
                recordEntity = recordEntity.copy(
                    question = question,
                    answer = answer,
                )
            )
        }
    }

    fun deleteRecord(recordEntity: RecordEntity) {
        viewModelScope.launch {
            recordRepository.deleteRecord(
                recordEntity = recordEntity
            )
        }
    }
}