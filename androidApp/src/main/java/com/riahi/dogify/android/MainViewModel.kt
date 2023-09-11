package com.riahi.dogify.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riahi.dogify.model.Breed
import com.riahi.dogify.usecase.FetchBreedsUseCase
import com.riahi.dogify.usecase.GetBreedsUseCase
import com.riahi.dogify.usecase.ToggleFavouriteStateUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import repository.BreedsRepository

class MainViewModel(
    breedsRepository: BreedsRepository,
    private val getBreeds: GetBreedsUseCase,
    private val fetchBreeds: FetchBreedsUseCase,
    private val onToggleFavouriteState: ToggleFavouriteStateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State.LOADING)
    val state: StateFlow<State> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event> = _events

    private val _shouldFilterFavorites = MutableStateFlow(false)
    val shouldFilterFavorites: StateFlow<Boolean> = _shouldFilterFavorites

    val breeds = breedsRepository.breeds.combine(shouldFilterFavorites) { breeds, shouldFilterFavorites ->
        if (shouldFilterFavorites) {
            breeds.filter { it.isFavourite }
        } else {
            breeds
        }.also {
            _state.value = if (it.isEmpty()) State.EMPTY else State.NORMAL
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    init {
        loadData()
    }

    private fun loadData(isForceRefresh: Boolean = false) {
        val getData: suspend () -> List<Breed> = {
            if (isForceRefresh) fetchBreeds.invoke() else getBreeds.invoke()
        }
        if (isForceRefresh) {
            _isRefreshing.value = true
        } else {
            _state.value = State.LOADING
        }

        viewModelScope.launch {
            _state.value = try {
                getData()
                State.NORMAL
            } catch (e: Exception) {
                State.ERROR
            }
            _isRefreshing.value = false
        }
    }

    fun refresh() {
        loadData(true)
    }

    fun onToggleFavouriteFilter() {
        _shouldFilterFavorites.value = !shouldFilterFavorites.value
    }

    fun onFavouriteTapped(breed: Breed) {
        viewModelScope.launch {
            try {
                onToggleFavouriteState(breed)
            } catch (e: Exception) {
                _events.emit(Event.Error)
            }
        }
    }


    enum class State {
        LOADING,
        NORMAL,
        EMPTY,
        ERROR
    }

    enum class Event {
        Error
    }
}

