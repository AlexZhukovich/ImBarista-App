package com.alexzh.imbarista.map

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alexzh.imbarista.R
import com.tomtom.online.sdk.map.MapFragment
import com.tomtom.online.sdk.map.OnMapReadyCallback
import com.tomtom.online.sdk.map.TomtomMap
import kotlinx.android.synthetic.main.fragment_tomtom_map.view.*

class TomTomMapFragment : Fragment(), OnMapReadyCallback {

    lateinit var map: TomtomMap

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_tomtom_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment
        mapFragment.getAsyncMap(this)

        rootView.currentLocation.setOnClickListener {
            Toast.makeText(this.context, "current location -> clicked", Toast.LENGTH_SHORT).show()
        }

        return rootView
    }

    override fun onMapReady(map: TomtomMap) {
        this.map = map
        this.map.isMyLocationEnabled = true
        this.map.markerSettings.setMarkersClustering(true)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        this.map.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.general_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onStop() {
        this.map.clear()
        super.onStop()
    }
}