package com.company.thecreater.dinnerdecider

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*
import java.util.*

class MainFragment : FragmentInteractionListener() {
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        logo.setOnClickListener {

            val food = food_choice.text
            val random = Random()
            val randomFood = random.nextInt(foods.count())

            if(food != foods.elementAt(randomFood).food) {

                food_choice.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE

                searchFood(randomFood)
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

    private fun searchFood(randomFood : Int) {
        GlobalScope.async {
            delay(1000)

            progressBar.visibility = View.INVISIBLE
            food_choice.visibility = View.VISIBLE
        }

        food_choice.text = foods.elementAt(randomFood).food
    }
}
