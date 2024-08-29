import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import components.CustomIconButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WindowScope.AppWindowTitleBar() = WindowDraggableArea {
    var boxHeight by remember { mutableStateOf(48.dp) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(boxHeight)
            .background(if (isWindowsInDarkMode()) Color(0xFF202020) else Color.LightGray)
            .drawBehind {
                val borderColor = Color.Black // Change this to your desired border color
                val borderWidth = 1.dp.toPx() // Border width

                // Draw bottom border
                drawLine(
                    color = borderColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderWidth
                )
            }
    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Test Yazılım",
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 10.dp)
            )

            Row {
                CustomIconButton(
                    painter = painterResource("./drawable/minimize.png"),
                    contentDescription = "",
                    size = boxHeight,
                    onClick = {  },
                    defaultColor = Color.Transparent,

                    hoverColor = Color(0xFF404040),
                    contentColor = Color.White,
                    animateColorTransition = false,
                    hoverContentColor = Color.White,
                ) { painter, contentDescription, currentColor ->

                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painter,
                        contentDescription = contentDescription,
                        colorFilter = ColorFilter.tint(currentColor),
                    )



                }
                CustomIconButton(
                    painter = painterResource("./drawable/close.png"),
                    contentDescription = "",
                    size = boxHeight,
                    onClick = {  },
                    defaultColor = Color.Transparent,
                    hoverColor = Color.Red,
                    contentColor = Color.White,
                    animateColorTransition = false,
                    hoverContentColor = Color.White,
                ) { painter, contentDescription, currentColor ->

                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painter,
                        contentDescription = contentDescription,
                        colorFilter = ColorFilter.tint(currentColor),
                    )



                }
            }
        }
    }
}