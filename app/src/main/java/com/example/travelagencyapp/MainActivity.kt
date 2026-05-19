package com.example.travelagencyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.travelagencyapp.presentation.theme.TravelAgencyAppTheme
import com.example.travelagencyapp.presentation.ui.TravelAgencyRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelAgencyAppTheme {
                TravelAgencyRoot()
            }
        }
    }
}
