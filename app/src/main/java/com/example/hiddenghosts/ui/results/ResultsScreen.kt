package com.example.hiddenghosts.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hiddenghosts.R
import com.example.hiddenghosts.ui.theme.GhostColor

@Composable
fun ResultsScreen(
    score: Int?,
    level: Int?,
    onNextLevel: (Int) -> Unit = {}
) {
    ResultsScreenContent(
        score = score ?: 0,
        onNextLevel = { onNextLevel(level ?: 0) }
    )
}

@Composable
fun ResultsScreenContent(
    score: Int,
    onNextLevel: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_background),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center),
        ) {
            Text(
                text = score.toString(),
                style = MaterialTheme.typography.h1,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.score),
                style = MaterialTheme.typography.h3,
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .padding(bottom = 36.dp)
                .align(Alignment.BottomCenter)
        ) {
            OutlinedButton(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(end = 36.dp, start = 36.dp)
                    .clip(shape = RoundedCornerShape(size = 24.dp)),
                onClick = { onNextLevel() }) {
                Text(
                    text = stringResource(R.string.next_level),
                    style = MaterialTheme.typography.body1,
                    color = GhostColor.PrimaryColor
                )
            }
        }
    }
}