package com.alexzh.imbarista.ui.map

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.CafeView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.ui.home.HomeActivity
import com.alexzh.imbarista.viewmodel.TomTomMapViewModel
import com.google.android.material.snackbar.Snackbar
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*
import kotlinx.android.synthetic.main.fragment_tomtom_map.*
import kotlinx.android.synthetic.main.fragment_tomtom_map.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TomTomMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: TomtomMap

    private val tomtomMapViewModel: TomTomMapViewModel by viewModel()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_tomtom_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment
        mapFragment.getAsyncMap(this)

        rootView.currentLocation.setOnClickListener {
            map.centerOnMyLocation()
            tomtomMapViewModel.fetchCafes(this.map.locationSource)
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        if (!checkLocationPermission(this.requireContext())) {
            // Permission is not granted; show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this.requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showPermissionExplanationSnackBar()
            } else {
                // No explanation needed, we can request the permission.
                requestLocationPermissions(this.requireActivity())
            }
        } else {
            tomtomMapViewModel.getCafes().observe(this, Observer<Resource<List<CafeView>>> {
                it?.let {
                    handleLoadingCafes(it)
                }
            })
        }
    }

    private fun handleLoadingCafes(resource: Resource<List<CafeView>>) {
        when (resource.status) {
            ResourceState.LOADING -> {

            }
            ResourceState.SUCCESS -> {
                this.map.clear()

                resource.data?.forEach {
                    val marker = MarkerBuilder(LatLng(it.latitude, it.longitude))
                        .icon(Icon.Factory.fromResources(this@TomTomMapFragment.requireContext(), R.drawable.ic_map_cafe_poi))
                        .markerBalloon(SimpleMarkerBalloon(it.title))

                    this.map.addMarker(marker)
                }
                map.centerOnMyLocation()
            }
            ResourceState.ERROR -> {

            }
        }
    }

    override fun onMapReady(map: TomtomMap) {
        this.map = map
        this.map.isMyLocationEnabled = true
        this.map.markerSettings.setMarkersClustering(true)

        tomtomMapViewModel.fetchCafes(this.map.locationSource)
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

    private fun showPermissionExplanationSnackBar() {
        Snackbar.make(root, R.string.permission_explanation, Snackbar.LENGTH_LONG)
            .setAction(R.string.permission_grant_text) { requestLocationPermissions(this.activity!!) }
            .show()
    }

    private fun checkLocationPermission(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            HomeActivity.LOCATION_REQUEST_CODE
        )
    }
}