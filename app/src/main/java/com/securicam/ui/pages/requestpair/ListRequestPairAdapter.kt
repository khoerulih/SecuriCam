package com.securicam.ui.pages.requestpair

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.securicam.data.responses.ListRequestPair
import com.securicam.databinding.ItemRowRequestPairBinding

class ListRequestPairAdapter(private val listRequestPair: List<ListRequestPair>): RecyclerView.Adapter<ListRequestPairAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):ListViewHolder {
        val binding = ItemRowRequestPairBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listRequestPair[position])
    }

    override fun getItemCount(): Int = listRequestPair.size

    class ListViewHolder(private val binding: ItemRowRequestPairBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataRequest: ListRequestPair) {
            binding.tvItemUsername.text = dataRequest.clientDetail.username
            binding.tvItemEmail.text = dataRequest.clientDetail.email
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