package com.example.hiddenghosts.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hiddenghosts.R
import com.example.hiddenghosts.ui.theme.GhostColor
import com.example.hiddenghosts.ui.welcome.WelcomeUIState
import com.example.hiddenghosts.ui.welcome.WelcomeViewModel

@Composable
fun WelcomeScreen(
    onStartClick: (level: Int) -> Unit = {},
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    WelcomeScreenContent(
        onStartClick = onStartClick,
        level = (uiState as? WelcomeUIState.Start)?.level,
    )
}

@Composable
fun WelcomeScreenContent(
    onStartClick: (Int) -> Unit = {},
    level: Int? = null,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_background),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        if (level == null) {
            Image(
                painter = painterResource(R.drawable.ic_ghost),
                contentDescription = null
            )
        } else {
            OutlinedButton(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp)
                    .clip(shape = RoundedCornerShape(size = 24.dp)),
                onClick = { onStartClick(level) }) {
                Text(
                    text = stringResource(R.string.start_game),
                    style = MaterialTheme.typography.body1,
                    color = GhostColor.PrimaryColor
                )
            }
        }
    }
}