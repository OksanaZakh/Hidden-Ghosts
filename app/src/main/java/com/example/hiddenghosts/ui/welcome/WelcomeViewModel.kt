package com.example.hiddenghosts.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiddenghosts.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WelcomeUIState>(WelcomeUIState.Loading)
    val uiState: StateFlow<WelcomeUIState> = _uiState
    init {
        viewModelScope.launch(Dispatchers.Default) {
            val level = repository.getLevel()
            _uiState.emit(WelcomeUIState.Start(level))
        }
    }
}