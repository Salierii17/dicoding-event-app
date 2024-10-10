package com.example.dicodingeventapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.FragmentHomeBinding
import com.example.dicodingeventapp.EventCarouselAdapter
import com.example.dicodingeventapp.EventListAdapter
import com.example.dicodingeventapp.ui.finished.EventFinishedViewModel
import com.example.dicodingeventapp.ui.upcoming.EventUpcomingViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val eventUpcomingViewModel by viewModels<EventUpcomingViewModel>()
    private val eventFinishedViewModel by viewModels<EventFinishedViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()


    private lateinit var upcomingEventListAdapter: EventCarouselAdapter
    private lateinit var finishedEventListAdapter: EventCarouselAdapter
    private lateinit var searchAdapter: EventListAdapter


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

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener {textView, actionId, event ->
                searchBar.setText(searchView.text)
                searchView.hide()
                Snackbar.make(view, searchView.text, Toast.LENGTH_SHORT).show()
                false
            }
        }


        binding.rvUpcomingEvent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvFinishedEvent.layoutManager = LinearLayoutManager(context)
        binding.rvFinishedEvent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        upcomingEventListAdapter = EventCarouselAdapter { eventItem ->
            navigateToEventDetail(eventItem.id)
        }
        finishedEventListAdapter = EventCarouselAdapter { eventItem ->
            navigateToEventDetail(eventItem.id)
        }

        binding.rvUpcomingEvent.adapter = upcomingEventListAdapter
        binding.rvFinishedEvent.adapter = finishedEventListAdapter

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