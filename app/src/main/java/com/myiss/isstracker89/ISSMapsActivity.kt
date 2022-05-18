package com.myiss.isstracker89

/********************************IMPORT********************************/
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.myiss.isstracker89.databinding.ActivityIssmapsBinding
import getLastKnownLocation
import java.util.*
import kotlin.concurrent.fixedRateTimer

/********************************IMPORT********************************/

class ISSMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    /********************************ATTRIBUTE********************************/
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityIssmapsBinding
    private lateinit var model: ISSMapsViewModel
    var timer: Timer? = null
    var move = true
    /********************************ATTRIBUTE********************************/

    /********************************ONCREATE********************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIssmapsBinding.inflate(layoutInflater)
        model = ViewModelProvider(this).get(ISSMapsViewModel::class.java)
        setContentView(binding.root)

        findViewById<Button>(R.id.move_card).setOnClickListener { move = !move }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            mMap.isMyLocationEnabled = true;
        }

        model.data.observe(this) {
            if (it != null) {
                mMap.clear()
                val iss = LatLng(it.latitude, it.longitude)
                mMap.addMarker(
                    MarkerOptions()
                        .position(iss)
                        .title("ISS")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )

                var loc = getLastKnownLocation(this)
                if (loc != null) {
                    mMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(loc.latitude, loc.longitude))
                            .title("YOU")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    )
                }

                if (move)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(iss, 6f))
            }
        } // placing a listener on the data

        model.cityPos.observe(this) {
            binding.textView.text = it ?: "..."
        } // placing a listener on the city


        model.loadData(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    /********************************ONCREATE********************************/

    /********************************OnMapReady********************************/
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }
    /********************************OnMapReady********************************/

    /********************************OnStart********************************/
    override fun onStart() {
        super.onStart()
        timer = fixedRateTimer(period = 1000) {
            model.loadData(this@ISSMapsActivity)
        }
    }
    /********************************OnStart********************************/

    /********************************OnStop********************************/
    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }
    /********************************OnStop********************************/

}

