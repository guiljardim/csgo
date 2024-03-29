package com.example.csgo.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.example.csgo.R
import com.example.csgo.databinding.AvatarImageViewBinding


private const val defaultValueAvatarSize = 60f
private const val defaultValue = 0
private const val defaultValueCircle = 40f
private const val defaultValueSquare = 8f

class AvatarImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var avatarType: AvatarType

    private var binding: AvatarImageViewBinding =
        AvatarImageViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageView)
        val dimension =
            attributes.getDimension(R.styleable.AvatarImageView_avatarSize, defaultValueAvatarSize)
        val layoutParams = LinearLayout.LayoutParams(dimension.toInt(), dimension.toInt())
        binding.avatar.layoutParams = layoutParams
        binding.imageViewAvatar.layoutParams.height = dimension.toInt()
        avatarType =
            AvatarType.values()[attributes.getInt(
                R.styleable.AvatarImageView_avatarType,
                defaultValue
            )]
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setAvatarType(avatarType)
    }

    private fun setAvatarType(avatarType: AvatarType) {
        when (avatarType) {
            AvatarType.CIRCLE -> binding.avatar.radius = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, defaultValueCircle,
                context.resources.displayMetrics
            )
            AvatarType.SQUARE -> binding.avatar.radius = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, defaultValueSquare,
                context.resources.displayMetrics
            )
        }
    }

    fun image(image: String) {
        binding.imageViewAvatar.load(image)

    }

}