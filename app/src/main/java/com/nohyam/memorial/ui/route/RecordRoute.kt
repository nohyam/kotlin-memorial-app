package com.nohyam.memorial.ui.route

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
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
                        answer = answer
                    )
                    showBottomSheet = false
                },
                onDismiss = { showBottomSheet = false },
                initialQuestion = record.question,
                initialAnswer = record.answer
            )
        }else{
            RecordBottomSheet(
                onSave = { question, answer ->
                    viewModel.addRecord(question, answer)
                    showBottomSheet = false
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
                            showDialog = false
                            viewModel.deleteRecord(recordEntity = record)
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
