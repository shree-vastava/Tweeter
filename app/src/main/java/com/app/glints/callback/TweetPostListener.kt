package com.app.glints.callback

import com.app.glints.data.Tweet

/**
 * The callback when user
 * clicks on the post button
 * of the compose popup for new tweet
 */
interface TweetPostListener {
    fun onPostButtonClicked(tweet: Tweet)
}