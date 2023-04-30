package ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ui.view.default.DefaultButton
import ui.view.default.DefaultSpacer
import ui.view.default.DefaultTextField
import ui.view.default.DefaultTopBar

@Composable
fun AddTxScreen(onBackPressed: () -> Unit) = Column {
    DefaultTopBar(
        title = "Add transaction",
        onBackPressed = onBackPressed
    )
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        // amount - mandatory
        DefaultTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "Enter amount",
            onTextChanged = {
                // TODO propagate to VM
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        DefaultSpacer(8.dp)

        // comment - optional
        DefaultTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "Enter comment (optional)",
            onTextChanged = {
                // TODO propagate to VM
            },
            minLines = 3,
            maxLines = 3
        )
        DefaultSpacer(8.dp)

        // button
        DefaultButton(
            text = "Done",
            onClick = {
                // TODO propagate to VM
            }
        )
    }
}
