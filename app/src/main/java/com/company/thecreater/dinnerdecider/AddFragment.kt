package com.company.thecreater.dinnerdecider

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : FragmentInteractionListener() {
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        add_food.setOnFocusChangeListener { _, hasFocus ->

            if(!hasFocus) {
                val food: String = add_food.text.toString().capitalize()

                if (food != "" &&
                        !(food.isEmpty()) &&
                        !(foods.contains(Food(food)))) {

                    Kitchen.add(food).addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            Toast.makeText(context, "$food was added", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "$food was not added", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
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
