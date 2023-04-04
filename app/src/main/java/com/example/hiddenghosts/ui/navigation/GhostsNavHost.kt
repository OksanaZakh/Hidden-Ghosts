package com.example.hiddenghosts.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hiddenghosts.ui.play.PlayScreen
import com.example.hiddenghosts.ui.ResultsScreen
import com.example.hiddenghosts.ui.WelcomeScreen

@Composable
fun GhostsNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Welcome.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Welcome.route) {
            WelcomeScreen(
                onStartClick = { navController.navigateToPlayScreen(it) }
            )
        }
        composable(
            route = Play.routeWithArgs,
            arguments = Play.arguments,
            deepLinks = Play.deepLinks
        ) { navBackStackEntry ->
            val levelType =
                navBackStackEntry.arguments?.getInt(Play.levelTypeArg)
            PlayScreen(
                level = levelType,
                onFinish = { score, level -> navController.navigateToResultsScreen(score, level) }
            )
        }
        composable(
            route = Results.routeWithArgs,
            arguments = Results.arguments,
            deepLinks = Results.deepLinks
        ) { navBackStackEntry ->
            val scoreType =
                navBackStackEntry.arguments?.getInt(Results.scoreTypeArg)
            val levelType =
                navBackStackEntry.arguments?.getInt(Results.levelTypeArg)
            ResultsScreen(
                score = scoreType,
                level = levelType,
                onNextLevel = { navController.navigateToPlayScreen(it) })
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }


private fun NavHostController.navigateToPlayScreen(level: Int) {
    this.navigateSingleTopTo("${Play.route}/$level")
}

private fun NavHostController.navigateToResultsScreen(score: Int, level: Int) {
    this.navigateSingleTopTo("${Results.route}/$score/$level")
}