package com.example.dicodingeventapp.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventapp.EventListAdapter
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.FragmentEventUpcomingBinding

class EventUpcomingFragment : Fragment() {

    private var _binding: FragmentEventUpcomingBinding? = null

    private val binding get() = _binding!!

    private val upcomingEventViewModel by viewModels<EventUpcomingViewModel>()

    private lateinit var adapter: EventListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventUpcomingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.rvUpcomingEvent.layoutManager = layoutManager

        adapter = EventListAdapter { eventItem ->
            val bundle = Bundle().apply {
                putInt("event_id", eventItem.id)
            }
            findNavController().navigate(
                R.id.action_navigation_upcoming_to_eventDetailFragment,
                bundle
            )
        }

        binding.rvUpcomingEvent.adapter = adapter

        upcomingEventViewModel.listEvent.observe(viewLifecycleOwner) { eventData ->
            setEventData(eventData)
        }

        upcomingEventViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setEventData(eventData: List<ListEventsItem>) {
        adapter.submitList(eventData)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}