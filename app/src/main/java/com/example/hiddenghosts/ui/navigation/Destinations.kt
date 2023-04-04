package com.example.hiddenghosts.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface GhostsDestination {
    val route: String
}

object Welcome : GhostsDestination {
    override val route = "welcome"
}

object Play : GhostsDestination {
    override val route = "play"
    const val levelTypeArg = "level"
    val routeWithArgs = "$route/{$levelTypeArg}"
    val arguments = listOf(
        navArgument(levelTypeArg) { type = NavType.IntType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "hiddenghosts://$route/{$levelTypeArg}" }
    )
}

object Results : GhostsDestination {
    override val route = "results"
    const val scoreTypeArg = "score"
    const val levelTypeArg = "level"
    val routeWithArgs = "${route}/{$scoreTypeArg}/{$levelTypeArg}"
    val arguments = listOf(
        navArgument(scoreTypeArg) { type = NavType.IntType },
        navArgument(levelTypeArg) { type = NavType.IntType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "hiddenghosts://$route/{$scoreTypeArg}/{$levelTypeArg}" }
    )
}

val ghostsScreens = listOf(Welcome, Play, Results)
