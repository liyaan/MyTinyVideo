package com.liyaan.mytinyvideo.ui.publish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PublishViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is publish Fragment"
    }
    val text: LiveData<String> = _text
}