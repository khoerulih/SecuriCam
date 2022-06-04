package com.securicam.ui.pages.cameramain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.securicam.data.responses.ListConnection
import com.securicam.databinding.ItemRowConnectionBinding

class ListConnectionAdapter(private val listConnection: List<ListConnection>): RecyclerView.Adapter<ListConnectionAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):ListViewHolder {
        val binding = ItemRowConnectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       holder.bind(listConnection[position])
    }

    override fun getItemCount(): Int = listConnection.size

    class ListViewHolder(private val binding: ItemRowConnectionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataConnection: ListConnection) {
            binding.tvItemUsername.text = dataConnection.connectionDetail.username
            binding.tvItemEmail.text = dataConnection.connectionDetail.email
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