package com.company.thecreater.dinnerdecider

import android.content.Context
import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.fragment_edit.*

internal var EditAdapter: RecyclerView.Adapter<*> = EditViewAdapter()

class EditFragment: FragmentInteractionListener() {
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        showKeyboard(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        editView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = EditAdapter
            itemAnimator = DefaultItemAnimator()
        }
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

    override fun onDetach() {
        super.onDetach()

        showKeyboard(false)
    }

    private fun showKeyboard(show: Boolean) {
        val inputManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputManager.hideSoftInputFromWindow(this.view?.windowToken, if (show) InputMethodManager.SHOW_FORCED else InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
