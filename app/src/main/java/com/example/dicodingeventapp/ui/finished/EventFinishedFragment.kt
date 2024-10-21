package com.example.dicodingeventapp.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dicodingeventapp.EventListAdapter
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.FragmentEventFinishedBinding
import com.google.android.material.snackbar.Snackbar

class EventFinishedFragment : Fragment() {

    private var _binding: FragmentEventFinishedBinding? = null

    private val binding get() = _binding

    private val eventFinishedViewModel by viewModels<EventFinishedViewModel>()

    private lateinit var adapter: EventListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventFinishedBinding.inflate(inflater, container, false)
        val root: View = binding?.root ?: View(requireContext())
        return root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding?.rvFinishedEvent?.layoutManager = layoutManager

        adapter = EventListAdapter { event ->
            val bundle = Bundle().apply {
                putInt("event_id", event.id)
            }
            findNavController().navigate(
                R.id.action_navigation_finished_to_eventDetailFragment,
                bundle
            )
        }

        binding?.rvFinishedEvent?.adapter = adapter

        eventFinishedViewModel.listEvent.observe(viewLifecycleOwner) { eventData ->
            setEventData(eventData)
        }
        eventFinishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        eventFinishedViewModel.snackBar.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                Snackbar.make(
                    requireView(),
                    message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setEventData(eventData: List<ListEventsItem>?) {
        adapter.submitList(eventData)
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}