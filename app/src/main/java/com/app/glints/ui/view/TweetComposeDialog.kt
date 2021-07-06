package com.app.glints.ui.view

import android.app.Dialog
import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.glints.R
import com.app.glints.data.Tweet
import com.app.glints.callback.TweetPostListener
import kotlinx.android.synthetic.main.layout_add_tweet.*
import java.util.*

/**
 * This class opens up the compose UI
 * to add a new tweet or edit an existing one.
 */
object TweetComposeDialog {
    fun showDialog(context: Context, tweetPostListener: TweetPostListener, tweet: Tweet? = null){
        var dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_add_tweet)
        val window = dialog.window
        window!!.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        if(tweet!=null){
            dialog.edt_txt_tweet.setText(tweet.text)
        }
        dialog.btn_post.setOnClickListener {
            if(tweet==null) {
                var post = dialog.edt_txt_tweet.text.toString()
                var newTweet = Tweet(UUID.randomUUID().toString(), post)
                tweetPostListener.onPostButtonClicked(newTweet)
            }
            else{
                tweet.text = dialog.edt_txt_tweet.text.toString()
                tweetPostListener.onPostButtonClicked(tweet)
            }
            dialog.dismiss()
        }
        dialog.show()
    }
}