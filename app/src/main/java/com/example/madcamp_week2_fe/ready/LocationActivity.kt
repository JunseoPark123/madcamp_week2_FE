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

    }


    private fun addMyLocation() {
        val myLocation = LocationEntity(5, "김병호, 김삼열 IT 융합센터 (N1)", 36.374165, 127.365831)
        val positionLatLng = LatLng(myLocation.latitude, myLocation.longitude)
        val markerOption = MarkerOptions().apply {
            position(positionLatLng)
            title(myLocation.name)
        }

        googleMap.addMarker(markerOption)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 15f))
    }

    /**
     * onMapReady()
     * Map 이 사용할 준비가 되었을 때 호출
     * @param googleMap
     */
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        // 고정 위치 표시
        addFixedLocations()

        currentMarker = setupMarker(LatLngEntity(36.374165,127.365831))  // default N1
        currentMarker?.showInfoWindow()
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
            snippet("김병호, 김삼열 IT 융합센터(N1)")
        }

        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE  // 지도 유형 설정
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