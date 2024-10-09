package com.example.dicodingeventapp.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.FragmentUpcomingEventBinding

class EventUpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingEventBinding? = null

    private val binding get() = _binding!!

    private val upcomingEventViewModel by viewModels<EventUpcomingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.rvUpcomingEvent.layoutManager = layoutManager


        upcomingEventViewModel.listEvent.observe(viewLifecycleOwner) { eventData ->
            setEventData(eventData)
        }

        upcomingEventViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setEventData(eventData: List<ListEventsItem>) {
        val adapter = EventAdapter()
        adapter.submitList(eventData)
        binding.rvUpcomingEvent.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}