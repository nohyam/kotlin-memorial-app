package com.nohyam.memorial.ui.route

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nohyam.memorial.data.database.AppDatabase
import com.nohyam.memorial.data.entity.RecordEntity
import com.nohyam.memorial.data.repository.RecordRepository
import com.nohyam.memorial.ui.screen.RecordBottomSheet
import com.nohyam.memorial.ui.screen.RecordScreen
import com.nohyam.memorial.viewmodel.RecordViewModel
import com.nohyam.memorial.viewmodel.RecordViewModelFactory


@Composable
fun RecordRoute(onBackClick: () -> Unit) {

    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val dao = database.recordDao()
    val repository = RecordRepository(dao)
    val factory = RecordViewModelFactory(repository)
    val viewModel: RecordViewModel = viewModel(factory = factory)
    val todayRecords by viewModel.todayRecords.collectAsStateWithLifecycle()

    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var selectedRecord by remember {
        mutableStateOf<RecordEntity?>(null)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }


    if (showBottomSheet) {

        val record = selectedRecord

        if (record != null){
            RecordBottomSheet(
                onSave = {question, answer ->
                    viewModel.editRecord(
                        recordEntity = record,
                        question = question,
                        answer = answer,
                        onResult = {success ->
                            if (success){
                                showBottomSheet = false
                            }else{
                                Toast.makeText(
                                    context,
                                    "기록 수정에 실패했습니다. 다시 시도해 주세요.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                },
                onDismiss = { showBottomSheet = false },
                initialQuestion = record.question,
                initialAnswer = record.answer,
                isEditing = true
            )
        }else{
            RecordBottomSheet(
                onSave = { question, answer ->
                    viewModel.addRecord(question, answer, onResult = {success ->
                        if (success){
                            showBottomSheet = false
                        }else{
                            Toast.makeText(
                                context,
                                "기록 저장에 실패했습니다. 다시 시도해 주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                },
                onDismiss = {
                    showBottomSheet = false
                }
            )
        }

    }

    if (showDialog) {
        val record = selectedRecord
        if (record != null){
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = { Text("정말 삭제하시겠습니까?") },
                text = { Text("삭제하면 복구할 수 없습니다.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteRecord(
                                recordEntity = record,
                                onResult = { success ->
                                    if (success) {
                                        showDialog = false
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "기록 삭제에 실패했습니다. 다시 시도해 주세요.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            )
                        }
                    ) {
                        Text("확인")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDialog = false }
                    ) {
                        Text("취소")
                    }
                }
            )
        }

    }


    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.refreshDate()
    }

    RecordScreen(
        records = todayRecords,
        onAddClick = {
            selectedRecord = null
            showBottomSheet = true
        },
        onBackClick = onBackClick,
        onEditClick = { record ->
            selectedRecord = record
            showBottomSheet = true
        },
        onDeleteClick = {record ->
            selectedRecord = record
            showDialog = true
        },
    )


}
