package com.ni032mas.sample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni032mas.sample.entities.Message

class MainViewModel : ViewModel() {

    private val messagesLiveData: MutableLiveData<MutableList<Message>> = MutableLiveData()

    fun getMessages(): LiveData<MutableList<Message>> = messagesLiveData

    fun setMessage(text: String) {
        val id = messagesLiveData.value?.size ?: 0
        val messages = (messagesLiveData.value ?: mutableListOf()).apply { add(0, Message(id, text, "13:13")) }
        messagesLiveData.postValue(messages)
    }
}