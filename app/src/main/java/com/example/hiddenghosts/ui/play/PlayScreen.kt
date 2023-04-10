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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.hiddenghosts.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun PlayScreen(
    level: Int? = null,
    onFinish: (Int, Int) -> Unit = { _, _ -> },
    viewModel: PlayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(level) {
        viewModel.startGame(level ?: 0)
    }

    (uiState as? PlayUIState.Finish)?.let {
        LaunchedEffect(Unit) {
            delay(2000)
            onFinish.invoke(it.score, if (it.passed) (level ?: 0) + 1 else level ?: 0)
        }
    }

    PlayScreenContent(
        score = (uiState as? PlayUIState.Playing)?.score
            ?: (uiState as? PlayUIState.Finish)?.score ?: 0,
        items = (uiState as? PlayUIState.Playing)?.items
            ?: (uiState as? PlayUIState.Finish)?.items
            ?: (uiState as? PlayUIState.Preview)?.items ?: emptyList(),
        onRestartClick = { viewModel.startGame(level ?: 0) },
        passLevel = (uiState as? PlayUIState.Finish)?.passed,
        onItemClick = viewModel::onItemClick
    )
}

@Composable
fun PlayScreenContent(
    score: Int = 0,
    onRestartClick: () -> Unit = {},
    items: List<GridState> = emptyList(),
    passLevel: Boolean? = null,
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

            Box(contentAlignment = Alignment.Center) {
                LazyVerticalGrid(
                    contentPadding = PaddingValues(
                        start = 30.dp,
                        top = 38.dp,
                        end = 30.dp,
                        bottom = 16.dp
                    ),
                    columns = GridCells.Fixed(columnsNumber), content = {
                        items(items.size) { index ->
                            when (items[index]) {
                                GridState.DEFAULT -> DefaultCard(onItemClick = { onItemClick(index) })
                                GridState.WRONG -> WrongCard()
                                GridState.PREVIEW -> PreviewCard()
                                GridState.SUCCESS -> SuccessCard()
                            }
                        }
                    })

                passLevel?.let {
                    Image(
                        painter = painterResource(id = if (it) R.drawable.ic_win else R.drawable.ic_fail),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun PreviewCard(
) {
    Card(
        modifier = Modifier
            .padding(2.dp),
        shape = RoundedCornerShape(2.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = GhostColor.PreviewColor)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(getGhostId(Random.nextInt(5))),
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

@Composable
fun SuccessCard(
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
                painter = painterResource(getGhostId(Random.nextInt(5))),
                contentDescription = null
            )
        }
    }
}

private fun getGhostId(randomNam: Int): Int = when (randomNam) {
    0 -> R.drawable.ic_ghost_1
    1 -> R.drawable.ic_ghost_2
    2 -> R.drawable.ic_ghost_3
    3 -> R.drawable.ic_ghost_4
    else -> R.drawable.ic_ghost_5
}
