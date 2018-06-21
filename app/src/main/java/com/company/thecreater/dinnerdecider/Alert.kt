package com.company.thecreater.dinnerdecider

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class Alert(context: Context?,
            title: String,
            message: String,
            positiveMessage: String,
            positiveAction: ((DialogInterface, Int) -> Unit)? = null,
            negativeMessage: String,
            negativeAction: ((DialogInterface, Int) -> Unit)? = null) {

    init {
        val alertDialogBuild : AlertDialog.Builder = AlertDialog.Builder(context)

        alertDialogBuild.setTitle(title)
        alertDialogBuild.setMessage(message)

        alertDialogBuild.setPositiveButton(positiveMessage, positiveAction)

        alertDialogBuild.setNegativeButton(negativeMessage, negativeAction)

        val alert : AlertDialog = alertDialogBuild.create()
        alert.show()
    }
}