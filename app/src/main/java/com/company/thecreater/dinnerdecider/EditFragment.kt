package com.company.thecreater.dinnerdecider

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import kotlinx.android.synthetic.main.fragment_edit.*


internal val EditAdapter: RecyclerView.Adapter<*> = EditViewAdapter()

class EditFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = layoutInflater.inflate(R.layout.fragment_edit, editContainer, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EditAdapter
        }

        add.setOnClickListener {
            var visible = fragment.isShown

            TransitionManager.beginDelayedTransition(editContainer)
            visible = !visible

            fragment.visibility = if (visible) View.VISIBLE else View.GONE

            val rotate = ObjectAnimator.ofFloat(add, "rotation", 0.0f, 45f).apply {
                duration = 456
                interpolator = AccelerateDecelerateInterpolator()
            }
            val reverseRotate = ObjectAnimator.ofFloat(add, "rotation", 45f, 0.0f).apply {
                duration = 456
                interpolator = AccelerateDecelerateInterpolator()
            }
            val furtherReverseRotate = ObjectAnimator.ofFloat(add, "rotation", -45f, 0.0f).apply {
                duration = 456
                interpolator = AccelerateDecelerateInterpolator()
            }

            AnimatorSet().apply {

                if (fragment.isShown) {
                    play(rotate)
                    add.setImageResource(R.drawable.ic_close_white_48dp)
                    play(reverseRotate)
                } else {
                    play(furtherReverseRotate)
                    add.setImageResource(R.drawable.ic_add_white_48dp)
                    play(reverseRotate)
                }

                start()
            }
        }
    }
}
