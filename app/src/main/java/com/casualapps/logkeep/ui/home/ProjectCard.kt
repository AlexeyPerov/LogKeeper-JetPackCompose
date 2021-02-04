package com.casualapps.logkeep.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.casualapps.logkeep.utils.ThemedPreview

@Composable
fun ProjectCard(
    project: String,
    isSelected: Boolean,
    select: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        backgroundColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .preferredSize(140.dp, 120.dp)
            .padding(top = 10.dp)
    ) {
        Column(modifier = Modifier.clickable(onClick = { select(project) })) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = project,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "project",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}

@Preview("Project Card Regular Theme")
@Composable
fun PreviewProjectCard() {
    ThemedPreview {
        ProjectCard("AoC Dev", false, {})
    }
}

@Preview("Project Card Dark Theme")
@Composable
fun PreviewProjectCardDark() {
    ThemedPreview(darkTheme = true) {
        ProjectCard("AoC Dev", false, {})
    }
}
