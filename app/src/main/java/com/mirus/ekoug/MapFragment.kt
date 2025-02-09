package com.mirus.ekoug

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import com.google.android.gms.common.internal.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val replace_contrainer = view.findViewById<ConstraintLayout>(R.id.replace_contrainer)
        replace_contrainer.setOnClickListener {
            Log.e("click_of_button", "Go go go")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(47.76790431738558, 41.13845331826536), 6.3f))
        val db = Firebase.firestore
        db.collection("Станции зарядки/Ростовская Область/Ростов-на-Дону").get().addOnSuccessListener {
            for(res in it){
                var kordi = res.getString("координаты").toString().split(";")
                mMap.addMarker(MarkerOptions().position(LatLng(kordi[0].toDouble(), kordi[1].toDouble())).title(res.getString("название")).icon(BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN)))
            }
        }
    }
}
