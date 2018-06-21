package com.company.thecreater.dinnerdecider

import android.content.Context
import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : FragmentInteractionListener() {
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        editView.layoutManager = LinearLayoutManager(activity)
        editView.adapter = EditViewAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add.setOnClickListener {
            var visible = fragment.isShown

            TransitionManager.beginDelayedTransition(editContainer)
            visible = !visible

            fragment.visibility = if (visible) View.VISIBLE else View.GONE
            add.animate().rotation(if (fragment.isShown) 45f else 0.0f).interpolator = AccelerateDecelerateInterpolator()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }
}
