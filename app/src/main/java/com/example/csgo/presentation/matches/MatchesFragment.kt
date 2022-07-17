package com.example.csgo.presentation.matches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.csgo.databinding.FragmentMatchesBinding
import com.example.csgo.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MatchesFragment : Fragment() {

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
                }

                Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> {
                    Log.d("GuilhermeTeste", it.error.toString())
                }

                Resource.Status.SUCCESS -> {
                    binding.recyclerView.adapter =
                        context?.let { context -> MatchesAdapter(context, it.data) }
                    binding.recyclerView.layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }


}