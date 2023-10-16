package com.vitantonio.nagauzzi.funpone.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vitantonio.nagauzzi.funpone.R
import com.vitantonio.nagauzzi.funpone.data.SettingsRepositoryStub
import com.vitantonio.nagauzzi.funpone.data.SettingsRepository
import com.vitantonio.nagauzzi.funpone.isValid
import com.vitantonio.nagauzzi.funpone.ui.theme.FunponeTheme
import kotlinx.coroutines.launch

@Composable
fun SettingsView(
    repository: SettingsRepository,
    selectedUrl: String
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val urls by repository.urls.collectAsState(initial = emptyList())
    var clickedUrl by remember { mutableStateOf("") }
    var menuExpanded by remember { mutableStateOf(false) }
    Column {
        urls.forEachIndexed { index, url ->
            val selected = url == selectedUrl
            UrlItem(
                selected = selected,
                url = url,
                onUrlChange = { newUrl ->
                    coroutineScope.launch {
                        repository.save(
                            urls = urls.mapIndexed { searchIndex, url ->
                                if (index == searchIndex) {
                                    newUrl
                                } else {
                                    url
                                }
                            },
                            selectedUrl = if (selected) newUrl else selectedUrl
                        )
                    }
                },
                onClickDropdown = {
                    clickedUrl = url
                    menuExpanded = true
                }
            )
        }
        Row(
            modifier = Modifier.align(Alignment.End),
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        repository.save(
                            urls = urls.plus(""),
                        )
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
    UrlItemDropdownMenu(
        expanded = menuExpanded,
        onClickMoveAbove = {
            urls.indexOf(clickedUrl).let { selectingIndex ->
                if (selectingIndex != 0) {
                    var mutableUrls = urls.toMutableList()
                    val tmp = urls[selectingIndex]
                    mutableUrls[selectingIndex] = urls[selectingIndex - 1]
                    mutableUrls[selectingIndex - 1] = tmp
                    coroutineScope.launch {
                        repository.save(urls = mutableUrls.toList())
                    }
                }
            }
        },
        onClickMoveBelow = {
            urls.indexOf(clickedUrl).let { selectingIndex ->
                if (selectingIndex != urls.size - 1) {
                    var mutableUrls = urls.toMutableList()
                    val tmp = urls[selectingIndex]
                    mutableUrls[selectingIndex] = urls[selectingIndex + 1]
                    mutableUrls[selectingIndex + 1] = tmp
                    coroutineScope.launch {
                        repository.save(urls = mutableUrls.toList())
                    }
                }
            }
        },
        onClickSelect = {
            if (isValid(url = clickedUrl)) {
                coroutineScope.launch {
                    repository.save(selectedUrl = clickedUrl)
                }
            } else {
                Toast.makeText(context, R.string.invalid_url, Toast.LENGTH_SHORT).show()
            }
        },
        onClickDelete = {
            coroutineScope.launch {
                repository.save(urls = urls.minus(clickedUrl))
            }
        },
        onDismiss = { menuExpanded = false }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsViewPreview() {
    FunponeTheme {
        SettingsView(
            repository = SettingsRepositoryStub(),
            selectedUrl = "https://example.com/"
        )
    }
}
