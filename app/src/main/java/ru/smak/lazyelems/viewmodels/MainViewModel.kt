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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.smak.lazyelems.db.internal.Card
import ru.smak.lazyelems.db.internal.CardColor
import ru.smak.lazyelems.db.CardDatabase
import ru.smak.lazyelems.db.internal.CardInfo
import ru.smak.lazyelems.db.CardInfoRepositoryImpl

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val cardInfoRepository = CardInfoRepositoryImpl(
        CardDatabase.getInstance(getApplication())
    )

    private val _cards = MutableStateFlow<List<CardInfo>>(listOf())
    val cards: StateFlow<List<CardInfo>> = _cards.asStateFlow()

    var showDialog by mutableStateOf(false)
    var showColorMenu by mutableStateOf(false)
    val colors = mutableStateListOf(
        CardColor(1, Color.White),
        CardColor(2, Color.Red),
        CardColor(3, Color.Yellow),
        CardColor(4, Color.Green),
    )
    var _selectedColor by mutableStateOf(Color.Unspecified)

    var selectedColor: Color
        get() = _selectedColor
        set(value){
            _selectedColor = value
            viewModelScope.launch {
                cardInfoRepository.getAllCardsByColor(value)?.collectLatest { newCards ->
                    _cards.value = newCards.cards.map{ CardInfo(it, newCards.cardColor) }
                }
            }

        }

    var page: Pages by mutableStateOf(Pages.MAIN)

    init {
        viewModelScope.launch{
            cardInfoRepository.apply {
                colors.forEach {
                    this.getColorById(it.id) ?: addColor(CardColor(it.id, it.color))
                }
            }
            cardInfoRepository.getAllCardsInfo().collectLatest { initialCards ->
                _cards.value = initialCards
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
        viewModelScope.launch {
            cardInfoRepository.addCard(
                Card(title = title, text = text, colorId = (1..4).random())
            )
        }
    }
}