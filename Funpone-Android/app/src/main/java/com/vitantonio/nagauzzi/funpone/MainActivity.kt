package com.vitantonio.nagauzzi.funpone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vitantonio.nagauzzi.funpone.data.repository.IconRepository
import com.vitantonio.nagauzzi.funpone.data.repository.SettingRepository
import com.vitantonio.nagauzzi.funpone.data.repository.ShortcutRepository
import com.vitantonio.nagauzzi.funpone.data.repository.repository
import com.vitantonio.nagauzzi.funpone.ui.FunponeView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val iconRepository: IconRepository by repository()
            val settingRepository: SettingRepository by repository()
            val shortcutRepository: ShortcutRepository by repository()
            FunponeView(
                iconRepository = iconRepository,
                settingRepository = settingRepository,
                shortcutRepository = shortcutRepository
            )
        }
    }
}
