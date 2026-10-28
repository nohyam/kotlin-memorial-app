package com.nohyam.memorial.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nohyam.memorial.data.database.AppDatabase
import com.nohyam.memorial.data.repository.RecordRepository
import com.nohyam.memorial.ui.screen.HomeScreen
import com.nohyam.memorial.viewmodel.HomeViewModel
import com.nohyam.memorial.viewmodel.HomeViewModelFactory

@Composable
fun HomeRoute(onQuizClick : () -> Unit, onRecordClick : () -> Unit){

    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val dao = database.recordDao()
    val repository = RecordRepository(dao)
    val factory = HomeViewModelFactory(repository)
    val viewmodel : HomeViewModel = viewModel(factory = factory)
    val homeUiState by viewmodel.state.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewmodel.refreshDate()
    }

    HomeScreen(homeUiState, onQuizClick, onRecordClick)
}