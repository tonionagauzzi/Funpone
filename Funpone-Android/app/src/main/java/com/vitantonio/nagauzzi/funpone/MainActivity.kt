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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.funpone.ui.theme.FunponeTheme

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
                    val urls = remember {
                        mutableStateListOf(
                            "https://www.yahoo.co.jp/",
                            "https://www.google.com/"
                        )
                    }
                    var selectedUrl by remember { mutableStateOf(urls[1]) }
                    var clickedUrl by remember { mutableStateOf("") }
                    var menuExpanded by remember { mutableStateOf(false) }
                    if (!intent.getBooleanExtra("shortcut", false)) {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(selectedUrl)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Column {
                            urls.forEachIndexed { index, url ->
                                UrlItem(
                                    selected = url == selectedUrl,
                                    url = url,
                                    onUrlChange = {
                                        urls[index] = it
                                    },
                                    onClickDropdown = {
                                        clickedUrl = url
                                        menuExpanded = true
                                    }
                                )
                            }
                            Button(
                                modifier = Modifier.align(Alignment.End),
                                onClick = {
                                    urls.add("")
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "追加")
                            }
                        }
                        UrlItemDropdownMenu(
                            expanded = menuExpanded,
                            onClickMoveAbove = {
                                urls.indexOf(clickedUrl).let { selectingIndex ->
                                    if (selectingIndex != 0) {
                                        val tmp = urls[selectingIndex]
                                        urls[selectingIndex] = urls[selectingIndex - 1]
                                        urls[selectingIndex - 1] = tmp
                                    }
                                }
                            },
                            onClickMoveBelow = {
                                urls.indexOf(clickedUrl).let { selectingIndex ->
                                    if (selectingIndex != urls.size - 1) {
                                        val tmp = urls[selectingIndex]
                                        urls[selectingIndex] = urls[selectingIndex + 1]
                                        urls[selectingIndex + 1] = tmp
                                    }
                                }
                            },
                            onClickSelect = { selectedUrl = clickedUrl },
                            onClickDelete = { urls.remove(clickedUrl) },
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
                containerColor = if (selected) Color.Green else Color.Transparent,
                contentColor = Color.Black
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
