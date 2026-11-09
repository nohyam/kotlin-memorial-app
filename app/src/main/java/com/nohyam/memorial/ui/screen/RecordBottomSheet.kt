package com.nohyam.memorial.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nohyam.memorial.ui.theme.Background
import com.nohyam.memorial.ui.theme.DarkText
import com.nohyam.memorial.ui.theme.NavyButton
import com.nohyam.memorial.ui.theme.PurpleAccent
import com.nohyam.memorial.ui.theme.SubText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordBottomSheet(
    onSave: (String, String) -> Unit,
    onDismiss: () -> Unit,
    initialQuestion: String = "",
    initialAnswer: String = "",
    isEditing : Boolean = false
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var question by remember{
        mutableStateOf(initialQuestion)
    }

    var answer by remember {
        mutableStateOf(initialAnswer)
    }

    val canSave = question.isNotBlank() &&
            answer.isNotBlank()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Background,
        shape = RoundedCornerShape(
            topStart = 28.dp,
            topEnd = 28.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 24.dp
                ),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = if (isEditing) {
                        "기록 수정"
                    } else {
                        "새 기록 추가"
                    },
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "오늘의 생각을 기억으로 남겨보세요.",
                    fontSize = 13.sp,
                    color = SubText
                )

            }

            RecordInputField(
                value = question,
                onValueChange = { question = it },
                label = "질문",
                placeholder = "기억하고 싶은 질문을 입력하세요.",
                maxLength = 40,
                maxLines = 2,
            )

            RecordInputField(
                value = answer,
                onValueChange = { answer = it },
                label = "답변",
                placeholder = "질문에 대한 답변을 입력하세요.",
                maxLength = 80,
                maxLines = 4
            )

            Button(
                onClick = { onSave(question, answer) },
                enabled = canSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NavyButton,
                    disabledContainerColor = NavyButton.copy(
                        alpha = 0.35f
                    )
                )
            ) {
                Text(
                    text = if (isEditing) {
                        "수정 완료"
                    } else {
                        "기록 저장"
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun RecordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    maxLength: Int,
    maxLines: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkText
        )

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 13.sp,
                    color = SubText
                )
            },
            minLines = maxLines,
            maxLines = maxLines,
            shape = RoundedCornerShape(18.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PurpleAccent,
                unfocusedBorderColor = Color(0xFFD6DCF0),
                cursorColor = PurpleAccent,
            ),
            supportingText = {
                Text(
                    text = "${value.length} / $maxLength",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontSize = 12.sp
                )
            }
        )
    }
}
