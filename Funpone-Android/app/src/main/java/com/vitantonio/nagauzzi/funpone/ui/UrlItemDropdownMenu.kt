package com.vitantonio.nagauzzi.funpone.ui

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vitantonio.nagauzzi.funpone.R
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
                Text(text = stringResource(id = R.string.move_above))
            }
        )
        DropdownMenuItem(
            onClick = {
                onClickSelect()
                onDismiss()
            },
            text = {
                Text(text = stringResource(id = R.string.select))
            }
        )
        DropdownMenuItem(
            onClick = {
                onClickMoveBelow()
                onDismiss()
            },
            text = {
                Text(text = stringResource(id = R.string.move_below))
            }
        )
        DropdownMenuItem(
            onClick = {
                onClickDelete()
                onDismiss()
            },
            text = {
                Text(text = stringResource(id = R.string.delete))
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
