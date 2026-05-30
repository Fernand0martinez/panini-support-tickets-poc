package com.panini.supporttickets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.panini.supporttickets.ui.navigation.PaniniSupportTicketsNavHost
import com.panini.supporttickets.ui.theme.PaniniSupportTicketsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaniniSupportTicketsTheme {
                PaniniSupportTicketsNavHost()
            }
        }
    }
}
