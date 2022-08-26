package com.scents.note.ui.survey

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scents.note.data.vo.response.KeywordInfo
import com.scents.note.data.vo.response.PerfumeInfo
import com.scents.note.data.vo.response.SeriesInfo
import com.scents.note.util.FlexboxRecyclerViewAdapter

object SurveyBinding {
    @JvmStatic
    @BindingAdapter("setPerfumeList")
    fun setPerfumeList(recyclerView: RecyclerView, list : MutableList<PerfumeInfo>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as CircleRecyclerViewAdapter){
            list?.let { setPerfumeData(list)
            Log.e("setperfumeList",perfumeData.toString())}
        }
    }
    @JvmStatic
    @BindingAdapter("setSeriesList")
    fun setSeriesList(recyclerView: RecyclerView, list : MutableList<SeriesInfo>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as CircleRecyclerViewAdapter){
            list?.let { setSeriesData(list)
                Log.e("setseriesList",perfumeData.toString())}
        }
    }

    @JvmStatic
    @BindingAdapter("setKeywordList")
    fun setKeywordList(recyclerView: RecyclerView, list : MutableList<KeywordInfo>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as FlexboxRecyclerViewAdapter){
            list?.let {
                setData(it)
//                submitList(list)
                Log.e("setkeywordList",data.toString())}
        }
    }

}