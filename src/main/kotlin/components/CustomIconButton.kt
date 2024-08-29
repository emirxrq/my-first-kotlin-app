package components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager

@ExperimentalComposeUiApi
@Composable
fun CustomIconButton(
    painter: Painter,
    contentDescription: String,
    size: Dp? = null, // Default size
    onClick: () -> Unit,
    defaultColor: Color = Color.Transparent,
    hoverColor: Color = Color(0xFF616161),
    contentColor: Color = Color.White,
    hoverContentColor: Color = Color.Black,
    shadowColor: Color = Color.Transparent,
    shadowRadius: Dp = 0.dp,
    shape: Dp = 0.dp,
    animateColorTransition: Boolean = true,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp,
    children: @Composable (painter: Painter, contentDescription: String, contentColor: Color) -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val backgroundColor by animateColorAsState (
        targetValue = if (isHovered) hoverColor else defaultColor

    )

    val currentContentColor by animateColorAsState(
        targetValue = if (isHovered) hoverContentColor else contentColor
    )


    var modifier = Modifier
        .border(BorderStroke(0.dp, shadowColor))
        .pointerHoverIcon(PointerIcon.Hand, true)
        .pointerMoveFilter(
            onEnter = {
                isHovered = true
                false
            },
            onExit = {
                isHovered = false
                false
            }
        )

    size?.let {
        modifier = modifier.size(it)
    }

    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = (if (animateColorTransition) backgroundColor else {
                (if (isHovered) hoverColor else defaultColor)
            }),
            contentColor = currentContentColor
        ),
        elevation = ButtonDefaults.elevation(shadowRadius),
        shape = RoundedCornerShape(shape),
        onClick = {
            onClick()

            focusManager.clearFocus()
        },
        contentPadding = PaddingValues(paddingHorizontal, paddingVertical),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            children(painter, contentDescription, (if (animateColorTransition) currentContentColor else {
                (if (isHovered) hoverContentColor else contentColor)
            }))
        }
    }
}
