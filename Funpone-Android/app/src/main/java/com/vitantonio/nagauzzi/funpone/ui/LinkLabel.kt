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
fun LinkLabel(label: String, onLabelChange: (String) -> Unit) {
    TextField(
        modifier = Modifier.fillMaxWidth().testTag("LinkLabel"),
        placeholder = {
            Text(text = stringResource(id = R.string.label))
        },
        value = label,
        onValueChange = onLabelChange
    )
}

@Preview
@Composable
fun LinkLabelPreview() {
    LinkLabel(label = "サンプル", onLabelChange = {})
}

@Preview
@Composable
fun LinkLabelPlaceholderPreview() {
    LinkLabel(label = "", onLabelChange = {})
}
