package com.nyang.dayFlower.presentation.feature.flowerDetail

sealed class FlowerDetailEvent {
    object SearchPrevFlower : FlowerDetailEvent()
    object SearchNextFlower : FlowerDetailEvent()
    object ShowDatePicker: FlowerDetailEvent()
    data class SearchFlower(val month : Int, val day : Int): FlowerDetailEvent()
}