package org.sample.sampleTest.ui.space

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SpaceViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is space Fragment"
    }
    val text: LiveData<String> = _text
}