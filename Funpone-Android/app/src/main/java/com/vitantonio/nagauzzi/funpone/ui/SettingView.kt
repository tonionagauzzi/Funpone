package com.vitantonio.nagauzzi.funpone.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vitantonio.nagauzzi.funpone.R
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import com.vitantonio.nagauzzi.funpone.data.repository.IconRepository
import com.vitantonio.nagauzzi.funpone.data.repository.SettingRepository
import com.vitantonio.nagauzzi.funpone.data.repository.ShortcutRepository
import com.vitantonio.nagauzzi.funpone.debug.IconRepositoryStubForPreview
import com.vitantonio.nagauzzi.funpone.debug.SettingRepositoryStubForPreview
import com.vitantonio.nagauzzi.funpone.debug.ShortcutRepositoryStubForPreview
import com.vitantonio.nagauzzi.funpone.ui.theme.FunponeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import android.graphics.drawable.Icon as AndroidIcon

@Composable
fun SettingView(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    iconRepository: IconRepository,
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
        LinkIconUri(
            iconUri = link.iconUri,
            onIconUriChange = { newIconUri ->
                coroutineScope.launch {
                    settingRepository.set(link = link.copy(iconUri = newIconUri))
                }
            }
        )
        Row {
            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.PickVisualMedia()
            ) { iconUri ->
                coroutineScope.launch {
                    settingRepository.save(link = link.copy(iconUri = iconUri.toString()))
                }
            }
            Button(
                onClick = {
                    singlePhotoPickerLauncher.launch(PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = stringResource(id = R.string.load_icon)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    coroutineScope.launch {
                        val icon = try {
                            iconRepository.get(link.iconUri)
                        } catch (e: Exception) {
                            val ee = e
                            AndroidIcon.createWithResource(context, R.mipmap.ic_launcher)
                        }
                        shortcutRepository.createShortcut(
                            link = link,
                            icon = icon
                        )
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
}

@Preview(showBackground = true)
@Composable
fun SettingViewPreview() {
    FunponeTheme {
        SettingView(
            iconRepository = IconRepositoryStubForPreview(),
            settingRepository = SettingRepositoryStubForPreview(),
            shortcutRepository = ShortcutRepositoryStubForPreview()
        )
    }
}
