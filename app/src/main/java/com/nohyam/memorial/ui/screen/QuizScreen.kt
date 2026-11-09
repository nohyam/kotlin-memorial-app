
package com.nohyam.memorial.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nohyam.memorial.data.entity.RecordEntity
import com.nohyam.memorial.ui.screen.component.MemorialBackButton
import com.nohyam.memorial.ui.screen.component.MemorialBanner
import com.nohyam.memorial.ui.theme.Background
import com.nohyam.memorial.ui.theme.DarkText
import com.nohyam.memorial.ui.theme.NavyButton
import com.nohyam.memorial.ui.theme.PurpleAccent
import com.nohyam.memorial.ui.theme.SoftPurple
import com.nohyam.memorial.ui.theme.SubText

@Composable
fun QuizScreen(
    currentRecord: RecordEntity,
    currentIndex: Int,
    isSubmitted: Boolean,
    totalCount: Int,
    onNext: (Boolean) -> Unit,
    submitQuiz: () -> Unit,
    onPreviousQuiz: () -> Unit,
    onHomeClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val answerState = remember(currentIndex) {
        TextFieldState()
    }

    Scaffold(
        containerColor = Background
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            QuizBanner(
                onBackClick = {
                    focusManager.clearFocus()

                    if (currentIndex > 0) {
                        onPreviousQuiz()
                    } else {
                        onHomeClick()
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = 20.dp,
                        vertical = 24.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                QuizProgress(
                    currentIndex = currentIndex,
                    totalCount = totalCount
                )

                QuizQuestionCard(
                    question = currentRecord.question
                )

                if (isSubmitted) {
                    QuizAnswerCard(
                        answer = currentRecord.answer
                    )

                    Text(
                        text = "기억이 나셨나요?",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText,
                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onNext(false) },
                            modifier = Modifier
                                .weight(1f)
                                .height(54.dp),
                            shape = RoundedCornerShape(18.dp),
                            border = BorderStroke(
                                1.dp,
                                PurpleAccent
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = NavyButton
                            )
                        ) {
                            Text(
                                text = "기억 못함",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Button(
                            onClick = { onNext(true) },
                            modifier = Modifier
                                .weight(1f)
                                .height(54.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NavyButton
                            )
                        ) {
                            Text(
                                text = "기억함",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "나의 답",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkText
                        )

                        OutlinedTextField(
                            state = answerState,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = "기억나는 내용을 입력해보세요.",
                                    fontSize = 14.sp,
                                    color = SubText
                                )
                            },
                            lineLimits = TextFieldLineLimits.MultiLine(
                                minHeightInLines = 4,
                                maxHeightInLines = 5
                            ),
                            shape = RoundedCornerShape(18.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PurpleAccent,
                                unfocusedBorderColor = Color(
                                    0xFFD6DCF0
                                ),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                cursorColor = PurpleAccent,
                                focusedTextColor = DarkText,
                                unfocusedTextColor = DarkText
                            )
                        )
                    }

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            submitQuiz()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NavyButton
                        )
                    ) {
                        Text(
                            text = "정답 확인하기",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun QuizBanner(
    onBackClick: () -> Unit
){
    MemorialBanner(
        height = 200.dp,
        topContent = {
            MemorialBackButton {
                onBackClick()
            }
        },
        bottomContent = {
                Text(
                    text = "기억을 떠올리는 시간",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "어제의 순간을 다시 기억해보세요.",
                    fontSize = 14.sp,
                    color = Color(0xFFE0E5F4)
                )
            }
    )
}
@Composable
private fun QuizProgress(
    currentIndex: Int,
    totalCount: Int
) {
    val progress = if (totalCount > 0) {
        (currentIndex + 1).toFloat() / totalCount
    } else {
        0f
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "오늘의 기억 퀴즈",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "${currentIndex + 1} / $totalCount",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = PurpleAccent
            )
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = PurpleAccent,
            trackColor = SoftPurple,
            gapSize = 0.dp,
            drawStopIndicator = {}
        )
    }
}

@Composable
private fun QuizQuestionCard(
    question: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(SoftPurple)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Q.",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleAccent
        )

        Text(
            text = question,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
            color = DarkText
        )
    }
}

@Composable
private fun QuizAnswerCard(
    answer: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(Color(0xFFE7ECFF))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "정답 · 어제의 기록",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleAccent
        )

        Text(
            text = answer,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 26.sp,
            color = DarkText
        )
    }
}

@Preview
@Composable
fun QuizScreenPreview(){
    QuizScreen(
        currentRecord = RecordEntity(
            question = "안녕",
            answer = "잘가",
            createdAt = System.currentTimeMillis()
        ),
        currentIndex = 0,
        isSubmitted = true,
        totalCount = 1,
        onNext = {},
        submitQuiz = {  },
        onPreviousQuiz = {  }
    ) {}
}