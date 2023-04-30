package ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TopBar(title: String, onBackPressed: () -> Unit) {
    TopAppBar(title = {
        Text(text = title)
    }, navigationIcon = {
        Icon(
            modifier = Modifier.clickable { onBackPressed() }.padding(16.dp),
            painter = painterResource("ic_arrow_back.xml"),
            contentDescription = null
        )
    })
}
