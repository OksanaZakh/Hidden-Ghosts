package com.example.hiddenghosts.ui.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiddenghosts.data.GridItem
import com.example.hiddenghosts.data.LevelInfo
import com.example.hiddenghosts.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PlayUIState> = MutableStateFlow(PlayUIState.Loading)
    val uiState: StateFlow<PlayUIState> = _uiState

    private val _gridItems: MutableStateFlow<List<GridItem>> = MutableStateFlow(emptyList())
    val gridItems: StateFlow<List<GridItem>> = _gridItems

    private var score = 0
    private var attempt = 0
    private var levelInfo: LevelInfo? = null
    private var listenClicks = true
    private var gridItemsList: MutableList<GridItem> = mutableListOf()

    fun startGame(level: Int) {
        viewModelScope.launch {
            _uiState.value = PlayUIState.Loading
            val list = generateGhostsList(level)
            gridItemsList = list
            _gridItems.value = list
            delay(1000)
            _uiState.value = PlayUIState.Preview
            delay(1000)
            score = 0
            attempt = 0
            _uiState.value = PlayUIState.Playing(score = score)
            listenClicks = true
        }
    }

    fun onItemClick(index: Int) {
        if (!listenClicks) return
        viewModelScope.launch {
            listenClicks = false
            val item = gridItemsList[index].copy(isClosed = false)
            gridItemsList[index] = item
            _gridItems.update { gridItemsList }
            val ghostsNum = levelInfo?.ghosts ?: 0
            if (item.isGhost) {
                score += 5
            }
            attempt += 1
            if (score < ghostsNum * 5 && attempt < ghostsNum) {
                _uiState.value = PlayUIState.Playing(score = score)
                listenClicks = true
            } else
                _uiState.value = PlayUIState.Finish(score)
        }
    }

    private fun generateGhostsList(level: Int): MutableList<GridItem> {
        val gridItems = mutableListOf<GridItem>()
        levelInfo = repository.getDataForLevel(level)
        levelInfo?.let { it ->
            val ghostsCount = it.ghosts
            val randomIntList = generateNumberList(it.grid.x * it.grid.x)
            for (item in randomIntList) {
                gridItems.add(GridItem(isGhost = item < ghostsCount, isClosed = true))
            }
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