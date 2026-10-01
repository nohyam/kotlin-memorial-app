package com.nohyam.memorial.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(records: List<String>, onAddClick: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("Memorial Records")
            }
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = onAddClick,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "ADD"
            )
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
        {
            if (records.isEmpty()) {
                Text("오늘 작성한 기록이 없습니다.", modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(records) { record ->
                        Text(record)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RecordScreenPreview() {
    RecordScreen(records = listOf("a", "b", "c"), {})
}