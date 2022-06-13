package com.securicam.ui.pages.notification

import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.securicam.data.responses.ListNotification
import com.securicam.databinding.ItemRowNotificationBinding

class NotificationAdapter(private val listNotification: List<ListNotification>) :
    RecyclerView.Adapter<NotificationAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemRowNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listNotification[position])
    }

    override fun getItemCount(): Int = listNotification.size

    class ListViewHolder(private val binding: ItemRowNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataNotification: ListNotification) {
            binding.tvMessage.text = dataNotification.message
            if(!dataNotification.data.isNullOrEmpty()){
                val objectDetected = dataNotification.data.map {
                    "${it.label} "
                }
                binding.tvData.text = objectDetected.toString()
            } else {
                binding.tvData.visibility = View.GONE
            }
        }
    }
}
