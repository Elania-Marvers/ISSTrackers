import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import androidx.core.content.ContextCompat

fun getLastKnownLocation(c: Context): Location? {

    //Contrôle de la permission
    if (ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_DENIED
    ) {
        return null
    }

    var lm = c.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.getProviders(true).map { lm.getLastKnownLocation(it) }
        .minByOrNull { it?.accuracy ?: Float.MAX_VALUE }
}