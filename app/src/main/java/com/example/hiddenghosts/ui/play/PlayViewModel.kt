package com.example.hiddenghosts.ui.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiddenghosts.data.GridItem
import com.example.hiddenghosts.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState: MutableLiveData<PlayUIState> = MutableLiveData(PlayUIState.Loading)
    val uiState: LiveData<PlayUIState> = _uiState

    private val _gridItems: MutableLiveData<List<GridItem>> = MutableLiveData(emptyList())
    val gridItems: LiveData<List<GridItem>> = _gridItems

    var level = 0

    init {
        startGame()
    }

    fun startGame() {
        viewModelScope.launch {
            _uiState.value = PlayUIState.Loading
            val list = generateGhostsList(level)
            _gridItems.value = list
            delay(1000)
            _uiState.value = PlayUIState.Preview
            delay(1000)
            _uiState.value = PlayUIState.Playing(0)
        }
    }

    fun onItemClick(index: Int) {
        val list = _gridItems.value
        val newList = list?.apply { get(index).isClosed = false }
        _gridItems.value = newList?: emptyList()
    }

    private fun generateGhostsList(level: Int): List<GridItem> {
        val gridItems = mutableListOf<GridItem>()
        val levelData = repository.getDataForLevel(level)
        val ghostsCount = levelData.ghosts
        val randomIntList = generateNumberList(levelData.grid.x * levelData.grid.x)
        for (item in randomIntList) {
            gridItems.add(GridItem(isGhost = item < ghostsCount))
        }
        return gridItems
    }

    private fun generateNumberList(size: Int): List<Int> {
        val list = mutableListOf<Int>()
        while (list.size < size) {
            val item = Random.nextInt(size)
            if (!list.contains(item)) list.add(item)
        }
        return list
    }
}