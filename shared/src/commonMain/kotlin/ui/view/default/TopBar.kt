package ui.view.default

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DefaultTopBar(
    title: String,
    backgroundColor: Color,
    textColor: Color,
    onBackPressed: () -> Unit
) = TopAppBar(
    backgroundColor = backgroundColor,
    elevation = 10.dp,
    title = {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = textColor,
            text = title.toUpperCase(Locale.current)
        )
    }, navigationIcon = {
        Icon(
            modifier = Modifier.clickable { onBackPressed() }.padding(16.dp),
            painter = painterResource("ic_arrow_back.xml"),
            contentDescription = null,
            tint = textColor
        )
    })

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DefaultTopBarNoBack(
    title: String,
    backgroundColor: Color,
    textColor: Color
) = TopAppBar(
    backgroundColor = backgroundColor,
    elevation = 10.dp,
    title = {
        Text(
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = textColor,
            textAlign = TextAlign.Center,
            text = title.toUpperCase(Locale.current)
        )
    })

