package ui.view.default

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) =
    Button(
        modifier = modifier,
        onClick = onClick,
        content = {
            Text(text)
        })
