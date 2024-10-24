package com.example.dicodingeventapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.data.local.EventEntity
import com.example.dicodingeventapp.data.response.ListEventsDetailItem
import com.example.dicodingeventapp.databinding.FragmentEventDetailBinding
import com.example.dicodingeventapp.ui.EventViewModel
import com.example.dicodingeventapp.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Locale

class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null

    private val binding get() = _binding

    private lateinit var eventItem: EventEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventId = arguments?.getInt("event_id") ?: -1
        val passedEventData: EventEntity = arguments?.getParcelable("event_item")!!

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }
        if (eventId != -1) {
            viewModel.fetchEventDetail(eventId)
        }
        viewModel.getFavoriteEvent().observe(viewLifecycleOwner) {
            if (it != null) {
                eventItem = it
            }
        }

    viewModel.eventDetail.observe(viewLifecycleOwner) { eventDetailData ->
            eventDetailData.let {
                eventItem = EventEntity(
                    eventId = it.id.toString(),
                    name = it.name,
                    mediaCover = it.mediaCover,
                    isActive = passedEventData.isActive,
                    isFavorite = passedEventData.isFavorite
                )
                setEventDetailData(eventDetailData)
                toggleFavorite(eventItem.isFavorite)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.snackBar.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                Snackbar.make(
                    requireView(),
                    message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding?.btnFavorite?.setOnClickListener {
            eventItem.isFavorite = !eventItem.isFavorite  // Toggle the state
            viewModel.saveEvents(eventItem)
            toggleFavorite(eventItem.isFavorite)
        }

    }

    private fun setEventDetailData(eventDetail: ListEventsDetailItem) {
        binding?.apply {
            Glide.with(root).load(eventDetail.imageLogo).into(imgMediaCover)
            tvName.text = eventDetail.name
            tvOwnerName.text = eventDetail.ownerName
            val quota = (eventDetail.quota - eventDetail.registrants).toString()
            tvQuota.text = quota
            tvTime.text = formatEventTime(eventDetail.beginTime, eventDetail.endTime)
            tvDescription.text = HtmlCompat.fromHtml(
                eventDetail.description,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            btnLink.setOnClickListener {
                val url = eventDetail.link
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
    }


    private fun formatEventTime(beginTime: String, endTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        return try {
            val parsedBeginTime = inputFormat.parse(beginTime)
            val parsedEndTime = inputFormat.parse(endTime)

            val formattedDate = parsedBeginTime?.let { outputDateFormat.format(it) }
            val formattedBeginTime = parsedBeginTime?.let { outputTimeFormat.format(it) }
            val formattedEndTime = parsedEndTime?.let { outputTimeFormat.format(it) }

            "$formattedDate, $formattedBeginTime - $formattedEndTime"

        } catch (e: Exception) {
            Log.e("EventDetailFragment", "onError : ${e.message.toString()}")
            "Invalid input format"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun toggleFavorite(isFavorite: Boolean) {
        binding?.btnFavorite?.setImageResource(if (isFavorite) R.drawable.ic_favorite_24 else R.drawable.ic_favorite_border_24)
    }

}
