package com.nohyam.memorial.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nohyam.memorial.data.entity.RecordEntity
import com.nohyam.memorial.ui.theme.DefaultPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizResultScreen(
    total: Int,
    bingo: Int,
    records: List<RecordEntity>,
    onHomeClick : () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("Quiz Complete")
            },
            navigationIcon = {
                IconButton(onClick = onHomeClick) {
                    Icon(imageVector = Icons.Default.Home,
                        contentDescription = "홈으로 가기")
                }
            }
        )
    }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(DefaultPadding)
        ) {
            Text("총 ${total}개 중 ${bingo}개 기억함")

            LazyColumn(modifier = Modifier.padding(vertical = DefaultPadding) ,
                verticalArrangement = Arrangement.spacedBy(DefaultPadding)){
                items(records){
                    record ->
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Q. ${record.question}")

                        if (record.isRemembered == true){
                            Text("결과: 기억함")
                        }else{
                            Text("결과: 기억 못함")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun QuizResultScreenPreview() {
    QuizResultScreen(
        total = 5,
        bingo = 3,
        records = listOf(
            RecordEntity(
                question = "저녁에 뭐먹었지?",
                answer = "치킨먹었지요",
                createdAt = System.currentTimeMillis(),
            ),
            RecordEntity(
                question = "저녁에 뭐먹었지?",
                answer = "치킨먹었지요",
                createdAt = System.currentTimeMillis(),
            ),
            RecordEntity(
                question = "저녁에 뭐먹었지?",
                answer = "치킨먹었지요",
                createdAt = System.currentTimeMillis(),
            )
        ),
        {}
    )
}