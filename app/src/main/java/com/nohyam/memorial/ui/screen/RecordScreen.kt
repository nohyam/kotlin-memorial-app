package com.nohyam.memorial.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nohyam.memorial.data.entity.RecordEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    records: List<RecordEntity>,
    onAddClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditClick: (RecordEntity) -> Unit,
    onDeleteClick: (RecordEntity) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("Memorial Records")
            },
            navigationIcon = {
                IconButton(onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "뒤로가기"
                    )
                }
            })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = onAddClick,
        ) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = "ADD"
            )
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (records.isEmpty()) {
                Text("오늘 작성한 기록이 없습니다.", modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(records) { record ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(record.question, fontSize = 24.sp, modifier = Modifier.weight(1f))
                            Row{
                                IconButton({ onEditClick(record) }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "수정"
                                    )
                                }
                                IconButton({ onDeleteClick(record) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "삭제"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RecordScreenPreview() {
    RecordScreen(
        records = listOf(
            RecordEntity(
                question = "안녕하세요 원이입니다",
                answer = "ㅎㅎ",
                createdAt = System.currentTimeMillis(),
            )
        ),
        {}, {},{},{})
}
