package com.afume.afume_android.ui.my.myperfume

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afume.afume_android.data.vo.RvMyPerfumeData
import com.afume.afume_android.databinding.RvItemMyMyperfumeBinding

class MyPerfumeRecyclerViewAdapter():RecyclerView.Adapter<MyPerfumeRecyclerViewHolder>(){
    var data = listOf<RvMyPerfumeData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPerfumeRecyclerViewHolder {
        val binding=RvItemMyMyperfumeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyPerfumeRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: MyPerfumeRecyclerViewHolder, position: Int) {
        holder.bind(data[position])
    }
}

class MyPerfumeRecyclerViewHolder(val binding:RvItemMyMyperfumeBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(data:RvMyPerfumeData){
        binding.rvMyPerfume=data
        binding.rvItemRbMyPerfume.setStar(data.starCount)
    }

}