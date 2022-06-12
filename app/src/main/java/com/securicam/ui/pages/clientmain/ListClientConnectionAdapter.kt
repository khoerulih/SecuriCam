package com.securicam.ui.pages.clientmain

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.securicam.data.responses.ListConnection
import com.securicam.databinding.ItemRowConnectionBinding
import com.securicam.databinding.ItemRowDevicesBinding
import com.securicam.ui.pages.detailconection.DetailConnectionActivity

class ListClientConnectionAdapter(private val listConnection: List<ListConnection>) :
    RecyclerView.Adapter<ListClientConnectionAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemRowDevicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listConnection[position])
    }

    override fun getItemCount(): Int = listConnection.size

    class ListViewHolder(private val binding: ItemRowDevicesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataConnection: ListConnection) {
            binding.tvCamDevice.text = dataConnection.connectionDetail.username
            itemView.setOnClickListener {
//                val goToDetailConnectionActivity =
//                    Intent(itemView.context, DetailConnectionActivity::class.java)
//                goToDetailConnectionActivity.putExtra(
//                    DetailConnectionActivity.EXTRA_DATA_CONNECTION,
//                    dataConnection
//                )
//
//                itemView.context.startActivity(goToDetailConnectionActivity)
            }
        }
    }
}