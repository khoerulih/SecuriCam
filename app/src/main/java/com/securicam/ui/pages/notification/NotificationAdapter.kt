package com.securicam.ui.pages.notification

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.securicam.data.responses.ListNotification
import com.securicam.databinding.ItemRowNotificationBinding
import com.securicam.ui.pages.detailnotification.DetailClientNotification

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
            binding.imgDetected
            binding.tvDetected.text = dataNotification.data
            binding.tvDesc.text = dataNotification.description
            itemView.setOnClickListener {
/*                val goToDetailNotificationActivity =
                    Intent(itemView.context, DetailClientNotification::class.java)
                goToDetailNotificationActivity.putExtra(
                    DetailClientNotification.EXTRA_DATA_NOTIFICATION,
                    dataNotification
                )

                itemView.context.startActivity(goToDetailNotificationActivity)*/

            }
        }
    }
}
