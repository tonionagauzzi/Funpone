package com.vitantonio.nagauzzi.funpone.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vitantonio.nagauzzi.funpone.R

@Composable
fun LinkUrl(url: String, onUrlChange: (String) -> Unit) {
    TextField(
        modifier = Modifier.fillMaxWidth().testTag("LinkUrl"),
        placeholder = {
            Text(text = stringResource(id = R.string.url))
        },
        value = url,
        onValueChange = onUrlChange
    )
}

@Preview
@Composable
fun LinkUrlPreview() {
    LinkUrl(url = "https://example.com/", onUrlChange = {})
}

@Preview
@Composable
fun LinkUrlPlaceholderPreview() {
    LinkUrl(url = "", onUrlChange = {})
}
