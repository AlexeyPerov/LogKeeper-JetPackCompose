package com.casualapps.logkeep.ui.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casualapps.logkeep.utils.Result
import com.casualapps.logkeep.data.logs.LogsRepository
import com.casualapps.logkeep.model.LogInfoEntity
import com.casualapps.logkeep.ui.common.ViewModelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(logsRepository: LogsRepository) : ViewModel() {
    private val _logsRepository = logsRepository

    private val _logs = MutableStateFlow(mutableListOf<LogInfoEntity>())
    private val _projects = MutableStateFlow(mutableListOf<String>())
    private val _selectedProject = mutableStateOf("")

    val selectedProject: State<String>
        get() = _selectedProject
    val projects: StateFlow<List<String>>
        get() = _projects
    val logs: StateFlow<List<LogInfoEntity>>
        get() = _logs

    private val _projectsState = mutableStateOf(ViewModelState.INITIAL)
    private val _logsState = mutableStateOf(ViewModelState.INITIAL)

    val projectsState: State<ViewModelState>
        get() = _projectsState
    val logsState: State<ViewModelState>
        get() = _logsState

    private fun loadProjects() {
        _projectsState.value = ViewModelState.LOADING

        viewModelScope.launch {
            when (val result = _logsRepository.getProjects()) {
                is Result.Success -> {
                    _projects.value = result.data.toMutableList()
                    _projectsState.value = ViewModelState.LOADED

                    if (_selectedProject.value.isEmpty() && _projects.value.count() > 0) {
                        selectProject(_projects.value[0])
                    }
                }
                is Result.Error -> {
                    Log.e(javaClass.name, "Error loading projects")
                    _projectsState.value = ViewModelState.ERROR
                }
            }
        }
    }

    private fun loadLogsForProject(project: String) {
        _logsState.value = ViewModelState.LOADING

        viewModelScope.launch {
            when (val result = _logsRepository.getLogsForProject(project)) {
                is Result.Success -> {
                    _logs.value = result.data.toMutableList()
                    _logsState.value = ViewModelState.LOADED
                }
                is Result.Error -> {
                    Log.e(javaClass.name, "Error loading logs")
                    _logsState.value = ViewModelState.ERROR
                }
            }
        }
    }

    fun refresh() {
        loadProjects()
    }

    fun selectProject(newProjectSelection: String) {
        _selectedProject.value = newProjectSelection
        loadLogsForProject(_selectedProject.value)
    }

    init {
        loadProjects()
    }
}