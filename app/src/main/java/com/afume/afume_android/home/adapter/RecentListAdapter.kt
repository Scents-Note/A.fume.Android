package com.afume.afume_android.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.HomePerfumeListData
import com.afume.afume_android.databinding.RvItemHomeRecentBinding

class RecentListAdapter(private val context: Context) : RecyclerView.Adapter<RecentListViewHolder>() {
    var data = mutableListOf<HomePerfumeListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentListViewHolder {
        val binding : RvItemHomeRecentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_recent,
            parent,
            false
        )

        return RecentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size
}

class RecentListViewHolder(val binding: RvItemHomeRecentBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: HomePerfumeListData){
        binding.item = item
        binding.executePendingBindings()
    }
}