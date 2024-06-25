package com.vitantonio.nagauzzi.funpone.ui

import android.graphics.drawable.Icon as AndroidIcon
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@Composable
fun SettingView(
    settingRepository: SettingRepository,
    shortcutRepository: ShortcutRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val link by settingRepository.link.collectAsState(initial = Link())

    Column {
        val context = LocalContext.current
        LinkLabel(
            label = link.label,
            onLabelChange = { newUrl ->
                coroutineScope.launch {
                    settingRepository.save(link = link.copy(label = newUrl))
                }
            }
        )
        LinkUrl(
            url = link.url,
            onUrlChange = { newUrl ->
                coroutineScope.launch {
                    settingRepository.save(link = link.copy(url = newUrl))
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
    override val link: Flow<Link> = listOf(
        Link(
            label = "Example",
            url = "https://example.com/"
        )
    ).asFlow()
) : SettingRepository {
    override suspend fun save(link: Link) = Unit
}

private class ShortcutRepositoryStubForPreview : ShortcutRepository {
    override fun createShortcut(link: Link, icon: AndroidIcon) = Unit
}
