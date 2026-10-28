package com.nohyam.memorial.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nohyam.memorial.ui.screen.component.BasicButton
import com.nohyam.memorial.ui.state.HomeUiState
import com.nohyam.memorial.ui.state.QuizState
import com.nohyam.memorial.ui.theme.DefaultPadding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    onQuizClick: () -> Unit,
    onRecordClick: () -> Unit
) {

    val quizText = when (homeUiState.quizState) {
        QuizState.NONE -> "퀴즈 없음"
        QuizState.AVAILABLE -> "퀴즈 있음"
        QuizState.COMPLETED -> "퀴즈 완료"
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Memorial") }
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HomeStatusCard("오늘 기록 ${homeUiState.recordCount}개")
            HomeStatusCard("오늘 $quizText")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicButton("기록하기", onRecordClick)
                BasicButton("퀴즈풀기", onQuizClick)
            }
        }
    }
}

@Composable
fun HomeStatusCard(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DefaultPadding),
    ) {
        Text(text = text, fontSize = 16.sp, modifier = Modifier.padding(DefaultPadding))
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        homeUiState = HomeUiState(5, QuizState.NONE),
        {},
        {}
    )
}
