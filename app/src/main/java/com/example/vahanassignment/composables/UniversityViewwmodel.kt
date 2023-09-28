package com.example.vahanassignment.composables

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vahanassignment.Database.UniversityItem
import com.example.vahanassignment.data.UniversityRepository
import com.example.vahanassignment.network.models.UniversityResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversityViewwmodel @Inject constructor(private val repository: UniversityRepository) :
    ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private var _Universitylist: MutableStateFlow<List<UniversityItem>> =
        MutableStateFlow(listOf<UniversityItem>())
    var UniversityList = _Universitylist.asStateFlow()

    init {
        viewModelScope.launch {
            repository.universitylist.collect{
                _Universitylist.value=it
            }

        }

        getUniversityList()
    }
    fun getUniversityList() {
        Log.d("inside viewmodel", "getUniversitylist: " + "new api call")
        // Execute the API call within a coroutine on the IO dispatcher
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUniversityList()
        }
    }
    }

