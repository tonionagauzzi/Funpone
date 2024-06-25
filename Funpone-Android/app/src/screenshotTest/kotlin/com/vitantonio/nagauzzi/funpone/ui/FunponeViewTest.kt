package com.vitantonio.nagauzzi.funpone.ui

import android.graphics.drawable.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import com.vitantonio.nagauzzi.funpone.data.repository.SettingRepository
import com.vitantonio.nagauzzi.funpone.data.repository.ShortcutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FunponeViewTest {
    @Preview(showBackground = true)
    @Composable
    fun PreviewFunponeView() {
        FunponeView(
            settingRepository = SettingRepositoryStubForPreview(),
            shortcutRepository = ShortcutRepositoryStubForPreview()
        )
    }
}

// TODO: Share test doubles with @Preview
private class SettingRepositoryStubForPreview(
    override val link: StateFlow<Link> = MutableStateFlow(
        Link(
            label = "Example",
            url = "https://example.com/"
        )
    )
) : SettingRepository {
    override suspend fun set(link: Link) = Unit
    override suspend fun save(link: Link) = Unit
}

private class ShortcutRepositoryStubForPreview : ShortcutRepository {
    override fun createShortcut(link: Link, icon: Icon) = Unit
}
