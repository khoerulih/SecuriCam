package com.securicam.ui.pages.requestpair

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.securicam.data.responses.ListRequestPair
import com.securicam.databinding.ItemRowRequestPairBinding
import com.securicam.ui.pages.detailrequestpair.DetailRequestPairActivity

class ListRequestPairAdapter(private val listRequestPair: List<ListRequestPair>) :
    RecyclerView.Adapter<ListRequestPairAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemRowRequestPairBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listRequestPair[position])
    }

    override fun getItemCount(): Int = listRequestPair.size

    class ListViewHolder(private val binding: ItemRowRequestPairBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataRequest: ListRequestPair) {
            binding.tvItemUsername.text = dataRequest.clientDetail.username
            binding.tvItemEmail.text = dataRequest.clientDetail.email
            itemView.setOnClickListener {
                val goToDetailRequestPairActivity =
                    Intent(itemView.context, DetailRequestPairActivity::class.java)
                goToDetailRequestPairActivity.putExtra(
                    DetailRequestPairActivity.EXTRA_DATA_REQUEST_PAIR,
                    dataRequest
                )

                itemView.context.startActivity(goToDetailRequestPairActivity)
            }
        }
    }
}