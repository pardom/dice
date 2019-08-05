package dev.pardo.dice.ui

import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.itemanimators.SlideDownAlphaAnimator

class HistoryItemAnimator : SlideDownAlphaAnimator() {

    override fun removeAnimation(holder: RecyclerView.ViewHolder): ViewPropertyAnimatorCompat {
        val animation = ViewCompat.animate(holder.itemView)
        return animation
            .setDuration(removeDuration)
            .translationX((holder.itemView.width).toFloat())
            .setInterpolator(interpolator)
    }

    override fun removeAnimationCleanup(holder: RecyclerView.ViewHolder) {
        ViewCompat.setTranslationX(holder.itemView, 0f)
    }

}
