package com.example.csgo.presentation.matchDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.csgo.databinding.ItemMatchDetailsBinding

class MatchDetailsAdapter(
    private var listOfPlayer: List<PlayerToShow>?,
) : RecyclerView.Adapter<MatchDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMatchDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listOfPlayer?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listOfPlayer.let {
            holder.bindView(it?.get(position))
        }
    }

    class ViewHolder(private val binding: ItemMatchDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(player: PlayerToShow?) {
            with(binding) {
                binding.textViewNameLeft.text = player?.nameLeft
                player?.image_urlLeft?.let { binding.iconImageViewPlayerLeft.image(it) }
                binding.textViewNicknameLeft.text = player?.nicknameLeft

                binding.textViewNameRight.text = player?.nameRight
                player?.image_urlRight?.let { binding.iconImageViewPlayerRight.image(it) }
                binding.textViewNicknameRight.text = player?.nicknameRight
            }
        }
    }

}