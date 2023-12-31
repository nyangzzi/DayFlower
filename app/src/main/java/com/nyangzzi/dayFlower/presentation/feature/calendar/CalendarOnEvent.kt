package com.nyangzzi.dayFlower.presentation.feature.calendar

sealed class CalendarOnEvent {
    object OnPrevMonth : CalendarOnEvent()
    object OnNextMonth : CalendarOnEvent()
    object OnSearchMonth : CalendarOnEvent()
    data class SetDetailDialog(val isShown: Boolean) : CalendarOnEvent()
}