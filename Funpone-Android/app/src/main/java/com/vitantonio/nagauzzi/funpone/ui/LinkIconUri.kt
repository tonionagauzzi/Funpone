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
fun LinkIconUri(iconUri: String, onIconUriChange: (String) -> Unit) {
    TextField(
        modifier = Modifier.fillMaxWidth().testTag("LinkIconUri"),
        placeholder = {
            Text(text = stringResource(id = R.string.icon_url))
        },
        value = iconUri,
        onValueChange = onIconUriChange
    )
}

@Preview
@Composable
fun LinkIconUriPreview() {
    LinkIconUri(
        iconUri = "content://media/picker/0/com.android.providers.media.photopicker/media/1000000000",
        onIconUriChange = {}
    )
}

@Preview
@Composable
fun LinkIconUriPlaceholderPreview() {
    LinkIconUri(iconUri = "", onIconUriChange = {})
}
