package com.vitantonio.nagauzzi.funpone.debug

import android.graphics.drawable.Icon
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import com.vitantonio.nagauzzi.funpone.data.repository.IconRepository
import com.vitantonio.nagauzzi.funpone.data.repository.SettingRepository
import com.vitantonio.nagauzzi.funpone.data.repository.ShortcutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class IconRepositoryStubForPreview : IconRepository {
    override suspend fun get(iconUri: String) = createPNGIcon()
}

internal class SettingRepositoryStubForPreview(
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

internal class ShortcutRepositoryStubForPreview : ShortcutRepository {
    override fun createShortcut(link: Link, icon: Icon) = Unit
}
