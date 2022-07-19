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

    private var listOfMatches: MutableList<Match> = mutableListOf()


    val matches = MutableLiveData<Pair<Resource<List<Match>>, Boolean>>()

    fun getMatches(page: Int, isFromLoadMore: Boolean) {
        viewModelScope.launch {
            getMatchesUseCase(page)
                .map { resource ->
                    when (resource.status) {
                        Resource.Status.LOADING -> {
                            matches.postValue(Pair(Resource.loading(), isFromLoadMore))
                        }
                        Resource.Status.SUCCESS -> {
                            matches.postValue(Pair(Resource.success(resource.data), isFromLoadMore))
                        }
                        else -> {
                            matches.postValue(Pair(Resource.error(resource.error), isFromLoadMore))
                        }
                    }
                }
                .stateIn(viewModelScope)
        }
    }

    fun onScrollEnded(page: Int) {
        getMatches(page, true)
    }
}


