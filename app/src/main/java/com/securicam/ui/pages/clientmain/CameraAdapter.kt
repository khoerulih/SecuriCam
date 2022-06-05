package com.securicam.ui.pages.clientmain

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.securicam.R
import com.securicam.data.responses.ListConnection
import com.securicam.data.responses.LoginData
import com.securicam.data.responses.LoginResponse
import com.securicam.databinding.ItemRowDevicesBinding

class CameraAdapter(private val listConnection: List<ListConnection>): RecyclerView.Adapter<CameraAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):ListViewHolder {
        val binding = ItemRowDevicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listConnection[position])
    }

    override fun getItemCount(): Int = listConnection.size

    class ListViewHolder(private val binding: ItemRowDevicesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataConnection: ListConnection) {
            binding.tvCamDevice.text = dataConnection.connectionDetail.username
            //binding.tvItemEmail.text = dataConnection.connectionDetail.email
//            itemView.setOnClickListener {
//                val goToDetailActivity = Intent(itemView.context, DetailStoryActivity::class.java)
//                goToDetailActivity.putExtra(DetailStoryActivity.EXTRA_STORY, dataStory)
//
//                val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    itemView.context as Activity,
//                    Pair(binding.ivItemThumbnail, "detail_thumbnail"),
//                    Pair(binding.tvItemName, "detail_name")
//                )
//                itemView.context.startActivity(goToDetailActivity, optionsCompat.toBundle())
//            }
        }
    }
}


