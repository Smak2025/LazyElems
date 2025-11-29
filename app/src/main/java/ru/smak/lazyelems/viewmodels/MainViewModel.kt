package ru.smak.lazyelems.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.smak.lazyelems.db.Card
import ru.smak.lazyelems.db.CardColor
import ru.smak.lazyelems.db.CardDatabase
import ru.smak.lazyelems.db.CardInfo

class MainViewModel(app: Application) : AndroidViewModel(app) {

    val values = mutableStateListOf<CardInfo>()
    var showDialog by mutableStateOf(false)

    var page: Pages by mutableStateOf(Pages.MAIN)

    init {
        CardDatabase.initDb(getApplication())
        viewModelScope.launch(Dispatchers.IO){
            CardDatabase.colorDao?.apply {
                get(1) ?: add(CardColor(1, Color.Unspecified))
                get(2) ?: add(CardColor(2, Color.Red))
                get(3) ?: add(CardColor(3, Color.Yellow))
                get(4) ?: add(CardColor(4, Color.Green))
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            CardDatabase.cardsDao?.getAllCardsInfo()?.collect { it ->
                values.apply {
                    clear()
                    addAll(it)
                }
            }
        }
    }

    fun toList(){
        page = Pages.LIST
    }

    fun back(){
        page = Pages.MAIN
    }

    fun addValue(title: String, text: String){
        viewModelScope.launch(Dispatchers.IO) {
            CardDatabase.cardsDao?.insert(
                Card(title = title, text = text, colorId = (1..4).random())
            )
        }
    }
}