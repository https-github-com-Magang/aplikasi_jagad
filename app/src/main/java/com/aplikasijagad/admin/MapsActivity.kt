package com.aplikasijagad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aplikasijagad.admin.HomeAdminFragment
import com.aplikasijagad.models.Users
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_maps.*
import java.text.SimpleDateFormat
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var newestCoord:LatLng
    private lateinit var usertype: String
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        usertype = ""
        database= FirebaseDatabase.getInstance().getReference("Users")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        loadMarkersFromDB()
        refresh_btn.setOnClickListener {
            loadMarkersFromDB()
        }
        logout_btn.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    finish()

                } else
                    Toast.makeText(this, "Logout Succesfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
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
    }

//    private fun getMapUpdate(){
//        val rootRef = FirebaseDatabase.getInstance().reference
//        val ref= rootRef.child("Users")
//        val valueEventListener = object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (ds in snapshot.children) {
//                    val latitude = ds.child("lat").getValue(Double::class.java)
//                    val longitude = ds.child("lng").getValue(Double::class.java)
//                    val name = ds.child("name").getValue(String::class.java)
//
//                    val userCoord = LatLng(latitude!!, longitude!!)
//                    newestCoord = userCoord
//                    mMap.addMarker(
//                        MarkerOptions()
//                            .position(userCoord)
//                            .title(name)
//                    )
//                }
//                val zoomLevel = 15f
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newestCoord,zoomLevel))
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("TAGerror", error.getMessage())
//            }
//        }
//        ref.addListenerForSingleValueEvent(valueEventListener)
//    }

    private fun loadMarkersFromDB(){

        database.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    var lat: Double
                    var lng: Double
                    var position: LatLng
                    for(spotLatLng:DataSnapshot in dataSnapshot.children){
                        lat = spotLatLng.child("lat/").value.toString().toDouble()
                        lng = spotLatLng.child("lng/").value.toString().toDouble()
                        position = LatLng(lat, lng)
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        } )
    }

}