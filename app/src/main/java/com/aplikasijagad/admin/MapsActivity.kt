package com.aplikasijagad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aplikasijagad.admin.HomeAdminFragment
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var newestCoord: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        getMapUpdate()

        refresh_btn.setOnClickListener {
            getMapUpdate()
        }

        logout_btn.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                    val intent = Intent(this, MapsActivity::class.java)
                    finish()
                    startActivity(intent)

                } else
                    Toast.makeText(this, "Logout Succesfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeAdminFragment::class.java)
                finish()
                startActivity(intent)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun getMapUpdate() {
        val rootReference = FirebaseDatabase.getInstance().reference
        val reference = rootReference.child("Users")
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val latitude = ds.child("lat").getValue(Double::class.java)
                    val longitude = ds.child("lng").getValue(Double::class.java)
                    val name = ds.child("name").getValue(String::class.java)
                    Log.d("getDataCoor", "latitude= " + latitude + " longitude: " + longitude)

                    val userCoord = LatLng(latitude!!, longitude!!)
                    newestCoord = userCoord
                    mMap.addMarker(MarkerOptions().position(userCoord).title(name))
                }
                val zoomLevel = 15f
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newestCoord, zoomLevel))
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAGerror", error.getMessage())
            }
        }
        reference.addListenerForSingleValueEvent(valueEventListener)
    }
}