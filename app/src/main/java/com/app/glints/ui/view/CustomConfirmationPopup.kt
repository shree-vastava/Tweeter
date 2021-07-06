package com.app.glints.ui.view

import android.app.Dialog
import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.glints.R
import com.app.glints.callback.ConfirmationPopupListener
import kotlinx.android.synthetic.main.layout_confirmation.*

object CustomConfirmationPopup {

    /**
     * This function can be used a generic
     * function to show confirmation popups
     * through out the class.
     * The message and icon is taken as an input
     * and the button click is then provided to
     * the source to take action
     *
     * NOTE: If we pass the 'Dialog' object in the
     * NO and YES callbacks, we will be able to handle
     * the cases where we want to dismiss/keep the popup on clicks. For
     * this app, we are dismissing it every time user
     * clicks on NO or YES.
     */
    fun showAlert(context: Context, label: String, icon: Int = R.drawable.twitter, confirmationPopupListener: ConfirmationPopupListener){
        var dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_confirmation)
        val window = dialog.window
        window!!.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.iv_logo.setImageResource(icon)
        dialog.label_confirmation.text = label
        dialog.tv_no.setOnClickListener {
            confirmationPopupListener.onNoClicked()
            dialog.dismiss()
        }

        dialog.tv_yes.setOnClickListener {
            confirmationPopupListener.onYesClicked()
            dialog.dismiss()
        }
        dialog.show()
    }
}