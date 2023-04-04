package com.example.hiddenghosts.ui.play

sealed class PlayUIState {
    object Loading : PlayUIState()
    data class Preview(val items: List<GridState>) : PlayUIState()
    data class Playing(val items: List<GridState>, val score: Int) : PlayUIState()
    data class Finish(val items: List<GridState>, val score: Int, val passed: Boolean) : PlayUIState()
}