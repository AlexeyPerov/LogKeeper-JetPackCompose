package com.casualapps.logkeep.ui.article

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casualapps.logkeep.utils.Result
import com.casualapps.logkeep.data.logs.LogsRepository
import com.casualapps.logkeep.model.LogEntity
import com.casualapps.logkeep.ui.common.ViewModelState
import kotlinx.coroutines.launch

class LogScreenViewModel(logId: String, project: String, logsRepository: LogsRepository) : ViewModel() {
    private val _logsRepository = logsRepository
    private val _logId = logId
    private val _project = project

    private val _log = mutableStateOf<LogEntity?>(null)

    val log: State<LogEntity?>
        get() = _log

    private val _state = mutableStateOf(ViewModelState.INITIAL)

    val state: State<ViewModelState>
        get() = _state

    private fun loadLog(logId: String, project: String) {
        _state.value = ViewModelState.LOADING

        viewModelScope.launch {
            when (val result = _logsRepository.getLogInProjectById(project, logId)) {
                is Result.Success -> {
                    _log.value = result.data
                    _state.value = ViewModelState.LOADED
                }
                is Result.Error -> {
                    Log.e(javaClass.name, "Error loading log")
                    _state.value = ViewModelState.ERROR
                }
            }
        }
    }

    fun removeLog() {
        TODO("Not implemented")
    }

    init {
        loadLog(_logId, _project)
    }
}