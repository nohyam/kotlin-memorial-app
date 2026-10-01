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
import com.nohyam.memorial.ui.theme.BasicPadding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Memorial") }
        )
    }) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            HomeStatusCard("오늘 기록 3개")
            HomeStatusCard("오늘 퀴즈 있음")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                BasicButton("기록하기", {})
                BasicButton("퀴즈풀기", {})
            }
        }
    }
}

@Composable
fun HomeStatusCard(text : String){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(BasicPadding),) {
        Text(text = text, fontSize = 16.sp, modifier = Modifier.padding(BasicPadding))
    }
}



@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
