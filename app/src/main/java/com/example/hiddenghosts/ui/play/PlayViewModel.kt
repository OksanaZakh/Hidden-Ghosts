package com.example.hiddenghosts.ui.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiddenghosts.data.LevelInfo
import com.example.hiddenghosts.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private var score = 0
    private var levelInfo: LevelInfo? = null
    private var listenClicks = true
    private var gridItemsList: MutableList<Boolean> = mutableListOf()
    private var statesList: MutableList<GridState> = mutableListOf()
    private var currentLevel: Int = 0
    private val defaultDispatcher = Dispatchers.Default

    fun startGame(level: Int) {
        viewModelScope.launch {
            _uiState.value = PlayUIState.Loading
            val list = generateGhostsList(level)
            currentLevel = level
            gridItemsList = list
            statesList = list.map { GridState.DEFAULT }.toMutableList()
            _uiState.value = PlayUIState.Preview(
                items = list.map { if (it) GridState.PREVIEW else GridState.DEFAULT }
            )
            delay(PREVIEW_TIME)
            score = 0
            _uiState.value = PlayUIState.Playing(items = statesList.toList(), score = score)
            listenClicks = true
        }
    }

    fun onItemClick(index: Int) {
        if (!listenClicks) return
        listenClicks = false
        updateItem(index)
        val opened = getOpenedItems()
        if (opened < (levelInfo?.ghosts ?: 0)) {
            _uiState.update { PlayUIState.Playing(items = statesList.toList(), score = score) }
            listenClicks = true
        } else {
            setUpFinishState()
        }
    }

    private fun saveLevelToSharedPref() {
        viewModelScope.launch(defaultDispatcher) {
            if (currentLevel < 5) {
                repository.saveLevel(currentLevel + 1)
            } else {
                currentLevel
            }
        }
    }

    private fun setUpFinishState() {
        gridItemsList.forEachIndexed { ind, item ->
            if (item) {
                val old = statesList[ind]
                if (old == GridState.DEFAULT) {
                    statesList[ind] = GridState.PREVIEW
                }
            }
        }
        val passedLevel = !statesList.contains(GridState.PREVIEW)
        _uiState.update {
            PlayUIState.Finish(
                items = statesList,
                score = score,
                passed = passedLevel
            )
        }
        if (passedLevel) saveLevelToSharedPref()
    }

    private fun getOpenedItems(): Int {
        var opened = 0
        statesList.forEach { if (it != GridState.DEFAULT) opened += 1 }
        return opened
    }

    private fun updateItem(index: Int) {
        if (gridItemsList[index]) {
            score += 5
            statesList[index] = GridState.SUCCESS
        } else {
            statesList[index] = GridState.WRONG
        }
    }

    private fun generateGhostsList(level: Int): MutableList<Boolean> {
        val gridItems = mutableListOf<Boolean>()
        levelInfo = repository.getDataForLevel(level)
        levelInfo?.let { it ->
            val ghostsCount = it.ghosts
            val randomIntList = generateNumberList(it.grid.x * it.grid.x)
            for (item in randomIntList) {
                gridItems.add(item < ghostsCount)
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

    companion object {
        private const val PREVIEW_TIME = 1000L
    }
}

enum class GridState { DEFAULT, SUCCESS, WRONG, PREVIEW }