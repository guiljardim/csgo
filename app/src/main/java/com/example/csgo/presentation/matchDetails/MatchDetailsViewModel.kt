package com.example.csgo.presentation.matchDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.csgo.domain.model.Match.Opponent
import com.example.csgo.domain.useCase.GetOpponentsDetailsUseCase
import com.example.csgo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MatchDetailsViewModel @Inject constructor(private val getOpponentsDetailsUseCase: GetOpponentsDetailsUseCase) :
    ViewModel() {

    val opponents = MutableLiveData<Resource<List<Opponent>>>()

    fun getOpponents(id: Int) {
        viewModelScope.launch {
            getOpponentsDetailsUseCase(id)
                .map { resource ->
                    when (resource.status) {
                        Resource.Status.LOADING -> {
                            opponents.postValue(Resource.loading())
                        }
                        Resource.Status.SUCCESS -> {
                            opponents.postValue(Resource.success(resource.data))
                        }
                        else -> {
                            opponents.postValue(Resource.error(resource.error))
                        }
                    }
                }
                .stateIn(viewModelScope)
        }
    }

}