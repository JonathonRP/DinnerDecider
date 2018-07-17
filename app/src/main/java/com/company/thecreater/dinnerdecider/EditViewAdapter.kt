package com.company.thecreater.dinnerdecider

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.edit_view.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*


class EditViewAdapter(private var context: Context? = null) : RecyclerView.Adapter<EditViewHolder>() {

    override fun getItemCount(): Int {

        return foods.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.edit_view, parent, false)

        context = parent.context

        return EditViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: EditViewHolder, position: Int) {

        val food = foods.elementAt(position).food
        val foodId = foods.elementAt(position).id

        holder.itemView?.item_title_layout?.hint = food
        holder.itemView?.item_title?.setText(food)

        holder.itemView?.item_title?.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus) {
                val newFood: String = holder.itemView.item_title?.text.toString().capitalize()

                if (newFood != "" &&
                        !(newFood.isEmpty()) &&
                        !(foods.contains(Food(newFood)))) {

                    updateItem(context, foodId, newFood, position)
                }
            }
        }

        holder.itemView?.delete?.setOnClickListener {

            Alert(context,
                    "You are Deleting $food",
                    "Are you sure you want to delete $food",
                    "Yes", { _: DialogInterface, _: Int ->
                        deleteItem(context, foodId, food, position)
                    },
                    "No")
        }

        holder.itemView?.item_title?.setOnKeyListener { _, keyCode, _ ->

            if (keyCode == KeyEvent.KEYCODE_ENTER) {

                val newFood: String = holder.itemView.item_title?.text.toString().capitalize()

                if(newFood != "" &&
                        !(newFood.isEmpty()) &&
                            !(foods.contains(Food(newFood)))) {

                    updateItem(context, foodId, newFood, position)

                } else if (foods.contains(Food(newFood))) {
                    holder.itemView.food_choice.clearFocus()

                } else if(newFood == "" && newFood.isEmpty()) {
                    Alert(context,
                            "You are Deleting $newFood",
                            "Are you sure you want to delete $newFood",
                            "Yes", { _: DialogInterface, _: Int ->
                                deleteItem(context, foodId, newFood, position)
                            },
                            "No")
                }

            }
            false
        }
    }

    private fun updateItem(context: Context?, foodId: String, food: String, position: Int) {

        oldFood = foods.elementAt(position)

        Kitchen.update(foodId, food).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                notifyDataSetChanged()
                Toast.makeText(context, "$food was updated", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(context, "$food was not updated", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun deleteItem(context: Context?, foodId: String, food: String, position: Int) {

        Kitchen.delete(foodId).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
                Toast.makeText(context, "$food was deleted", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(context, "$food was not deleted", Toast.LENGTH_LONG).show()
            }
        }
    }
}

class EditViewHolder(view : View) : RecyclerView.ViewHolder(view)