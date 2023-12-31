package com.nyangzzi.dayFlower.presentation.feature.mainFlower


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.flowerDay.RequestFlowerDay
import com.nyangzzi.dayFlower.domain.model.flowerList.RequestFlowerList
import com.nyangzzi.dayFlower.domain.model.flowerMonth.RequestFlowerMonth
import com.nyangzzi.dayFlower.domain.usecase.GetFlowerDayUseCase
import com.nyangzzi.dayFlower.domain.usecase.GetFlowerListUseCase
import com.nyangzzi.dayFlower.domain.usecase.GetFlowerMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainFlowerViewModel @Inject constructor(
    private val flowerDetailUseCase: GetFlowerDayUseCase,
    private val flowerMonthUseCase: GetFlowerMonthUseCase,
    private val flowerListUseCase: GetFlowerListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainFlowerUiState())
    val uiState: StateFlow<MainFlowerUiState> = _uiState

    init {
        viewModelScope.launch {
            getFlowerDetail()
        }
    }

    fun onEvent(event: MainFlowerEvent) {
        viewModelScope.launch {
            when (event) {
                is MainFlowerEvent.SearchMainFlower -> {
                    setLocalDate(LocalDate.of(LocalDate.now().year, event.month, event.day))
                    getFlowerDetail()
                }

                is MainFlowerEvent.SearchNextDay -> {
                    setLocalDate(_uiState.value.localDate.plusDays(1))
                    getFlowerDetail()
                }

                is MainFlowerEvent.SearchPrevDay -> {
                    setLocalDate(_uiState.value.localDate.minusDays(1))
                    getFlowerDetail()
                }

                is MainFlowerEvent.SearchNextMonth -> {
                    setLocalDate(_uiState.value.localDate.plusMonths(1))
                    getFlowerMonth()
                }

                is MainFlowerEvent.SearchPrevMonth -> {
                    setLocalDate(_uiState.value.localDate.minusMonths(1))
                    getFlowerMonth()
                }

                is MainFlowerEvent.ShowDatePicker -> {
                    _uiState.update { it.copy(isDatePicker = true) }
                }

                is MainFlowerEvent.IsChangeView -> {

                    setLocalDate(
                        LocalDate.of(
                            _uiState.value.localDate.year,
                            event.month ?: _uiState.value.localDate.month.value,
                            event.day ?: _uiState.value.localDate.dayOfMonth
                        )
                    )
                    if (event.isCalendar) getFlowerMonth()
                    else getFlowerDetail()
                }

                is MainFlowerEvent.IsSearchDialog -> {
                    isSearchDialog(event.isShow)
                }

                is MainFlowerEvent.SearchFlowerList -> {
                    getFlowerList(event.type, event.word)
                }

                else -> {}
            }
        }
    }

    private fun isSearchDialog(isShow: Boolean) {
        _uiState.update {
            it.copy(isSearch = isShow, flowerList = emptyList())
        }
    }

    private fun setLocalDate(localDate: LocalDate) {
        _uiState.update {
            it.copy(localDate = localDate, isDatePicker = false)
        }
    }

    private suspend fun getFlowerDetail() {
        flowerDetailUseCase(
            requestFlowerDay = RequestFlowerDay(
                fMonth = _uiState.value.localDate.month.value,
                fDay = _uiState.value.localDate.dayOfMonth
            )
        ).collect { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.update {
                        it.copy(flowerDetail = result.data)
                    }
                }

                is ResultWrapper.Error -> {

                }

                else -> {}
            }
        }
    }

    private suspend fun getFlowerMonth() {
        flowerMonthUseCase(
            requestFlowerMonth = RequestFlowerMonth(
                fMonth = _uiState.value.localDate.month.value
            )
        ).collect { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.update {
                        it.copy(flowerMonth = result.data)
                    }
                }

                is ResultWrapper.Error -> {

                }

                else -> {}
            }
        }
    }

    private suspend fun getFlowerList(type: Int, word: String) {
        flowerListUseCase(
            requestFlowerList = RequestFlowerList(
                searchType = type,
                searchWord = word
            )
        ).collect { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.update {
                        it.copy(flowerList = result.data)
                    }
                }

                is ResultWrapper.Error -> {

                }

                else -> {}
            }
        }
    }

}