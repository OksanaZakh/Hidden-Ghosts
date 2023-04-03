package com.example.hiddenghosts.ui.play

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.hiddenghosts.ui.theme.*

@Composable
fun PlayScreen(
    level: Int? = null,
    onFinish: (Int) -> Unit = {},
    viewModel: PlayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState()
    val itemsList = viewModel.gridItems.observeAsState()

    viewModel.level = level ?: 0

    PlayScreenContent(
        score = (uiState as? PlayUIState.Playing)?.score ?: 0,
        items = itemsList.value ?: emptyList(),
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
                        when (getGridState(item = items[index], isPreview = isPreview)) {
                            GridSate.WRONG -> WrongCard()
                            GridSate.DEFAULT -> DefaultCard(onItemClick = { onItemClick(index) })
                            GridSate.SUCCESS -> CorrectCard()
                        }
                    }
                })
        }
    }
}

@Composable
fun getGridState(
    item: GridItem,
    isPreview: Boolean
) = if (isPreview) {
    if (item.isGhost) GridSate.SUCCESS else GridSate.DEFAULT
} else {
    if (item.isClosed) {
        GridSate.DEFAULT
    } else {
        if (item.isGhost) GridSate.SUCCESS else GridSate.WRONG
    }
}

enum class GridSate { DEFAULT, SUCCESS, WRONG }

@Composable
fun CorrectCard(
) {
    Card(
        modifier = Modifier
            .padding(2.dp),
        shape = RoundedCornerShape(2.dp)
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

@Composable
fun WrongCard() {
    Card(
        modifier = Modifier
            .padding(2.dp),
        shape = RoundedCornerShape(2.dp)
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

@Composable
fun DefaultCard(
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(2.dp),
        shape = RoundedCornerShape(2.dp),
    ) {
        Box(
            modifier = Modifier
                .background(color = GhostColor.DefaultCellColor)
                .fillMaxSize()
                .clickable { onItemClick() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_general),
                contentDescription = null
            )
        }
    }
}
