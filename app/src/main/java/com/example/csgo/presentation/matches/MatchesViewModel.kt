package com.example.csgo.presentation.matches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.csgo.domain.model.Match
import com.example.csgo.domain.useCase.GetMatchesUseCase
import com.example.csgo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MatchesViewModel @Inject constructor(private val getMatchesUseCase: GetMatchesUseCase) :
    ViewModel() {

    val matches = MutableLiveData<Resource<List<Match>>>()

    fun getMatches() {
        viewModelScope.launch {
            getMatchesUseCase()
                .map { resource ->
                    when (resource.status) {
                        Resource.Status.LOADING -> {
                            matches.postValue(Resource.loading())
                        }
                        Resource.Status.SUCCESS -> {
                            matches.postValue(Resource.success(resource.data))
                        }
                        else -> {
                            matches.postValue(Resource.error(resource.error))
                        }
                    }
                }
                .stateIn(viewModelScope)
        }
    }
}


