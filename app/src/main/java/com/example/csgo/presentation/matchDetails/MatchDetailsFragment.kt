package com.example.csgo.presentation.matchDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.csgo.databinding.FragmentMatchDetailsBinding
import com.example.csgo.domain.model.Match
import com.example.csgo.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MatchDetailsFragment : Fragment() {

    companion object {
        const val ID_DETAILS_RESULT = "ID_DETAILS_RESULT"
        const val LEAGUE_SERIE_DETAILS_RESULT = "LEAGUE_SERIE_DETAILS_RESULT"
        const val DATE_RESULT = "DATE_RESULT"
    }

    private val viewModel: MatchDetailsViewModel by viewModels()
    private lateinit var binding: FragmentMatchDetailsBinding
    private lateinit var leagueAndSerieTitle: String
    private lateinit var date: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(ID_DETAILS_RESULT)
        leagueAndSerieTitle = arguments?.getString(LEAGUE_SERIE_DETAILS_RESULT).toString()
        date = arguments?.getString(DATE_RESULT).toString()
        viewModel.getOpponents(
            arguments?.getInt(ID_DETAILS_RESULT) ?: 0
        )
        observer()
    }


    private fun observer() {
        viewModel.opponents.observe(viewLifecycleOwner) {
            when (it.status) {

                Resource.Status.LOADING -> {
                }

                Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> {
                    Log.d("GUILHERMEERROR", it.error.toString())
                }

                Resource.Status.SUCCESS -> {
                    initViews(it.data)
                }
            }
        }
    }

    private fun initViews(data: List<Match.Opponent>?) {
        binding.toolbarTitleVolume.text = leagueAndSerieTitle
        binding.textViewDate.text = date
        data?.first()?.image_url?.let { binding.iconImageViewFirstOpponentDetails.image(it) }
        data?.last()?.image_url?.let { binding.iconImageViewSecondOpponentDetails.image(it) }
        binding.textViewFirstOpponentName.text = data?.first()?.name
        binding.textViewSecondOpponentName.text = data?.last()?.name
        binding.recyclerViewPlayers.adapter = MatchDetailsAdapter(
            data?.first()?.player?.mapToShow(
                data.last().player
            )
        )
        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(context)

    }

}

fun List<Match.Player>.mapToShow(player: List<Match.Player>?): List<PlayerToShow> {
    val playerToShow: MutableList<PlayerToShow> = mutableListOf()
    for ((i, v) in this.withIndex()) {
        playerToShow.add(
            PlayerToShow(
                v.name,
                v.nickname,
                v.image_url,
                player?.get(i)?.name,
                player?.get(i)?.nickname,
                player?.get(i)?.image_url
            )
        )
    }
    return playerToShow
}




