package com.vitantonio.nagauzzi.funpone.ui

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vitantonio.nagauzzi.funpone.ui.theme.FunponeTheme

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
