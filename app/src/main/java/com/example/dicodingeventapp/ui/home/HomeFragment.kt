package com.example.dicodingeventapp.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.data.local.EventEntity
import com.example.dicodingeventapp.databinding.FragmentHomeBinding
import com.example.dicodingeventapp.ui.EventListAdapter
import com.example.dicodingeventapp.ui.EventViewModel
import com.example.dicodingeventapp.ui.SearchAdapter
import com.example.dicodingeventapp.ui.ViewModelFactory
import com.example.dicodingeventapp.utils.Result
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }

        val eventActiveAdapter = EventListAdapter(EventListAdapter.SIZE_SMALL) { eventItem ->
            navigateToEventDetail(eventItem.eventId.toInt(), eventItem)
        }
        val eventFinishAdapter = EventListAdapter(EventListAdapter.SIZE_SMALL) { eventItem ->
            navigateToEventDetail(eventItem.eventId.toInt(), eventItem)
        }

        val searchAdapter = SearchAdapter { eventItem ->
            navigateToEventDetail(eventItem.id)
        }

        // Fetch Active Event
        fetchEvent(viewModel, eventActiveAdapter, binding?.pbUpcomingEvent, 1)
        // Fetch Finished Event
        fetchEvent(viewModel, eventFinishAdapter, binding?.pbFinishEvent, 0)

        binding?.rvUpcomingEvent?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = eventActiveAdapter
        }
        binding?.rvFinishedEvent?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = eventFinishAdapter
        }
        binding?.rvSearchResult?.layoutManager = LinearLayoutManager(context)


        binding?.rvSearchResult?.adapter = searchAdapter


        binding?.let { binding ->
            with(binding) {
                searchView.setupWithSearchBar(searchBar)
                searchView.editText.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val query = searchView.text.toString()
                        searchBar.setText(query)
                        searchEvent(viewModel, query, searchAdapter)
                        viewModel.searchEvent(query)
                        rvSearchResult.visibility = View.VISIBLE
                        Snackbar.make(view, query, Toast.LENGTH_SHORT).show()
                        true
                    } else {
                        false
                    }
                }
            }
        }
    }

    private fun searchEvent(viewModel: EventViewModel, query: String, adapter: SearchAdapter) {
        viewModel.searchEvent(query).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.pbSearch?.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding?.pbSearch?.visibility = View.GONE
                        val eventData = result.data
                        val eventDataList = eventData.listEvents
                        adapter.submitList(eventDataList)
                    }

                    is Result.Error -> {
                        binding?.pbSearch?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Error occurs:" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
        }
    }

    private fun fetchEvent(
        viewModel: EventViewModel,
        adapter: EventListAdapter,
        progressBar: ProgressBar?,
        isActive: Int,
    ) {
        viewModel.fetchEvent(isActive).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        progressBar?.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        progressBar?.visibility = View.GONE
                        val eventData = result.data.take(5)
                        adapter.submitList(eventData)
                    }

                    is Result.Error -> {
                        progressBar?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Error occurs:" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
        }
    }

    private fun navigateToEventDetail(eventId: Int, eventItem: EventEntity) {
        val bundle = Bundle().apply {
            putInt("event_id", eventId)
            putParcelable("event_item", eventItem)
        }
        findNavController().navigate(R.id.action_navigation_home_to_eventDetailFragment, bundle)
    }

    private fun navigateToEventDetail(eventId: Int) {
        val bundle = Bundle().apply {
            putInt("event_id", eventId)
        }
        findNavController().navigate(R.id.action_navigation_home_to_eventDetailFragment, bundle)
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.pbSearch?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}







