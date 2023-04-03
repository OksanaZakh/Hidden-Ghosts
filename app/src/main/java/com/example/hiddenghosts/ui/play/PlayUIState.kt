package com.example.hiddenghosts.ui.play

import com.example.hiddenghosts.data.GridItem

sealed class PlayUIState {
    object Loading : PlayUIState()
    data class Preview(val items: List<GridItem>) : PlayUIState()
    data class Playing(val items: List<GridItem>, val score: Int) : PlayUIState()
    data class Finish(val score: Int) : PlayUIState()
}