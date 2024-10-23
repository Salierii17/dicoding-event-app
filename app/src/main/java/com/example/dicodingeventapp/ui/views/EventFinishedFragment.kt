package com.example.dicodingeventapp.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.databinding.FragmentEventFinishedBinding
import com.example.dicodingeventapp.ui.adapter.EventListAdapter
import com.example.dicodingeventapp.ui.viewmodel.EventViewModel
import com.example.dicodingeventapp.ui.viewmodel.ViewModelFactory
import com.example.dicodingeventapp.utils.Result

class EventFinishedFragment : Fragment() {

    private var _binding: FragmentEventFinishedBinding? = null

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventFinishedBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }

        val eventAdapter = EventListAdapter { eventItem ->
            val bundle = Bundle().apply {
                putInt("event_id", eventItem.eventId.toInt())
                putParcelable("book_item", eventItem)

            }
            findNavController().navigate(
                R.id.action_navigation_finished_to_eventDetailFragment,
                bundle
            )
        }

        viewModel.fetchEvent(0).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val eventData = result.data
                        eventAdapter.submitList(eventData)
                    }

                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE

                    }
                }
            }
        }

        binding?.rvFinishedEvent?.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = eventAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}