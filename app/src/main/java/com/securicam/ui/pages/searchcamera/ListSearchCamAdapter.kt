package com.securicam.ui.pages.searchcamera

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.securicam.data.responses.ListCamera
import com.securicam.databinding.ItemRowDevicesBinding
import com.securicam.ui.pages.requestconnection.RequestConnectToCamActivity

class ListSearchCamAdapter(private val listCamera: List<ListCamera>): RecyclerView.Adapter<ListSearchCamAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):ListViewHolder {
        val binding = ItemRowDevicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listCamera[position])
    }

    override fun getItemCount(): Int = listCamera.size

    class ListViewHolder(private val binding: ItemRowDevicesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataConnection: ListCamera) {
            binding.tvCamDevice.text = dataConnection.username
            dataConnection.email
            dataConnection.id
            itemView.setOnClickListener {
                val goToRequestConnectToCamActivity =
                    Intent(itemView.context, RequestConnectToCamActivity::class.java)
                goToRequestConnectToCamActivity.putExtra(
                    RequestConnectToCamActivity.EXTRA_DATA_SEND_PAIR,
                    dataConnection
                )

                itemView.context.startActivity(goToRequestConnectToCamActivity)
            }
        }
    }
}