package com.example.hiddenghosts.ui.play

sealed class PlayUIState {
    object Loading : PlayUIState()
    object Preview : PlayUIState()
    data class Playing(val score: Int) : PlayUIState()
    data class Finish(val score: Int) : PlayUIState()
}