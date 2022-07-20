package com.example.csgo.presentation.matches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.csgo.domain.model.Match
import com.example.csgo.domain.useCase.GetMatchesUseCase
import com.example.csgo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MatchesViewModel @Inject constructor(private val getMatchesUseCase: GetMatchesUseCase) :
    ViewModel() {

    val matches = MutableLiveData<Pair<Resource<List<Match>>, Boolean>>()

    fun getMatches(page: Int, isFromLoadMore: Boolean) {
        with(viewModelScope) {
            launch {
                getMatchesUseCase.invoke(page)
                    .onStart {
                        matches.postValue(Pair(Resource.loading(), isFromLoadMore))
                    }.catch {
                        matches.postValue(Pair(Resource.error(), isFromLoadMore))
                    }.collect {
                        matches.postValue(Pair(Resource.success(it), isFromLoadMore))
                    }
            }
        }

    }

    fun onScrollEnded(page: Int) {
        getMatches(page, true)
    }
}


