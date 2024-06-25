package com.vitantonio.nagauzzi.funpone.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vitantonio.nagauzzi.funpone.data.repository.SettingRepository
import com.vitantonio.nagauzzi.funpone.data.repository.ShortcutRepository
import com.vitantonio.nagauzzi.funpone.ui.theme.FunponeTheme

@Composable
fun FunponeView(settingRepository: SettingRepository, shortcutRepository: ShortcutRepository) {
    FunponeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            SettingView(
                settingRepository = settingRepository,
                shortcutRepository = shortcutRepository
            )
        }
    }
}
