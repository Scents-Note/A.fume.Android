package com.afume.afume_android.ui.note

import android.os.Bundle
import android.view.View
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityNoteBinding
import com.afume.afume_android.ui.detail.PerfumeDetailActivity
import com.afume.afume_android.util.NoteKeywordAdapter
import com.afume.afume_android.util.setSelectedSeekBarTxt
import com.afume.afume_android.util.startActivity
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    lateinit var noteKeywordAdapter : NoteKeywordAdapter
    private val noteViewModel : NoteViewModel by viewModels()
    private val keywordBottomSheetFragment = KeywordBottomSheetFragment()

    lateinit var txtLongevityList : List<TextView>
    lateinit var txtReverbList : List<TextView>
    lateinit var txtGenderList : List<TextView>
    lateinit var btnSeasonList : List<CheckedTextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_note)
        binding.lifecycleOwner = this
        binding.viewModel = noteViewModel

        setEnabledCompleteBtn()
        setComponentList()
        onSeekBarChangeListener()
//        onClickSeasonBtn()
        initKeywordList()
    }

    private fun setEnabledCompleteBtn(){
        noteViewModel.contentsTxt.observe(this, Observer {
            noteViewModel.checkCompleteBtn()
        })
        noteViewModel.rating.observe(this, Observer {
            noteViewModel.checkCompleteBtn()
        })
    }

    private fun initKeywordList(){
        val flexboxLayoutManager= FlexboxLayoutManager(this).apply {
            flexDirection= FlexDirection.ROW
            flexWrap= FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        noteKeywordAdapter = NoteKeywordAdapter(1) { index, add -> noteViewModel.addKeywordList(index, add) }

        binding.rvNoteKeywordList.apply {
            layoutManager=flexboxLayoutManager
            adapter=noteKeywordAdapter
        }
    }

    private fun setComponentList(){
        txtLongevityList = listOf(binding.txtNoteLongevityVeryWeak, binding.txtNoteLongevityWeak, binding.txtNoteLongevityUsual, binding.txtNoteLongevityStrong, binding.txtNoteLongevityVeryStrong)
        txtReverbList = listOf(binding.txtNoteReverbLight, binding.txtNoteReverbUsual, binding.txtNoteReverbHeavy)
        txtGenderList = listOf(binding.txtNoteGenderMan, binding.txtNoteGenderNeuter, binding.txtNoteGenderWoman)

        btnSeasonList = listOf(binding.btnNoteSeasonSpring, binding.btnNoteSeasonSummer, binding.btnNoteSeasonFall, binding.btnNoteSeasonWinder)
    }

    private fun onSeekBarChangeListener(){
        noteViewModel.longevityProgress.observe(this, Observer {
            binding.sbNoteLongevity.thumb = ContextCompat.getDrawable(this@NoteActivity, R.drawable.seekbar_note_thumb)
            setSelectedSeekBarTxtBold(txtLongevityList,it)
        })

        noteViewModel.reverbProgress.observe(this, Observer {
            binding.sbNoteReverb.thumb = ContextCompat.getDrawable(this@NoteActivity, R.drawable.seekbar_note_thumb)
            setSelectedSeekBarTxtBold(txtReverbList,it)
        })

        noteViewModel.genderProgress.observe(this, Observer {
            binding.sbNoteGender.thumb = ContextCompat.getDrawable(this@NoteActivity, R.drawable.seekbar_note_thumb)
            setSelectedSeekBarTxtBold(txtGenderList,it)
        })
//        binding.sbNoteLongevity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
//                setSelectedSeekBarTxtBold(txtReverbList,progress)
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//                binding.sbNoteLongevity.thumb = ContextCompat.getDrawable(this@NoteActivity, R.drawable.seekbar_note_thumb)
//                if(binding.sbNoteLongevity.progress==0){
//                    setSelectedSeekBarTxtBold(txtLongevityList,0)
//                }
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//            }
//
//        })
    }

    private fun setSelectedSeekBarTxtBold(list: List<TextView>, select: Int){
        for(i in list.indices){
           if(i==select){
               list[i].setSelectedSeekBarTxt(true)
           }else{
               list[i].setSelectedSeekBarTxt(false)
           }
        }
    }

    fun onClickBackBtn(view : View){
        finish()
    }

    fun onClickDetailBtn(view : View){
        this.startActivity(PerfumeDetailActivity::class.java)
    }

    fun onClickKeywordBtn(view : View){
        keywordBottomSheetFragment.show(supportFragmentManager, "noteKeywordDialog")
    }

    fun onClickCompleteBtn(view : View){
        finish()
    }
}