import com.sun.jna.Native
import com.sun.jna.platform.win32.WinReg
import com.sun.jna.platform.win32.Advapi32Util
import androidx.compose.ui.graphics.Color

fun isWindowsInDarkMode(): Boolean {
    val key = "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize"
    val valueName = "AppsUseLightTheme"
    return try {
        val lightThemeEnabled = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, key, valueName)
        lightThemeEnabled == 0
    } catch (e: Exception) {
        false
    }
}
