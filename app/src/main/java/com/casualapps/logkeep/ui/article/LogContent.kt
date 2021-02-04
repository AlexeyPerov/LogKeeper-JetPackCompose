package com.casualapps.logkeep.ui.article

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.AmbientContentColor
import androidx.compose.material.Colors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.casualapps.logkeep.data.logs.mock.testLog
import com.casualapps.logkeep.model.LogEntity
import com.casualapps.logkeep.model.LogInfoEntity
import com.casualapps.logkeep.utils.ThemedPreview
import com.casualapps.logkeep.utils.formatDate

private val defaultSpacerSize = 16.dp

@Composable
fun LogContent(log: LogEntity, modifier: Modifier = Modifier) {
    ScrollableColumn(
        modifier = modifier.padding(horizontal = defaultSpacerSize)
    ) {
        Spacer(Modifier.preferredHeight(defaultSpacerSize))
        Text(text = log.info.title, style = MaterialTheme.typography.h4)
        Spacer(Modifier.preferredHeight(defaultSpacerSize))
        PostMetadata(log.info)
        Spacer(Modifier.preferredHeight(24.dp))
        Paragraph(log.data.contents)
        Spacer(Modifier.preferredHeight(48.dp))
    }
}

@Composable
private fun PostMetadata(logInfo: LogInfoEntity) {
    val typography = MaterialTheme.typography
    Row {
        Image(
            imageVector = Icons.Filled.AccountCircle,
            modifier = Modifier.preferredSize(40.dp),
            colorFilter = ColorFilter.tint(AmbientContentColor.current),
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.preferredWidth(8.dp))
        Column {
            Text(
                text = logInfo.author,
                style = typography.caption,
                modifier = Modifier.padding(top = 4.dp)
            )

            Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = formatDate(logInfo.createdAt, "yyyy-MM-dd HH:mm:ss"),
                    style = typography.caption
                )
            }
        }
    }
}

@Composable
private fun Paragraph(paragraph: String) {
    Box(modifier = Modifier.padding(bottom = 2.dp)) {
        CodeBlockParagraph(
            text = paragraph,
            textStyle = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun CodeBlockParagraph(
    text: String,
    textStyle: TextStyle
) {
    Surface(
        color = MaterialTheme.colors.codeBlockBackground,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = text,
            style = textStyle
        )
    }
}

private val Colors.codeBlockBackground: Color
    get() = onSurface.copy(alpha = .15f)

@Preview("Post content")
@Composable
fun PreviewPost() {
    ThemedPreview {
        LogContent(log = testLog)
    }
}

@Preview("Post content dark theme")
@Composable
fun PreviewPostDark() {
    ThemedPreview(darkTheme = true) {
        LogContent(log = testLog)
    }
}
