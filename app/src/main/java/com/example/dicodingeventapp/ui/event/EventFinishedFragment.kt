package com.example.dicodingeventapp.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.FragmentFinishedEventBinding

class EventFinishedFragment : Fragment() {

    private var _binding: FragmentFinishedEventBinding? = null

    private val binding get() = _binding!!

    private val eventFinishedViewModel by viewModels<EventFinishedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedEventBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvFinishedEvent.layoutManager = layoutManager

        eventFinishedViewModel.listEvent.observe(viewLifecycleOwner) { eventData ->
            setEventData(eventData)
        }
        eventFinishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setEventData(eventData: List<ListEventsItem>?) {
        val adapter = EventAdapter()
        adapter.submitList(eventData)
        binding.rvFinishedEvent.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}