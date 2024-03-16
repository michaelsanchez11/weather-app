package com.example.weatherappjpm.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappjpm.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchBar: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
        searchBar = view.findViewById(R.id.searchEditText)

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

        searchViewModel.fetchLocalDbData()
        searchViewModel.fetchNetworkData("Soledad", getString(R.string.weather_api_key))

        searchViewModel.searchQuery.observe(viewLifecycleOwner) { query ->
            performSearch(query)
        }

        searchViewModel.localDbData.observe(viewLifecycleOwner) { localData ->
            Log.d("Michael", "Local Data $localData")
        }

        searchViewModel.networkData.observe(viewLifecycleOwner) { networkData ->
            Log.d("Michael", "Network Data $networkData")
        }
    }

    private fun performSearch(query: String) {
        // vm call to api(query)
    }
}
