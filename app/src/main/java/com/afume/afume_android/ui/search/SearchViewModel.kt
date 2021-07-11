package com.afume.afume_android.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.data.repository.SearchRepository
import com.afume.afume_android.data.vo.request.RequestSearch
import com.afume.afume_android.data.vo.request.SendFilter
import com.afume.afume_android.data.vo.response.PerfumeInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SearchViewModel : ViewModel() {
    private val searchRepository = SearchRepository()
    private val compositeDisposable = CompositeDisposable()

    var filter=MutableLiveData<SendFilter>()
    val perfumeList = MutableLiveData(mutableListOf<PerfumeInfo>())
    val perfumeLike: MutableLiveData<Boolean> = MutableLiveData()

    init {
        postSearchResultPerfume()
    }

    fun postSearchResultPerfume() {
        try{
            val requestSearch = RequestSearch("", mutableListOf<Int>(), mutableListOf<Int>(), mutableListOf<Int>())
            val tempFilterList = filter.value?.filterInfoPList
            tempFilterList?.forEach {
                when (it.type) {
                    1 -> {
                        if(it.idx!=-1) requestSearch.ingredientList?.add(it.idx)
                        else{
                            filter.value?.filterSeriesPMap?.get(it.name)?.forEach {
                                requestSearch.ingredientList?.add(it.ingredientIdx)
                            }
                        }
                    }
                    2 -> requestSearch.brandList?.add(it.idx)
                    3 -> requestSearch.keywordList?.add(it.idx)
                    4 -> requestSearch.searchText = it.name
                }
            }
            filter.value?.filterInfoPList =tempFilterList
            Log.e("Request Search ", requestSearch.toString())

            viewModelScope.launch {
                perfumeList.value = searchRepository.postResultPerfume(AfumeApplication.prefManager.accessToken,requestSearch)
                Log.e("search result", perfumeList.value.toString())
            }
        }catch (e: HttpException){

        }


    }

    fun postPerfumeLike(perfumeIdx: Int) {
        compositeDisposable.add(
            searchRepository.postPerfumeLike(AfumeApplication.prefManager.accessToken, perfumeIdx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("postPerfumeLike", it.toString())
                    Log.e("postPerfumeLike", it.toString())
                    perfumeLike.postValue(it.data)
                    clickHeartPerfumeList(perfumeIdx,it.data)
                }) {
                    Log.d("postPerfumeLike error", it.toString())
//                    Toast.makeText(context, "서버 점검 중입니다.", Toast.LENGTH_SHORT).show()
                })
    }

    private fun clickHeartPerfumeList(perfumeIdx: Int, isSelected:Boolean){
        val tempList = perfumeList.value
        tempList?.forEach { if(it.perfumeIdx==perfumeIdx) it.isLiked= isSelected}
        perfumeList.value=tempList
    }


    companion object {
        private var instance: SearchViewModel? = null
        fun getInstance() = instance ?: synchronized(SearchViewModel::class.java) {
            instance ?: SearchViewModel().also { instance = it }
        }
    }

}