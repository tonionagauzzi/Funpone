package com.vitantonio.nagauzzi.funpone.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.funpone.ui.theme.FunponeTheme

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
