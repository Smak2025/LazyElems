package ru.smak.lazyelems.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val values = mutableStateListOf<Pair<Int, String>>()

    var page: Pages by mutableStateOf(Pages.MAIN)
    var showDialog by mutableStateOf(false)

    fun toList(){
        page = Pages.LIST
    }

    fun back(){
        page = Pages.MAIN
    }

    fun addValue(){
        if (values.isEmpty()) values.add(1 to "text")
        else values.add((values.last().first + 1) to "text")
    }
}