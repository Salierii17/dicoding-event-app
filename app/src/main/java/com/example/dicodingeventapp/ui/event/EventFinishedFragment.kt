package com.example.dicodingeventapp.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.FragmentEventFinishedBinding

class EventFinishedFragment : Fragment() {

    private var _binding: FragmentEventFinishedBinding? = null

    private val binding get() = _binding!!

    private val eventFinishedViewModel by viewModels<EventFinishedViewModel>()

    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvFinishedEvent.layoutManager = layoutManager

        adapter = EventAdapter { event ->
            val bundle = Bundle().apply {
                putInt("event_id", event.id)
            }
            findNavController().navigate(R.id.nav_host_fragment_container, bundle)
        }

        binding.rvFinishedEvent.adapter = adapter

        eventFinishedViewModel.listEvent.observe(viewLifecycleOwner) { eventData ->
            setEventData(eventData)
        }
        eventFinishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setEventData(eventData: List<ListEventsItem>?) {
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