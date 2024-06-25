package com.vitantonio.nagauzzi.funpone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.vitantonio.nagauzzi.funpone.data.repository.SettingRepository
import com.vitantonio.nagauzzi.funpone.data.repository.ShortcutRepository
import com.vitantonio.nagauzzi.funpone.data.repository.repository
import com.vitantonio.nagauzzi.funpone.ui.SettingView
import com.vitantonio.nagauzzi.funpone.ui.theme.FunponeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FunponeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val settingRepository: SettingRepository by repository()
                    val shortcutRepository: ShortcutRepository by repository()
                    SettingView(
                        settingRepository = settingRepository,
                        shortcutRepository = shortcutRepository
                    )
                }
            }
        }
    }
}
