package com.nohyam.memorial.ui.screen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nohyam.memorial.ui.theme.BasicPadding

@Composable
fun BasicButton(label: String, onClick : () ->Unit , modifier: Modifier = Modifier){
    Button(onClick = onClick, modifier = modifier.padding(BasicPadding)) {
        Text(label)
    }
}