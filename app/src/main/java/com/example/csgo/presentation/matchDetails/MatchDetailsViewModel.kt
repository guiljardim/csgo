package com.example.csgo.presentation.matchDetails

import androidx.lifecycle.LiveData
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

    private val _opponents = MutableLiveData<Resource<List<Opponent>>>()

    val opponents: LiveData<Resource<List<Opponent>>>
        get() = _opponents

    fun getOpponents(id: Int) {
        with(viewModelScope) {
            launch {
                getOpponentsDetailsUseCase.invoke(id)
                    .onStart {
                        _opponents.postValue(Resource.loading())
                    }.catch {
                        _opponents.postValue(Resource.error())
                    }.collect {
                        _opponents.postValue(Resource.success(it))
                    }
            }
        }

    }

}