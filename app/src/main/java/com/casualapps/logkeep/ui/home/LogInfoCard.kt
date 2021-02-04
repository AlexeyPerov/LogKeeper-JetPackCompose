package com.casualapps.logkeep.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.casualapps.logkeep.data.logs.mock.log3
import com.casualapps.logkeep.model.LogInfoEntity
import com.casualapps.logkeep.ui.common.Screen
import com.casualapps.logkeep.utils.ThemedPreview
import com.casualapps.logkeep.utils.formatDate

@Composable
fun LogInfoCard(project: String, log: LogInfoEntity, navigateTo: (Screen) -> Unit) {
    Row(
        Modifier
            .clickable(onClick = { navigateTo(Screen.LogContents(project, log.id)) })
            .padding(16.dp)
    ) {
        LogImage(
            modifier = Modifier.padding(end = 16.dp)
        )
        Column(Modifier.weight(1f)) {
            LogTitle(log = log)
            LogMetadata(
                log = log,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun LogImage(modifier: Modifier = Modifier) {
    val image = Icons.Filled.Notes

    Image(
        image,
        modifier = modifier
            .preferredSize(40.dp, 40.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun LogTitle(log: LogInfoEntity) {
    Text(log.title, style = MaterialTheme.typography.subtitle1,
        maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.preferredHeight(60.dp))
}

@Composable
fun LogMetadata(
    log: LogInfoEntity,
    modifier: Modifier = Modifier
) {
    Row(modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Providers(AmbientContentAlpha provides ContentAlpha.medium) {
            val textStyle = MaterialTheme.typography.subtitle2
            Text(
                text = log.author,
                style = textStyle
            )
            Text(
                text = formatDate(log.createdAt, "yyyy-MM-dd HH:mm"),
                style = textStyle
            )
        }
    }
}

@Preview("LogInfo Card Regular Theme")
@Composable
fun PreviewLogInfoCard() {
    ThemedPreview {
        LogInfoCard("Test Project", log3) {}
    }
}

@Preview("LogInfo Card Dark Theme")
@Composable
fun PreviewLogInfoCardDark() {
    ThemedPreview(darkTheme = true) {
        LogInfoCard("Test Project", log3) {}
    }
}