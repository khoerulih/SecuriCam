package com.securicam.ui.pages.cameramain

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.securicam.data.responses.ListConnection
import com.securicam.databinding.ItemRowConnectionBinding
import com.securicam.ui.pages.detailconection.DetailConnectionActivity

class ListConnectionAdapter(private val listConnection: List<ListConnection>) :
    RecyclerView.Adapter<ListConnectionAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemRowConnectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listConnection[position])
    }

    override fun getItemCount(): Int = listConnection.size

    class ListViewHolder(private val binding: ItemRowConnectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataConnection: ListConnection) {
            binding.tvItemUsername.text = dataConnection.connectionDetail.username
            binding.tvItemEmail.text = dataConnection.connectionDetail.email
            itemView.setOnClickListener {
                val goToDetailConnectionActivity =
                    Intent(itemView.context, DetailConnectionActivity::class.java)
                goToDetailConnectionActivity.putExtra(
                    DetailConnectionActivity.EXTRA_DATA_CONNECTION,
                    dataConnection
                )

                itemView.context.startActivity(goToDetailConnectionActivity)
            }
        }
    }
}