package com.example.sadzmonovamegalomanskaapkaa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Modifier
import com.example.sadzmonovamegalomanskaapkaa.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    DialogModuleChoose()
                }
                //LoginSCreen()
            }
        }
    }
}