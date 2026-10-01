package com.nohyam.memorial.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nohyam.memorial.ui.screen.component.BasicButton
import com.nohyam.memorial.ui.theme.BasicPadding

private val isCheck: Boolean = false

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen() {
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
                    .fillMaxHeight(0.5f)
                    .align(Alignment.Center)
                    .padding(BasicPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(BasicPadding),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Previous Quiz"
                            )
                        }

                        Text(
                            "1/4", fontSize = 32.sp,
                        )
                    }

                    Text(
                        "Q. 질문칸입니다.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        state = rememberTextFieldState(),
                        label = { Text("나의 답") },
                    )

                    if (isCheck) {
                        Text("원래 나의 답", modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            BasicButton(
                                label = "기억 안남",
                                onClick = {},
                                modifier = Modifier.weight(1f)
                            )
                            BasicButton(
                                label = "기억 남",
                                onClick = {},
                                modifier = Modifier.weight(1f)
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            BasicButton(
                                label = "기억 안남",
                                onClick = {},
                                modifier = Modifier.weight(1f)
                            )
                            BasicButton(label = "제출", onClick = {}, modifier = Modifier.weight(1f))
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
    QuizScreen()
}