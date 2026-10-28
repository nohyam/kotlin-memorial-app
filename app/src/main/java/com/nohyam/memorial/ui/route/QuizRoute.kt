package com.nohyam.memorial.ui.route

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nohyam.memorial.data.database.AppDatabase
import com.nohyam.memorial.data.repository.RecordRepository
import com.nohyam.memorial.ui.screen.QuizResultScreen
import com.nohyam.memorial.ui.screen.QuizScreen
import com.nohyam.memorial.ui.state.QuizUiState
import com.nohyam.memorial.viewmodel.QuizViewModel
import com.nohyam.memorial.viewmodel.QuizViewModelFactory

@Composable
fun QuizRoute(onHomeClick: () -> Unit) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val dao = database.recordDao()
    val repository = RecordRepository(dao)
    val factory = QuizViewModelFactory(repository)
    val viewModel: QuizViewModel = viewModel(factory = factory)
    val quizState by viewModel.quizState.collectAsStateWithLifecycle()
    var showDialog by remember {
        mutableStateOf(false)
    }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = { Text("정말 나가시겠습니까?") },
            text = { Text("퀴즈를 완료하지 않고 나가면 지금까지 선택한 평가 결과는 저장되지 않습니다.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onHomeClick()
                    }
                ) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("취소")
                }
            }
        )
    }


    when (val state = quizState) {
        QuizUiState.Empty -> Text("풀 수 있는 퀴즈가 없습니다.")
        is QuizUiState.Error -> Text(state.message)
        QuizUiState.Loading -> Text("불러오는 중....")
        is QuizUiState.Success -> {
            val currentIndex = state.currentIndex
            val currentRecord = state.records[currentIndex]
            val isSubmitted = state.isSubmitted
            val isCompleted = state.isCompleted

            if (isCompleted) {
                QuizResultScreen(
                    total = state.records.size,
                    bingo = state.records.count { record ->
                        record.isRemembered == true
                    },
                    records = state.records,
                    onHomeClick = onHomeClick
                )
            } else {
                QuizScreen(
                    currentRecord = currentRecord,
                    currentIndex = currentIndex,
                    isSubmitted = isSubmitted,
                    totalCount = state.records.size,
                    onNext = { isRemembered -> viewModel.evaluateCurrentQuiz(isRemembered) },
                    submitQuiz = { viewModel.submitQuiz() },
                    onPreviousQuiz = { viewModel.previousQuiz() },
                    onHomeClick = {showDialog = true}
                )
                BackHandler(enabled = true) {
                    showDialog = true
                }
            }
        }
    }
}