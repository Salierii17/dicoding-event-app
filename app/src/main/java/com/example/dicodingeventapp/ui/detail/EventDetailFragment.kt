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
import com.example.dicodingeventapp.data.response.ListEventsDetailItem
import com.example.dicodingeventapp.databinding.FragmentEventDetailBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Locale

class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null

    private val binding get() = _binding

    private val eventDetailViewModel: EventDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailBinding.inflate(layoutInflater, container, false)
        val view = binding?.root ?: View(requireContext())
        return view
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val eventID = arguments?.getInt("event_id", 0)

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
        eventDetailViewModel.snackBar.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                Snackbar.make(
                    requireView(),
                    message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun setEventDetailData(eventDetailData: ListEventsDetailItem) {
        eventDetailData.let { event ->
            binding?.let { binding ->
                with(binding) {
                    Glide.with(root).load(event.imageLogo).into(imgMediaCover)
                    tvName.text = event.name
                    tvOwnerName.text = event.ownerName
                    val quota = (event.quota - event.registrants).toString()
                    tvQuota.text = quota
                    tvTime.text = formatEventTime(event.beginTime, event.endTime)
                    tvDescription.text = HtmlCompat.fromHtml(
                        event.description,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    btnLink.setOnClickListener {
                        val url = event.link
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    }
                }
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

}
