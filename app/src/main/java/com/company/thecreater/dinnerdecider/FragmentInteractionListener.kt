package com.company.thecreater.dinnerdecider

import android.net.Uri
import android.support.v4.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentInteractionListener.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddFragment()] factory method to
 * create an instance of this fragment.
 *
 */

abstract class FragmentInteractionListener : Fragment() {

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
}