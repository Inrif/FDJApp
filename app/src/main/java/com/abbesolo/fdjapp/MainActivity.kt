package com.abbesolo.fdjapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.abbesolo.fdjapp.presentation.screens.LeagueSearchScreen
import com.abbesolo.fdjapp.ui.theme.FDJAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FDJAppTheme {
                LeagueSearchScreen()
            }
        }
    }
}
