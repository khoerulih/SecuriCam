package com.securicam.ui.pages.clientmain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.securicam.data.responses.ListCamera
import com.securicam.databinding.ItemRowDevicesBinding

class CameraSearchAdapter(private val listCamera: List<ListCamera>): RecyclerView.Adapter<CameraSearchAdapter.ListViewHolder>() {
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
            binding.imgDetected
            binding.tvCamDevice.text = dataConnection.username
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