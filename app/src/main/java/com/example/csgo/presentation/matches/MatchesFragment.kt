package com.example.csgo.presentation.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.csgo.R
import com.example.csgo.databinding.FragmentMatchesBinding
import com.example.csgo.presentation.matchDetails.MatchDetailsFragment.Companion.DATE_RESULT
import com.example.csgo.presentation.matchDetails.MatchDetailsFragment.Companion.ID_DETAILS_RESULT
import com.example.csgo.presentation.matchDetails.MatchDetailsFragment.Companion.LEAGUE_SERIE_DETAILS_RESULT
import com.example.csgo.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MatchesFragment : Fragment(), MatchesAdapter.OnItemClickListener {

    private val viewModel: MatchesViewModel by viewModels()
    private lateinit var binding: FragmentMatchesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchesBinding.inflate(inflater, container, false)
        viewModel.getMatches()
        observer()
        return binding.root
    }

    private fun observer() {
        viewModel.matches.observe(viewLifecycleOwner) {
            when (it.status) {

                Resource.Status.LOADING -> {
                    showProgress(true)
                }

                Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> {
                    showProgress(false)
                    Toast.makeText(
                        context,
                        getString(R.string.error_list_matches),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    viewModel.getMatches()

                }

                Resource.Status.SUCCESS -> {
                    showProgress(false)
                    binding.recyclerView.adapter =
                        context?.let { context -> MatchesAdapter(context, it.data, this) }
                    binding.recyclerView.layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    private fun showProgress(show: Boolean) {
        binding.progressCircular.isVisible = show
        binding.recyclerView.isVisible = !show
    }

    override fun onItemClickListener(id: Int, leagueSerie: String, date: String) {
        findNavController().navigate(
            R.id.action_matches_fragment_to_matches_details_fragment,
            Bundle().apply {
                putInt(ID_DETAILS_RESULT, id)
                putString(LEAGUE_SERIE_DETAILS_RESULT, leagueSerie)
                putString(DATE_RESULT, date)
            })
    }


}