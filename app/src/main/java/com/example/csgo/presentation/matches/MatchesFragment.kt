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
import com.example.csgo.domain.model.Match
import com.example.csgo.presentation.matchDetails.MatchDetailsFragment.Companion.DATE_RESULT
import com.example.csgo.presentation.matchDetails.MatchDetailsFragment.Companion.ID_DETAILS_RESULT
import com.example.csgo.presentation.matchDetails.MatchDetailsFragment.Companion.LEAGUE_SERIE_DETAILS_RESULT
import com.example.csgo.util.Resource
import dagger.hilt.android.AndroidEntryPoint


private const val INSTANCE_STATE_PAGE = "INSTANCE_STATE_PAGE"
private const val INSTANCE_STATE_TOTAL_ITEMS = "INSTANCE_STATE_TOTAL_ITEMS"


@AndroidEntryPoint
class MatchesFragment : Fragment(), MatchesAdapter.OnItemClickListener,
    MatchesAdapter.OnBottomReachedListener {

    private val viewModel: MatchesViewModel by viewModels()
    private lateinit var binding: FragmentMatchesBinding
    private var listOfMatches: MutableList<Match> = mutableListOf()
    private var matchesAdapter: MatchesAdapter? = null
    private var page = 1
    private var totalItems: Int = 0


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(INSTANCE_STATE_PAGE, page)
        outState.putInt(INSTANCE_STATE_TOTAL_ITEMS, totalItems)
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getMatches(page, false)
        observer()
        createMatchList()
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            page = savedInstanceState.getInt(INSTANCE_STATE_PAGE)
            totalItems = savedInstanceState.getInt(INSTANCE_STATE_TOTAL_ITEMS)
            return
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observer() {
        viewModel.matches.observe(viewLifecycleOwner) {
            when (it.first.status) {

                Resource.Status.LOADING -> {
                    showProgress(!it.second)
                }

                Resource.Status.ERROR -> {
                    showProgress(false)
                    Toast.makeText(
                        context,
                        getString(R.string.error_list_matches),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Resource.Status.SUCCESS -> {
                    showProgress(false)
                    totalItems = it.first.data?.first()?.totalItem ?: 0
                    it.first.data?.let { match -> listOfMatches.addAll(match) }
                    if (it.second) {
                        matchesAdapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun createMatchList() {
        matchesAdapter = context?.let { MatchesAdapter(it, listOfMatches, this, this) }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = matchesAdapter

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

    override fun onBottomReached() {
        if (listOfMatches.size <= totalItems) {
            viewModel.onScrollEnded(page)
            page++
        } else {
            matchesAdapter?.isLoading(false)
        }

    }


}