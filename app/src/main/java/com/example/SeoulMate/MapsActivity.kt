package com.example.SeoulMate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        // Add a marker in Sydney and move the camera
        val IncheonAirport = LatLng(37.460531, 126.440996)
        mMap.addMarker(MarkerOptions().position(IncheonAirport).title("Incheon International Airport"))



        val Gwanghwamun = LatLng(37.575960, 126.976925)
        mMap.addMarker(MarkerOptions().position(Gwanghwamun).title("Gwanghwamun"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Gwanghwamun))

        val MyeongDong = LatLng(37.5599, 126.9868)
        mMap.addMarker(MarkerOptions().position(MyeongDong).title("Myeong-dong Station Underground Shopping Center"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MyeongDong))

        val Hongik = LatLng(37.556039, 126.922978)
        mMap.addMarker(MarkerOptions().position(Hongik).title("Exit 9 of Hongik University"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Hongik))

        val Gangnam = LatLng(37.498001, 127.027457)
        mMap.addMarker(MarkerOptions().position(Gangnam).title("Gangnam Station Underground Shopping Center"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Gangnam))

        val Itaewon = LatLng(37.534340, 126.993718)
        mMap.addMarker(MarkerOptions().position(Itaewon).title("Itaewon"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Itaewon))

        val Bukchon = LatLng(37.582417, 126.983640)
        mMap.addMarker(MarkerOptions().position(Bukchon).title("Bukchon Hanok Village"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Bukchon))


        val Sungnyemun = LatLng(37.559921, 126.975343)
        mMap.addMarker(MarkerOptions().position(Sungnyemun).title("Sungnyemun Gate"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Sungnyemun))


        val Namsan = LatLng(37.551357, 126.988205)
        mMap.addMarker(MarkerOptions().position(Namsan).title("the Namsan Tower"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Namsan))


        val Cheongwadae = LatLng(37.586642, 126.975004)
        mMap.addMarker(MarkerOptions().position(Cheongwadae).title("Cheongwadae, the Blue House, the Korean presidential residence"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Cheongwadae))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Namsan, 11.5f))

        val Insadong = LatLng(37.574350, 126.984937)
        mMap.addMarker(MarkerOptions().position(Insadong).title("Insadong Ssamji-gil"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Insadong))


        val Gyeongbokgung = LatLng(37.579591, 126.977331)
        mMap.addMarker(MarkerOptions().position(Gyeongbokgung).title("Gyeongbokgung Palace"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Gyeongbokgung))


        val LotteTower = LatLng(37.512992, 127.102764)
        mMap.addMarker(MarkerOptions().position(LotteTower).title("Lotte World Tower"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LotteTower))


        val LotteWorld = LatLng(37.511122, 127.098553)
        mMap.addMarker(MarkerOptions().position(LotteWorld).title("Lotte World"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LotteWorld))


        val Express= LatLng(37.504906, 127.004880)
        mMap.addMarker(MarkerOptions().position(Express).title("Express Bus Terminal Station"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Express))











    }
}
