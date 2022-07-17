package com.example.csgo.presentation.matches

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.csgo.R
import com.example.csgo.databinding.CardLayoutMatchBinding
import com.example.csgo.domain.model.Match
import com.example.csgo.util.formatToPattern

class MatchesAdapter(
    private val context: Context,
    private var listOfMatch: List<Match>?,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardLayoutMatchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listOfMatch?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listOfMatch.let {
            holder.bindView(it?.get(position), context, listener)
        }
    }

    class ViewHolder(private val binding: CardLayoutMatchBinding) :
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
                textLeagueSerie.text =
                    context.getString(R.string.league_serie, match?.leagueName, match?.serieName)

                if (match?.status == "running") {
                    tagStatus.text = context.getString(R.string.now)
                } else {
                    tagStatus.text = match?.begin_at.formatToPattern()
                    tagStatus.background =
                        AppCompatResources.getDrawable(context, R.drawable.tag_background_grey)

                }
                cardRoot.setOnClickListener {
                    match?.id?.let { id ->
                        listener.onItemClickListener(
                            id,
                            context.getString(
                                R.string.league_serie,
                                match.leagueName,
                                match.serieName
                            ),
                            match.begin_at.formatToPattern()
                        )
                    }
                }

            }
        }

    }

    interface OnItemClickListener {
        fun onItemClickListener(id: Int, leagueSerie: String, date: String)
    }

}
