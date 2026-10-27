package com.example.httpreqdemov2

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.httpreqdemov2.data.FunFact
import com.example.httpreqdemov2.data.FunFactRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FunFactViewModel(private val repository: FunFactRepository) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // Re-queries Room every time the search text changes
    @OptIn(ExperimentalCoroutinesApi::class)
    val facts: StateFlow<List<FunFact>> = _query
        .flatMapLatest { repository.searchFacts(it.trim()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun fetchNewFact() {
        if (_isLoading.value) return
        viewModelScope.launch {
            _isLoading.value = true
            _message.value = null
            try {
                val inserted = repository.fetchAndSaveFunFact()
                if (!inserted) _message.value = "You already have that fact saved. Try again!"
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("FunFact", "Error fetching fact", e)
                _message.value = "Couldn't fetch a fact. Check your connection."
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class FunFactViewModelFactory(
    private val repository: FunFactRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FunFactViewModel::class.java)) {
            return FunFactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
