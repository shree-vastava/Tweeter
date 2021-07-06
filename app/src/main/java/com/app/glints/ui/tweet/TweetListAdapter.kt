package com.app.glints.ui.tweet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.app.glints.R
import com.app.glints.callback.TweetActionListener
import com.app.glints.data.Tweet
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_tweet.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * This adapter extends the FirestoreRecyclerAdapter which keeps
 * a track of the updates in the table and updates itself in
 * real time.
 * The details of table and how the data should be fetched (query)
 * is provided in the FireStoreOptions in the constructor
 */
class TweetListAdapter(
    fireStoreRecyclerOptions: FirestoreRecyclerOptions<Tweet>,
    private val tweetActionListener: TweetActionListener
): FirestoreRecyclerAdapter<Tweet, TweetViewHolder>(fireStoreRecyclerOptions) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_tweet, parent, false)
        return TweetViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int, model: Tweet) {
        holder.itemView.tweet_text.text = model.text
        holder.itemView.tweet_timestamp.text = getDisplayDate(model.timestamp.toDate())
        holder.itemView.layout_menu_tweet.setOnClickListener {
            showPopupMenu(it, model)
        }
    }

    /**
     * Showing a menu for each tweet to access
     * the delete and edit action
     */
    private fun showPopupMenu(view: View, tweet: Tweet) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.tweet_menu_options, menu)
        setOnMenuItemClickListener { item ->
            when(item.itemId) {

                R.id.edit -> {
                    tweetActionListener.onTweetEdit(tweet)
                }

                R.id.delete -> {
                    tweetActionListener.onTweetDelete(tweet)
                }
            }
            true
        }
        show()
    }

    private fun getDisplayDate(date: Date): String{
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm")
        val strDate = dateFormat.format(date)
        return "Posted on $strDate"
    }

}
class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)