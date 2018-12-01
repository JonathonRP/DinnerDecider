package com.company.thecreater.dinnerdecider

import android.net.Uri
import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity(),
        FragmentInteractionListener.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {

    }
}
