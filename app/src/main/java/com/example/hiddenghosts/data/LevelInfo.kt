package com.example.hiddenghosts.data

data class LevelInfo(val level: Int, val grid: Grid, val ghosts: Int)

data class Grid(val x: Int, val y: Int)