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
import com.securicam.data.responses.LoginData
import com.securicam.data.responses.LoginResponse
import com.securicam.databinding.ItemRowDevicesBinding

class CameraAdapter (private val listUser: List<LoginData>) :
    RecyclerView.Adapter<CameraAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowDevicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (username) = listUser[position]
        holder.binding.tvCamDevice.text = username
        holder.itemView.setOnClickListener {
            val goToDetailActivity = Intent(holder.itemView.context, RequestConnectToCamActivity::class.java)
            goToDetailActivity.putExtra(RequestConnectToCamActivity.EXTRA_USERNAME, username)
            holder.itemView.context.startActivity(goToDetailActivity)
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(var binding: ItemRowDevicesBinding) : RecyclerView.ViewHolder(binding.root)
}


