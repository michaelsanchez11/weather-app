package com.example.weatherappjpm.search

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappjpm.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchBar: EditText
    private lateinit var weatherDataTexView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
        searchBar = view.findViewById(R.id.searchEditText)
        weatherDataTexView = view.findViewById(R.id.weather_data)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

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
}
