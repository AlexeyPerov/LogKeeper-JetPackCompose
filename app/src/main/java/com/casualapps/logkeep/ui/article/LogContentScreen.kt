package com.casualapps.logkeep.ui.article

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.casualapps.logkeep.model.LogEntity
import com.casualapps.logkeep.ui.common.ViewModelState
import kotlinx.coroutines.launch

@Composable
fun LogContentScreen(
    viewModel: LogScreenViewModel,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    LogContentScreen(
        viewModel = viewModel,
        onBack = onBack,
        onRemoveLog = {
            coroutineScope.launch {
                viewModel.removeLog() // TODO not implemented
            }
        }
    )
}

@Composable
fun LogContentScreen(
    viewModel: LogScreenViewModel,
    onBack: () -> Unit,
    onRemoveLog: () -> Unit
) {
    when (viewModel.state.value) {
        ViewModelState.INITIAL -> {
            Box(Modifier.fillMaxSize()) {}
        }
        ViewModelState.ERROR -> {
            TextButton(onClick = { onBack() }, Modifier.fillMaxSize()) {
                Text("Tap to go back", textAlign = TextAlign.Center)
            }
        }
        ViewModelState.LOADING -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }
        ViewModelState.LOADED -> {
            var log = viewModel.log.value ?: error("Error retrieving log");
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = log.project,
                                style = MaterialTheme.typography.subtitle2,
                                color = AmbientContentColor.current
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(Icons.Filled.ArrowBack)
                            }
                        }
                    )
                },
                bodyContent = { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)
                    LogContent(log, modifier)
                },
                bottomBar = {
                    BottomBar(
                        log = log,
                        onRemoveLog = onRemoveLog
                    )
                }
            )
        }
    }
}

@Composable
private fun BottomBar(
    log: LogEntity,
    onRemoveLog: () -> Unit
) {
    Surface(elevation = 2.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .preferredHeight(56.dp)
                .fillMaxWidth()
        ) {
            IconButton(onClick = onRemoveLog) {
                Icon(Icons.Filled.Delete)
            }
            val context = AmbientContext.current
            IconButton(onClick = { shareLog(log, context) }) {
                Icon(Icons.Filled.Share)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { }) { // TODO open in HTML page
                Icon(Icons.Filled.Web)
            }
        }
    }
}

private fun shareLog(log: LogEntity, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, log.info.title)
        putExtra(Intent.EXTRA_TEXT, log.data.contents)
    }
    context.startActivity(Intent.createChooser(intent, "Share log"))
}