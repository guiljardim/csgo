package com.example.csgo.presentation.matches

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.csgo.R
import com.example.csgo.databinding.CardLayoutMatchBinding
import com.example.csgo.databinding.LoadingMatchBinding
import com.example.csgo.domain.model.Match
import com.example.csgo.util.formatToPattern

class MatchesAdapter(
    private val context: Context,
    private var listOfMatch: List<Match>,
    private val onBottomListener: OnBottomReachedListener,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading: Boolean = true

    fun isLoading(result: Boolean) {
        this.isLoading = result

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LOADING -> LoadingViewHolder(
                LoadingMatchBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ITEM -> MatchViewHolder(
                CardLayoutMatchBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> MatchViewHolder(
                CardLayoutMatchBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == listOfMatch.size - 1 && isLoading) LOADING else ITEM
    }

    override fun getItemCount(): Int = listOfMatch.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LOADING -> (holder as LoadingViewHolder).bindView()
            ITEM -> (holder as MatchViewHolder).bindView(listOfMatch[position], context, listener)
        }
        if (position == listOfMatch.size - 1) {
            onBottomListener.onBottomReached()
        }

    }

    class MatchViewHolder(private val binding: CardLayoutMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(match: Match?, context: Context, listener: OnItemClickListener) {
            with(binding) {
                if (!match?.opponents.isNullOrEmpty()) {
                    match?.opponents?.first()?.image_url?.let { iconImageViewFirstOpponent.image(it) }
                    match?.opponents?.last()?.image_url?.let { iconImageViewSecondOpponent.image(it) }
                    textViewFirstOpponentName.text = match?.opponents?.first()?.name
                    textViewSecondOpponentName.text = match?.opponents?.last()?.name
                }

                match?.leagueIcon?.let { iconImageLeague.image(it) }
                val leaguePlusSerie = context.getString(
                    R.string.league_serie, match?.leagueName.orEmpty(),
                    match?.serieName.orEmpty()
                )
                val dateFormatted = match?.begin_at.formatToPattern(context)

                textLeagueSerie.text = leaguePlusSerie

                if (match?.status == "running") {
                    tagStatus.text = context.getString(R.string.now)
                } else {
                    tagStatus.text = dateFormatted
                    tagStatus.background =
                        AppCompatResources.getDrawable(context, R.drawable.tag_background_grey)

                }
                cardRoot.setOnClickListener {
                    match?.id?.let { id ->
                        listener.onItemClickListener(
                            id,
                            leaguePlusSerie,
                            dateFormatted
                        )
                    }
                }

            }
        }

    }

    class LoadingViewHolder(private val binding: LoadingMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView() {
            with(binding) {
                binding.progressCircularLoadMore.isVisible = true
            }
        }

    }


    interface OnItemClickListener {
        fun onItemClickListener(id: Int, leagueSerie: String, date: String)
    }

    interface OnBottomReachedListener {
        fun onBottomReached()
    }

    companion object {
        const val LOADING = 0
        const val ITEM = 1
    }

}
