package com.example.dicodingeventapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventapp.CarouselAdapter
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.SearchAdapter
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.FragmentHomeBinding
import com.example.dicodingeventapp.ui.finished.EventFinishedViewModel
import com.example.dicodingeventapp.ui.upcoming.EventUpcomingViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val eventUpcomingViewModel by viewModels<EventUpcomingViewModel>()
    private val eventFinishedViewModel by viewModels<EventFinishedViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()


    private lateinit var upcomingEventListAdapter: CarouselAdapter
    private lateinit var finishedEventListAdapter: CarouselAdapter
    private lateinit var searchAdapter: SearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        binding.rvSearchResult.layoutManager = LinearLayoutManager(context)
        binding.rvUpcomingEvent.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFinishedEvent.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        searchAdapter = SearchAdapter { eventItem ->
            navigateToEventDetail(eventItem.id)
        }
        upcomingEventListAdapter = CarouselAdapter { eventItem ->
            navigateToEventDetail(eventItem.id)
        }
        finishedEventListAdapter = CarouselAdapter { eventItem ->
            navigateToEventDetail(eventItem.id)
        }

        binding.rvSearchResult.adapter = searchAdapter
        binding.rvUpcomingEvent.adapter = upcomingEventListAdapter
        binding.rvFinishedEvent.adapter = finishedEventListAdapter

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = searchView.text.toString()
                    searchBar.setText(query)
                    homeViewModel.search(query)
                    rvSearchResult.visibility = View.VISIBLE
                    Snackbar.make(view, query, Toast.LENGTH_SHORT).show()
                    true
                } else {
                    false
                }
            }
        }

        homeViewModel.searchResult.observe(viewLifecycleOwner) { eventItem ->
            setSearchResultData(eventItem)
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        eventUpcomingViewModel.listEvent.observe(viewLifecycleOwner) { eventData ->
            setUpcomingEventData(eventData)
        }
        eventUpcomingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        eventFinishedViewModel.listEvent.observe(viewLifecycleOwner) { eventData ->
            setFinishedEventData(eventData)
        }
        eventFinishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun navigateToEventDetail(eventId: Int) {
        val bundle = Bundle().apply {
            putInt("event_id", eventId)
        }
        findNavController().navigate(R.id.action_navigation_home_to_eventDetailFragment, bundle)
    }

    private fun setSearchResultData(eventData: List<ListEventsItem>) {
        if (eventData.isNotEmpty()) {
            searchAdapter.submitList(eventData)
            binding.rvSearchResult.visibility = View.VISIBLE
        } else {
            binding.rvSearchResult.visibility = View.GONE
        }
    }

    private fun setUpcomingEventData(eventData: List<ListEventsItem>) {
        upcomingEventListAdapter.submitList(eventData)
    }

    private fun setFinishedEventData(eventData: List<ListEventsItem>) {
        finishedEventListAdapter.submitList(eventData)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}