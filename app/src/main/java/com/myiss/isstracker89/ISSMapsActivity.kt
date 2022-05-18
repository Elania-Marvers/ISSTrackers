package com.myiss.isstracker89

/********************************IMPORT********************************/
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.myiss.isstracker89.databinding.ActivityIssmapsBinding

/********************************IMPORT********************************/

class ISSMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    /********************************ATTRIBUTE********************************/
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityIssmapsBinding
    private lateinit var model: ISSMapsViewModel
    /********************************ATTRIBUTE********************************/

    /********************************ONCREATE********************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIssmapsBinding.inflate(layoutInflater)
        model = ViewModelProvider(this).get(ISSMapsViewModel::class.java)
        setContentView(binding.root)

        model.data.observe(this) {
            if (it != null) {
                val iss = LatLng(it.latitude, it.longitude)
                mMap.addMarker(MarkerOptions().position(iss).title("Marker in Sydney"))
                mMap.animateCamera(CameraUpdateFactory.newLatLng(iss))
            }
        } // placing a listener on the data

        model.loadData()
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
}

