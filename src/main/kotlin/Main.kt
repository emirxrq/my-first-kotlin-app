import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.ptr.IntByReference
import com.sun.jna.win32.W32APIOptions
import com.sun.jna.platform.win32.User32
import components.CustomIconButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import screens.HomeScreen
import screens.LoginScreen


interface DwmApi : com.sun.jna.Library {
    fun DwmSetWindowAttribute(hwnd: WinDef.HWND?, dwAttribute: Int, pvAttribute: IntByReference?, cbAttribute: Int): Int

    companion object {
        val INSTANCE: DwmApi = Native.load("dwmapi", DwmApi::class.java, W32APIOptions.DEFAULT_OPTIONS)
    }
}

fun setTitleBarBasedOnTheme() {
    println(isWindowsInDarkMode());
    val hwnd = User32.INSTANCE.GetForegroundWindow()
    val darkMode = IntByReference(if (isWindowsInDarkMode()) 1 else 0)

    DwmApi.INSTANCE.DwmSetWindowAttribute(hwnd, 20 /* DWMWA_USE_IMMERSIVE_DARK_MODE */, darkMode, 4)
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    val scope = rememberCoroutineScope()
    val windowState = WindowState(width = 800.dp, height = 600.dp)
    var dragStartPosition by remember { mutableStateOf(Offset.Zero) }

    val isDarkMode = isWindowsInDarkMode()

    val darkColors = darkColors(
        primary = Color(0xFFBB86FC),
        primaryVariant = Color(0xFFBB86FC),
        secondary = Color(0xFF03DAC6),
        background = Color(0xFF171717),
        surface = Color(0xFF121212),
        onPrimary = Color.Black,
        onSecondary = Color.Black,
        onBackground = Color.White,
        onSurface = Color.White
    )

    val lightColors = lightColors(
        primary = Color(0xFF6200EE),
        primaryVariant = Color(0xFF3700B3),
        secondary = Color(0xFF03DAC6),
        background = Color(0xFFFFFFFF),
        surface = Color(0xFFFFFFFF),
        onPrimary = Color.Black,
        onSecondary = Color.Black,
        onBackground = Color.Black,
        onSurface = Color.Black
    )

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        undecorated = true,
        title = "Test Yazılım",
        transparent = true
    ) {
        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                delay(100)
                setTitleBarBasedOnTheme()
            }
        }

        MaterialTheme(colors = if (isDarkMode) darkColors else lightColors) {

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .shadow(3.dp, RoundedCornerShape(7.dp)),
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(7.dp)
            ) {

                Column(
                ) {
                    AppWindowTitleBar()


                        Navigator(LoginScreen()) { navigator ->
                            SlideTransition(navigator)
                        }

                }
            }
        }
    }
}
