package com.vitantonio.nagauzzi.funpone.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vitantonio.nagauzzi.funpone.debug.IconRepositoryStubForPreview
import com.vitantonio.nagauzzi.funpone.debug.SettingRepositoryStubForPreview
import com.vitantonio.nagauzzi.funpone.debug.ShortcutRepositoryStubForPreview

class FunponeViewTest {
    @Preview(showBackground = true)
    @Composable
    fun PreviewFunponeView() {
        FunponeView(
            iconRepository = IconRepositoryStubForPreview(),
            settingRepository = SettingRepositoryStubForPreview(),
            shortcutRepository = ShortcutRepositoryStubForPreview()
        )
    }
}
