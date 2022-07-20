package com.example.csgo.presentation.matches

import androidx.lifecycle.LiveData
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

    private val _matches = MutableLiveData<Pair<Resource<List<Match>>, Boolean>>()

    val matches: LiveData<Pair<Resource<List<Match>>, Boolean>>
        get() = _matches

    fun getMatches(page: Int, isFromLoadMore: Boolean) {
        with(viewModelScope) {
            launch {
                getMatchesUseCase.invoke(page)
                    .onStart {
                        _matches.postValue(Pair(Resource.loading(), isFromLoadMore))
                    }.catch {
                        _matches.postValue(Pair(Resource.error(), isFromLoadMore))
                    }.collect {
                        _matches.postValue(Pair(Resource.success(it), isFromLoadMore))
                    }
            }
        }

    }

    fun onScrollEnded(page: Int) {
        getMatches(page, true)
    }
}


