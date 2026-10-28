package com.nohyam.memorial.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nohyam.memorial.data.entity.RecordEntity
import com.nohyam.memorial.ui.screen.component.BasicButton
import com.nohyam.memorial.ui.theme.DefaultPadding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    currentRecord: RecordEntity,
    currentIndex: Int,
    isSubmitted: Boolean,
    totalCount: Int,
    onNext: (Boolean) -> Unit,
    submitQuiz: () -> Unit,
    onPreviousQuiz : () -> Unit,
    onHomeClick : () -> Unit

    ) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Memorial Quiz") })
        }) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(DefaultPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(DefaultPadding),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = if (currentIndex > 0){
                                onPreviousQuiz
                            } else {
                                onHomeClick
                            }

                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Previous Quiz"
                            )
                        }

                        Text(
                            "${currentIndex + 1}/$totalCount", fontSize = 32.sp,
                        )
                    }

                    Text(
                        "Q. ${currentRecord.question}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        state = remember(currentIndex) {
                            TextFieldState()
                        },
                        label = { Text("나의 답") },
                    )

                    if (isSubmitted) {
                        Text(currentRecord.answer)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicButton(
                                label = "기억 안남",
                                onClick = { onNext(false) },
                                modifier = Modifier.weight(1f)
                            )
                            BasicButton(
                                label = "기억 남",
                                onClick = { onNext(true) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicButton(label = "제출", onClick = {
                                focusManager.clearFocus()
                                submitQuiz()
                            }, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun QuizScreenPreview() {
    QuizScreen(
        currentRecord = RecordEntity(
            question = "저녁에 뭐먹었지?",
            answer = "치킨먹었지요",
            createdAt = System.currentTimeMillis(),
        ),
        currentIndex = 0,
        totalCount = 3,
        onNext = {},
        isSubmitted = false,
        submitQuiz = {},
        onPreviousQuiz = {},
        onHomeClick = {}
    )
}
