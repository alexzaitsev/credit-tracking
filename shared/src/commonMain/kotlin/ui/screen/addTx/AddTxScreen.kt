package ui.screen.addTx

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ui.screen.observeSideEffect
import ui.screen.observeState
import ui.view.default.DefaultButton
import ui.view.default.DefaultProgress
import ui.view.default.DefaultSpacer
import ui.view.default.DefaultTextField
import ui.view.default.DefaultTopBar
import ui.view.rememberMutable

@Composable
fun AddTxScreen(sm: AddTxScreenModel, onBackClicked: () -> Unit, onAdded: () -> Unit) {
    when (sm.observeState()) {
        AddTxScreenModel.State.Initial -> InitialState(
            onBackClicked = onBackClicked,
            onDoneClicked = { amount, comment ->
                sm.addTx(amount = amount, comment = comment)
            }
        )

        AddTxScreenModel.State.Loading -> DefaultProgress()
    }

    sm.observeSideEffect { sideEffect ->
        when (sideEffect) {
            AddTxScreenModel.SideEffect.TxAdded -> onAdded()
        }
    }
}

@Composable
private fun InitialState(
    onBackClicked: () -> Unit,
    onDoneClicked: (Double, String) -> Unit
) {
    var amount by rememberMutable("")
    var comment by rememberMutable("")
    var amountError by rememberMutable(false)
    val commentError by rememberMutable(false)

    Column {
        DefaultTopBar(
            title = "Add transaction",
            onBackPressed = onBackClicked
        )
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // amount - mandatory
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                defaultValue = amount,
                hint = "Enter amount",
                isError = amountError,
                onTextChanged = { newAmount ->
                    amount = newAmount
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            DefaultSpacer(8.dp)

            // comment - optional
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                defaultValue = comment,
                hint = "Enter comment (optional)",
                isError = commentError,
                onTextChanged = { newComment ->
                    comment = newComment
                },
                minLines = 3,
                maxLines = 3
            )
            DefaultSpacer(8.dp)

            // button
            DefaultButton(
                text = "Done",
                onClick = {
                    val amountDouble = amount.toDoubleOrNull()
                    if (amountDouble == null) {
                        amountError = true
                    } else {
                        onDoneClicked(amountDouble, comment)
                    }
                }
            )
        }
    }
}
