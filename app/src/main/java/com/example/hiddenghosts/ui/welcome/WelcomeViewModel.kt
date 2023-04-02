package com.example.hiddenghosts.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiddenghosts.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableLiveData<WelcomeUIState>(WelcomeUIState.Loading)
    val uiState: LiveData<WelcomeUIState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val level = repository.getLevel()
            _uiState.postValue(WelcomeUIState.Start(level))
        }
    }
}