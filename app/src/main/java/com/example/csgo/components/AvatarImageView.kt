package com.example.csgo.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.example.csgo.R
import com.example.csgo.databinding.AvatarImageViewBinding


@SuppressLint("Recycle")
class AvatarImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: AvatarImageViewBinding =
        AvatarImageViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageView)
        val dimension = attributes.getDimension(R.styleable.AvatarImageView_avatarSize, 60f)
        val layoutParams = LinearLayout.LayoutParams(dimension.toInt(), dimension.toInt())
        binding.avatar.layoutParams = layoutParams
        binding.imageViewAvatar.layoutParams.height = dimension.toInt()
    }

    fun image(image: String) {
        binding.imageViewAvatar.load(image)

    }

}