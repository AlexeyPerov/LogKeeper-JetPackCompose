package com.casualapps.logkeep.ui.home

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.casualapps.logkeep.R
import com.casualapps.logkeep.ui.common.AppDrawer
import com.casualapps.logkeep.ui.common.Screen
import com.casualapps.logkeep.ui.common.ViewModelState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun HomeScreen(
    navigateTo: (Screen) -> Unit,
    viewModel: HomeScreenViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    ScreenContents(
        viewModel = viewModel,
        navigateTo = navigateTo,
        scaffoldState = scaffoldState
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenContents(
    viewModel: HomeScreenViewModel,
    navigateTo: (Screen) -> Unit,
    scaffoldState: ScaffoldState
) {
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            AppDrawer(
                currentScreen = Screen.Home,
                closeDrawer = { scaffoldState.drawerState.close() },
                navigateTo = navigateTo
            )
        },
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = { scaffoldState.drawerState.open() }) {
                        Icon(vectorResource(R.drawable.ic_launcher_foreground))
                    }
                }
            )
        },
        bodyContent = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            BodyContents(
                viewModel = viewModel,
                navigateTo = navigateTo,
                modifier = modifier
            )
        }
    )
}

@Composable
private fun BodyContents(
    viewModel: HomeScreenViewModel,
    navigateTo: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    when (viewModel.projectsState.value) {
        ViewModelState.INITIAL -> {
            Box(modifier.fillMaxSize()) {}
        }
        ViewModelState.ERROR -> {
            TextButton(onClick = { viewModel.refresh() }, modifier.fillMaxSize()) {
                Text(stringResource(id = R.string.tap_to_retry), textAlign = TextAlign.Center)
            }
        }
        ViewModelState.LOADING -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)) {
                CircularProgressIndicator()
            }
        }
        ViewModelState.LOADED -> {
            ScrollableColumn(modifier = modifier) {
                ProjectsListSection(viewModel)

                when (viewModel.logsState.value) {
                    ViewModelState.LOADED -> {
                        LogsListSection(viewModel, navigateTo)
                    }
                    ViewModelState.LOADING -> {
                        Box(modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)) {
                                LinearProgressIndicator()
                        }
                    }
                    else -> {
                        Box(modifier.fillMaxSize()) {}
                    }
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
private fun ProjectsListSection(
    viewModel: HomeScreenViewModel
) {
    var projects = viewModel.projects.collectAsState()
    var selectedProject = viewModel.selectedProject.value;

    Column {
        ScrollableRow(modifier = Modifier.padding(end = 16.dp)) {
            projects.value.forEach { project ->
                ProjectCard(project, project == selectedProject,
                {
                         viewModel.selectProject(it)
                }, Modifier.padding(start = 16.dp, bottom = 16.dp))
            }
        }
        ListDivider()
    }
}

@ExperimentalCoroutinesApi
@Composable
private fun LogsListSection(
    viewModel: HomeScreenViewModel,
    navigateTo: (Screen) -> Unit
) {
    var logs = viewModel.logs.collectAsState()
    var selectedProject = viewModel.selectedProject.value;

    Column {
        logs.value.forEach { log ->
            LogInfoCard(selectedProject, log, navigateTo)
            ListDivider()
        }
    }
}

@Composable
private fun ListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 17.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.18f)
    )
}