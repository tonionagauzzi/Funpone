package com.vitantonio.nagauzzi.funpone.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vitantonio.nagauzzi.funpone.R
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import com.vitantonio.nagauzzi.funpone.data.repository.SettingRepository
import com.vitantonio.nagauzzi.funpone.data.repository.ShortcutRepository
import com.vitantonio.nagauzzi.funpone.ui.theme.FunponeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.graphics.drawable.Icon as AndroidIcon

@Composable
fun SettingView(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    settingRepository: SettingRepository,
    shortcutRepository: ShortcutRepository
) {
    val link by settingRepository.link.collectAsState(initial = Link())

    Column {
        val context = LocalContext.current
        LinkLabel(
            label = link.label,
            onLabelChange = { newUrl ->
                coroutineScope.launch {
                    settingRepository.set(link = link.copy(label = newUrl))
                }
            }
        )
        LinkUrl(
            url = link.url,
            onUrlChange = { newUrl ->
                coroutineScope.launch {
                    settingRepository.set(link = link.copy(url = newUrl))
                }
            }
        )
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                shortcutRepository.createShortcut(
                    link = link,
                    icon = AndroidIcon.createWithResource(context, R.mipmap.ic_launcher)
                )
                coroutineScope.launch {
                    settingRepository.save(link = link)
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingViewPreview() {
    FunponeTheme {
        SettingView(
            settingRepository = SettingRepositoryStubForPreview(),
            shortcutRepository = ShortcutRepositoryStubForPreview()
        )
    }
}

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
    override fun createShortcut(link: Link, icon: AndroidIcon) = Unit
}
