package com.example.dicodingeventapp.ui.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.data.response.Event
import com.example.dicodingeventapp.databinding.FragmentEventDetailBinding

class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null

    private val binding get() = _binding!!

    private val eventDetailViewModel: EventDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val eventID = arguments?.getInt("event_id", 8948)

        if (eventID != null && eventID != -1) {
            eventDetailViewModel.fetchEventDetail(eventID)
        } else {
            Log.e("EventDetailFragment", "Event ID is Null")
        }

        eventDetailViewModel.eventDetail.observe(viewLifecycleOwner) { eventDetailData ->
            setEventDetailData(eventDetailData)
        }

        eventDetailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setEventDetailData(eventDetailData: Event) {
        eventDetailData.let {
            binding.tvName.text = it.name
            binding.tvOwnerName.text = it.ownerName
            binding.tvQuota.text = it.quota.toString()
            binding.tvTime.text = it.beginTime
            binding.tvDescription.text = HtmlCompat.fromHtml(
                it.description ?: "",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            Glide.with(binding.root).load(it.imageLogo).into(binding.imgMediaCover)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
