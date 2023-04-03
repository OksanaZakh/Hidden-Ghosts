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
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState: MutableLiveData<PlayUIState> = MutableLiveData(PlayUIState.Loading)
    val uiState: LiveData<PlayUIState> = _uiState

    var level = 0
    private var score = 0
    private var attempt = 0
    private var levelInfo: LevelInfo? = null
    private var gridItemsList: MutableList<GridItem> = mutableListOf()

    init {
        startGame()
    }

    fun startGame() {
        viewModelScope.launch {
            _uiState.value = PlayUIState.Loading
            val list = generateGhostsList(level)
            gridItemsList = list
            delay(1000)
            _uiState.value = PlayUIState.Preview(list)
            delay(1000)
            score = 0
            attempt = 0
            _uiState.value = PlayUIState.Playing(items = list, score = score)
        }
    }

    fun onItemClick(index: Int) {
        val item = gridItemsList[index]
        gridItemsList[index] = GridItem(false, item.isGhost)
        val ghostsNum = levelInfo?.ghosts ?: 0
        if (item.isGhost) {
            score += 5
        }
        attempt += 1
        if (score < ghostsNum * 5 && attempt < ghostsNum) {
            _uiState.value = PlayUIState.Playing(items = gridItemsList, score = score)
        } else {
            viewModelScope.launch {
                _uiState.value = PlayUIState.Playing(items = gridItemsList, score = score)
                delay(1000)
                _uiState.value = PlayUIState.Finish(score)
            }
        }
    }

    private fun generateGhostsList(level: Int): MutableList<GridItem> {
        val gridItems = mutableListOf<GridItem>()
        levelInfo = repository.getDataForLevel(level)
        levelInfo?.let { it ->
            val ghostsCount = it.ghosts
            val randomIntList = generateNumberList(it.grid.x * it.grid.x)
            for (item in randomIntList) {
                gridItems.add(GridItem(isGhost = item < ghostsCount))
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