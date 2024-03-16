package com.example.weatherappjpm.search

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappjpm.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchBar: EditText
    private lateinit var weatherDataTexView: TextView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
        searchBar = view.findViewById(R.id.searchEditText)
        weatherDataTexView = view.findViewById(R.id.weather_data)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        if (checkLocationPermission()) {
            getLocation()
        } else {
            requestLocationPermission()
        }

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchViewModel.setSearchQuery(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                performSearch(searchBar.text.toString())
                true
            } else {
                false
            }
        }

        searchViewModel.fetchMostRecentLocationSearched()

        searchViewModel.localDbData.observe(viewLifecycleOwner) { localData ->
            weatherDataTexView.text = localData?.toString() ?: ""
        }

        searchViewModel.networkData.observe(viewLifecycleOwner) { networkData ->
            weatherDataTexView.text = networkData?.toString() ?: ""
        }

        searchViewModel.networkConnected.observe(viewLifecycleOwner) { networkConnected ->
            if (!networkConnected) {
                Toast.makeText(context, getString(R.string.network_error_toast), Toast.LENGTH_LONG).show()
                searchViewModel.setNetworkConnected(true)
            }
        }
    }

    private fun performSearch(query: String) {
        searchViewModel.fetchNetworkData(query, getString(R.string.weather_api_key))
    }

    private fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // Use the location
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    searchViewModel.fetchNetworkDataByLocation(latitude, longitude, getString(R.string.weather_api_key))
                }
            }
            .addOnFailureListener { e ->
                // Handle failure
                Toast.makeText(requireContext(), "Failed to get location", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 100
    }
}
