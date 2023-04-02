package com.example.hiddenghosts.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hiddenghosts.R
import com.example.hiddenghosts.data.GridItem
import com.example.hiddenghosts.ui.play.PlayUIState
import com.example.hiddenghosts.ui.play.PlayViewModel
import com.example.hiddenghosts.ui.theme.*

@Composable
fun PlayScreen(
    level: Int? = null,
    onFinish: (Int) -> Unit = {},
    viewModel: PlayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState()
    val ghostItems by viewModel.gridItems.observeAsState()

    viewModel.level = level ?: 0

    PlayScreenContent(
        score = (uiState as? PlayUIState.Playing)?.score ?: 0,
        items = ghostItems ?: emptyList(),
        onRestartClick = { viewModel.startGame() },
        isPreview = (uiState as? PlayUIState.Preview) != null,
        onItemClick = viewModel::onItemClick
    )
}

@Composable
fun PlayScreenContent(
    score: Int = 0,
    onRestartClick: () -> Unit = {},
    items: List<GridItem> = emptyList(),
    isPreview: Boolean = false,
    onItemClick: (Int) -> Unit = {}
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = score.toString(),
                color = Color.White,
                style = MaterialTheme.typography.h3
            )

            OutlinedButton(
                modifier = Modifier
                    .height(48.dp)
                    .wrapContentWidth()
                    .clip(shape = RoundedCornerShape(size = 24.dp)),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = GhostColor.RestartButtonColor,
                    contentColor = GhostColor.SuccessColor

                ),
                onClick = { onRestartClick() }) {
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = stringResource(R.string.restart),
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
            }

            val columnsNumber = if (items.size > 24) 5 else 4

            LazyVerticalGrid(
                contentPadding = PaddingValues(
                    start = 30.dp,
                    top = 38.dp,
                    end = 30.dp,
                    bottom = 16.dp
                ),
                columns = GridCells.Fixed(columnsNumber), content = {
                    items(items.size) { index ->
                        GridItem(item = items[index],
                            isPreview = isPreview,
                            onItemClick = { onItemClick(index) })
                    }
                })
        }
    }
}

@Composable
fun GridItem(
    item: GridItem,
    isPreview: Boolean,
    onItemClick: () -> Unit = {}
) {
    if (isPreview) {
        if (item.isGhost) CorrectCard(onItemClick) else DefaultCard(onItemClick)
    } else {
        if (item.isClosed) DefaultCard(onItemClick)
        else if (item.isGhost) {
            CorrectCard(onItemClick)
        } else WrongCard(onItemClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CorrectCard(
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(2.dp),
        shape = RoundedCornerShape(2.dp),
        onClick = { onItemClick() }
    ) {
        Box(
            modifier = Modifier
                .background(color = GhostColor.SuccessColor)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_ghost_1),
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WrongCard(
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(2.dp),
        shape = RoundedCornerShape(2.dp),
        onClick = { onItemClick() }
    ) {
        Box(
            modifier = Modifier
                .background(color = GhostColor.WrongColor)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_ghost_wrong),
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultCard(
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(2.dp),
        shape = RoundedCornerShape(2.dp),
        onClick = { onItemClick() }
    ) {
        Box(
            modifier = Modifier
                .background(color = GhostColor.DefaultCellColor)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_general),
                contentDescription = null
            )
        }
    }
}

