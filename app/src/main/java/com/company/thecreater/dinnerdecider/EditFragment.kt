package com.company.thecreater.dinnerdecider

import android.content.Context
import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : FragmentInteractionListener() {
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editView.layoutManager = LinearLayoutManager(context)
        editView.adapter = EditViewAdapter()

        add.setOnClickListener {
            var visible = false

            TransitionManager.beginDelayedTransition(editContainer)
            visible = !visible
            addfragment.view?.visibility = if (visible) View.VISIBLE else View.GONE
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
