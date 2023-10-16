@file:OptIn(ExperimentalMaterial3Api::class)

package com.vitantonio.nagauzzi.funpone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.vitantonio.nagauzzi.funpone.data.SettingsRepository
import com.vitantonio.nagauzzi.funpone.ui.UrlItem
import com.vitantonio.nagauzzi.funpone.ui.UrlItemDropdownMenu
import com.vitantonio.nagauzzi.funpone.ui.theme.FunponeTheme
import kotlinx.coroutines.launch

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
                    val coroutineScope = rememberCoroutineScope()
                    val settingsRepository = SettingsRepository(LocalContext.current)
                    val urls by settingsRepository.urls.collectAsState(initial = emptyList())
                    val selectedUrl by settingsRepository.selectedUrl.collectAsState(initial = "")
                    var clickedUrl by remember { mutableStateOf("") }
                    var menuExpanded by remember { mutableStateOf(false) }
                    if (!intent.getBooleanExtra("shortcut", false)
                        && urls.isNotEmpty()
                        && selectedUrl.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(selectedUrl)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Column {
                            urls.forEachIndexed { index, url ->
                                val selected = url == selectedUrl
                                UrlItem(
                                    selected = selected,
                                    url = url,
                                    onUrlChange = { newUrl ->
                                        coroutineScope.launch {
                                            settingsRepository.save(
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
                                            settingsRepository.save(
                                                urls = urls.plus(""),
                                            )
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "追加"
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
                                            settingsRepository.save(urls = mutableUrls.toList())
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
                                            settingsRepository.save(urls = mutableUrls.toList())
                                        }
                                    }
                                }
                            },
                            onClickSelect = {
                                coroutineScope.launch {
                                    settingsRepository.save(selectedUrl = clickedUrl)
                                }
                            },
                            onClickDelete = {
                                coroutineScope.launch {
                                    settingsRepository.save(urls = urls.minus(clickedUrl))
                                }
                            },
                            onDismiss = { menuExpanded = false }
                        )
                    }
                }
            }
        }
    }
}
