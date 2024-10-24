package com.example.dicodingeventapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.databinding.FragmentEventFavoriteBinding
import com.example.dicodingeventapp.ui.EventListAdapter
import com.example.dicodingeventapp.ui.EventViewModel
import com.example.dicodingeventapp.ui.ViewModelFactory

class EventFavoriteFragment : Fragment() {


    private var _binding: FragmentEventFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventFavoriteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }

        val eventAdapter = EventListAdapter { eventItem ->
            if (eventItem.isFavorite) {
                viewModel.deleteEvents(eventItem)
            } else {
                viewModel.saveEvents(eventItem)
            }

            val bundle = Bundle().apply {
                putInt("event_id", eventItem.eventId.toInt())
                putParcelable("event_item", eventItem)
            }
            findNavController().navigate(
                R.id.action_eventFavoriteFragment_to_eventDetailFragment,
                bundle
            )
        }

        viewModel.getListFavoriteEvent().observe(viewLifecycleOwner) { event ->
            eventAdapter.submitList(event)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding?.rvFavoriteEvent?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = eventAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}