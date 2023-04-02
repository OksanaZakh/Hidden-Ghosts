package com.example.hiddenghosts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.hiddenghosts.ui.navigation.GhostsNavHost
import com.example.hiddenghosts.ui.theme.GhostColor
import com.example.hiddenghosts.ui.theme.GhostsTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GhostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            GhostsTheme {
                GhostsNavHost(
                    navController = navController,
                )
            }
            rememberSystemUiController().apply {
                setSystemBarsColor(GhostColor.PrimaryColor)
                setSystemBarsColor(GhostColor.PrimaryColor)
            }
        }
    }
}