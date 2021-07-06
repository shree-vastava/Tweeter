package com.app.glints.callback

import com.app.glints.data.Tweet

/**
 * The callbacks to get the
 * action from user on any tweet.
 * A tweet can either be edited or deleted
 */
interface TweetActionListener {
    fun onTweetEdit(tweet: Tweet)
    fun onTweetDelete(tweet: Tweet)
}