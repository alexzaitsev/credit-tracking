package ui.view.default

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) =
    Button(
        modifier = modifier,
        onClick = onClick,
        content = {
            Text(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                text = text
            )
        })
