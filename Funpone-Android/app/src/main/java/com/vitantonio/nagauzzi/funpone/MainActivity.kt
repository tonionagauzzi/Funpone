@file:OptIn(ExperimentalMaterial3Api::class)

package com.vitantonio.nagauzzi.funpone

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vitantonio.nagauzzi.funpone.data.SettingsRepository
import com.vitantonio.nagauzzi.funpone.ui.theme.FunponeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

@Composable
fun UrlList(

) {

}

@Composable
fun UrlItem(
    selected: Boolean,
    url: String,
    onUrlChange: (String) -> Unit,
    onClickDropdown: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                contentColor = if (selected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onBackground,
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            onClick = onClickDropdown,
            shape = RectangleShape
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "移動",
            )
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = url,
            onValueChange = onUrlChange
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UrlItemPreview() {
    FunponeTheme {
        UrlItem(
            selected = true,
            url = "https://example.com/",
            onUrlChange = {},
            onClickDropdown = {}
        )
    }
}

@Composable
fun UrlItemDropdownMenu(
    expanded: Boolean,
    onClickMoveAbove: () -> Unit,
    onClickMoveBelow: () -> Unit,
    onClickSelect: () -> Unit,
    onClickDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
    ) {
        DropdownMenuItem(
            onClick = {
                onClickMoveAbove()
                onDismiss()
            },
            text = {
                Text(text = "1つ上へ移動")
            }
        )
        DropdownMenuItem(
            onClick = {
                onClickSelect()
                onDismiss()
            },
            text = {
                Text(text = "選択")
            }
        )
        DropdownMenuItem(
            onClick = {
                onClickMoveBelow()
                onDismiss()
            },
            text = {
                Text(text = "1つ下へ移動")
            }
        )
        DropdownMenuItem(
            onClick = {
                onClickDelete()
                onDismiss()
            },
            text = {
                Text(text = "削除")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UrlItemDropdownMenuPreview() {
    FunponeTheme {
        UrlItemDropdownMenu(
            expanded = true,
            onClickMoveAbove = {},
            onClickMoveBelow = {},
            onClickSelect = {},
            onClickDelete = {},
            onDismiss = {},
        )
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
