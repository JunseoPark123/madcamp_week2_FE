package com.example.madcamp_week2_fe.ready

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.databinding.ActivityMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
internal class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val TAG = "LocationActivity"
        const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }

    lateinit var binding: ActivityMapBinding

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@LocationActivity)

        //위치 권한을 확인하고 업데이트를 시작
        requestLocationPermission()

    }
    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // 권한이 이미 허용된 경우 위치 업데이트를 시작할 수 있음
            startLocationUpdates()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허용됨
                    startLocationUpdates()
                } else {
                    // 권한 거부됨
                    Toast.makeText(
                        this,
                        "앱을 사용하려면 위치 권한이 필요합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 허용된 경우 위치 업데이트를 시작합니다.
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        val currentLatLng = LatLng(it.latitude, it.longitude)
                        currentMarker = setupMarker(LatLngEntity(it.latitude, it.longitude))
                        currentMarker?.showInfoWindow()
                        moveCameraToLocation(currentLatLng)
                    }
                }
        } else {
            // 권한이 거부된 경우, 다시 권한을 요청하거나 사용자에게 메시지를 표시할 수 있습니다.
            requestLocationPermission()
        }
    }
    private fun moveCameraToLocation(latLng: LatLng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    /**
     * onMapReady()
     * Map 이 사용할 준비가 되었을 때 호출
     * @param googleMap
     */
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        // 현재 위치 업데이트를 시작
        startLocationUpdates()

        // 고정 위치 표시
        addFixedLocations()
    }

    private fun addFixedLocations() {
        val locations = listOf(
            LocationEntity(1, "재령이네 과일가게", 36.372572, 127.368222),
            LocationEntity(2, "준서네 반찬가게", 36.375268, 127.366441),
            LocationEntity(3, "재듕이네 과일가게", 36.376115, 127.362753),
            LocationEntity(4, "쭌서네 반찬가게", 36.373403, 127.362524)
        )

        for (location in locations) {
            val positionLatLng = LatLng(location.latitude, location.longitude)
            val markerOption = MarkerOptions().apply {
                position(positionLatLng)
                title(location.name)
            }

            googleMap.addMarker(markerOption)
        }
    }


    /**
     * setupMarker()
     * 선택한 위치의 marker 표시
     * @param locationLatLngEntity
     * @return
     */
    private fun setupMarker(locationLatLngEntity: LatLngEntity): Marker? {

        val positionLatLng = LatLng(locationLatLngEntity.latitude!!,locationLatLngEntity.longitude!!)
        val markerOption = MarkerOptions().apply {
            position(positionLatLng)
            title("내 위치")
        }

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL  // 지도 유형 설정
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 15f))  // 카메라 이동
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))  // 줌의 정도 - 1 일 경우 세계지도 수준, 숫자가 커질 수록 상세지도가 표시됨
        return googleMap.addMarker(markerOption)

    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }
    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }


    /**
     * LatLngEntity data class
     *
     * @property latitude 위도 (ex. 37.5562)
     * @property longitude 경도 (ex. 126.9724)
     */
    data class LatLngEntity(
        var latitude: Double?,
        var longitude: Double?
    )

    /**
     * LocationEntity data class
     *
     * @property id         위치의 고유 식별자
     * @property name       위치의 이름
     * @property latitude   위치의 위도
     * @property longitude  위치의 경도
     */
    data class LocationEntity(
        val id: Int,
        val name: String,
        val latitude: Double,
        val longitude: Double
    )

}