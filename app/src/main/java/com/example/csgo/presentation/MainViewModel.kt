package com.example.csgo.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.csgo.data.datasource.OpponentsRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val opponentsRemoteDataSource: OpponentsRemoteDataSource
) : ViewModel() {

    fun get() {
        viewModelScope.launch {
            opponentsRemoteDataSource.invoke(
                "d8LG03ULv5vnWG1ay-OU8YbcLZeW1z9o9T1a_porwfhhr6llESI",
                642199
            )
        }

    }

}