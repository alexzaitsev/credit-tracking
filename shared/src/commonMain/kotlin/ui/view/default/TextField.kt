package ui.view.default

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.view.rememberMutable

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    defaultValue: String = "",
    hint: String? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    minLines: Int = 1,
    maxLines: Int = 1,
    onTextChanged: (String) -> Unit
) {
    val tfValue = rememberMutable(defaultValue)

    TextField(
        modifier = modifier,
        value = tfValue.value,
        label = @Composable {
            if (hint == null) {
                // there is no hint
            } else {
                Text(text = hint)
            }
        },
        isError = isError,
        onValueChange = { newVal: String ->
            tfValue.value = newVal
            onTextChanged(newVal)
        },
        keyboardOptions = keyboardOptions,
        minLines = minLines,
        maxLines = maxLines
    )
}
