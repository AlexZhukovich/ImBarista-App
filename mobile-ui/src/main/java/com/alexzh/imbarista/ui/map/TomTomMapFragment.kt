package com.alexzh.imbarista.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
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

    private var map: TomtomMap? = null

    private val tomtomMapViewModel: TomTomMapViewModel by viewModel()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_tomtom_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment
        mapFragment.getAsyncMap(this)

        rootView.currentLocation.setOnClickListener {
            map?.centerOnMyLocation()
            tomtomMapViewModel.fetchCafes(this.map?.locationSource!!)
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
                showPermissionExplanation()
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
                this.map?.clear()

                if (resource.data != null) {
                    if (resource.data.isNotEmpty()) {
                        resource.data.forEach {
                            val marker = MarkerBuilder(LatLng(it.latitude, it.longitude))
                                .icon(Icon.Factory.fromResources(this@TomTomMapFragment.requireContext(), R.drawable.ic_map_poi_cafe_poi))
                                .markerBalloon(SimpleMarkerBalloon(it.title))

                            this.map?.addMarker(marker)
                        }
                        map?.centerOnMyLocation()
                    } else {
                        showErrorMessage(getString(R.string.error_no_cafes_near_you))
                    }
                }
            }
            ResourceState.ERROR -> {
                showErrorMessage(getString(R.string.error_unexpected_error_during_loading_cafes_near_you))
            }
        }
    }

    override fun onMapReady(map: TomtomMap) {
        this.map = map
        this.map?.isMyLocationEnabled = true
        this.map?.markerSettings?.setMarkersClustering(true)
        this.map?.uiSettings?.compassView?.hide()

        this.map?.locationSource?.let { tomtomMapViewModel.fetchCafes(it) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        this.map?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.general_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onStop() {
        this.map?.clear()
        super.onStop()
    }

    private fun showPermissionExplanation() {
        showErrorMessage(
            getString(R.string.permission_explanation),
            getString(R.string.permission_grant_text),
            { requestLocationPermissions(this.activity!!) },
            Snackbar.LENGTH_INDEFINITE
        )
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

    @SuppressLint("PrivateResource")
    private fun showErrorMessage(
        messageText: String,
        actionText: String? = null,
        action: (() -> Unit)? = null,
        snackbarLength: Int = Snackbar.LENGTH_LONG
    ) {
        val snackbar = Snackbar.make(root, messageText, snackbarLength)
        snackbar.apply {
            val commonMargin = resources.getDimension(R.dimen.common_margin)
            val navigationHeight = resources.getDimension(R.dimen.design_bottom_navigation_height)
            view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams)
                .apply {
                    setMargins(leftMargin, topMargin, rightMargin, (navigationHeight + commonMargin).toInt())
                }
        }
        if (actionText != null &&  action != null) {
            snackbar.setAction(actionText) { action() }
        }
        snackbar.show()
    }
}