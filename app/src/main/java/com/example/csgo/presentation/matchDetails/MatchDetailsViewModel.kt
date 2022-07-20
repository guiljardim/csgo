package com.example.csgo.presentation.matchDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.csgo.domain.model.Match.Opponent
import com.example.csgo.domain.useCase.GetOpponentsDetailsUseCase
import com.example.csgo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MatchDetailsViewModel @Inject constructor(private val getOpponentsDetailsUseCase: GetOpponentsDetailsUseCase) :
    ViewModel() {

    val opponents = MutableLiveData<Resource<List<Opponent>>>()

    fun getOpponents(id: Int) {
        with(viewModelScope) {
            launch {
                getOpponentsDetailsUseCase.invoke(id)
                    .onStart {
                        opponents.postValue(Resource.loading())
                    }.catch {
                        opponents.postValue(Resource.error())
                    }.collect {
                        opponents.postValue(Resource.success(it))
                    }
            }
        }

    }

}