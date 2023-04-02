package com.example.hiddenghosts.ui.welcome

sealed class WelcomeUIState {
    object Loading : WelcomeUIState()
    data class Start(val level: Int) : WelcomeUIState()
}