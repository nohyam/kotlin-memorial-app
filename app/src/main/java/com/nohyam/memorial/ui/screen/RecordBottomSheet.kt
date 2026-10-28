package com.nohyam.memorial.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.nohyam.memorial.ui.theme.DefaultPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordBottomSheet(
    onSave: (String, String) -> Unit,
    onDismiss: () -> Unit,
    initialQuestion: String = "",
    initialAnswer: String = ""
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .padding(DefaultPadding)
        ) {

            var question by remember { mutableStateOf(initialQuestion) }
            var answer by remember { mutableStateOf(initialAnswer) }

            InputText(question, { question = it }, "질문", modifier = Modifier.fillMaxWidth())
            InputText(answer, { answer = it }, "답변", modifier = Modifier.fillMaxWidth())

            Button(
                onClick = { onSave(question, answer) },
                enabled = question.isNotEmpty() && answer.isNotEmpty()
            ) {
                Text("저장")
            }
        }
    }
}

@Composable
fun InputText(value: String, onValueChanged: (String) -> Unit, label: String,modifier: Modifier) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            if (newValue.length <= 42) {
                onValueChanged(newValue)
            }
        },
        label = { Text(label) },
        minLines = 2,
        maxLines = 2,
        supportingText = {
            if (value.length >= 42){
                Text("더이상 입력할 수 없습니다.")
            }
        }
    )
}