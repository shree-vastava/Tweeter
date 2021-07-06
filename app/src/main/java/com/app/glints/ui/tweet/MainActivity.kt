package com.app.glints.ui.tweet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.glints.*
import com.app.glints.callback.ConfirmationPopupListener
import com.app.glints.callback.TweetActionListener
import com.app.glints.callback.TweetPostListener
import com.app.glints.data.Tweet
import com.app.glints.ui.login.LoginActivity
import com.app.glints.ui.view.CustomConfirmationPopup
import com.app.glints.ui.view.TweetComposeDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_tweet.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TweetActionListener, View.OnClickListener {

    val viewModel: MainViewModel by viewModels()
    lateinit var fsOptions: FirestoreRecyclerOptions<Tweet>
    lateinit var tweetListAdapter: TweetListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Getting the firebase options to pass to
         * the adapter constructore
         */
        lifecycleScope.launch {
            suspend {
               fsOptions = viewModel.getFSOptions()
            }.invoke()
        }

        tweetListAdapter = TweetListAdapter(fsOptions, this)

        /**
         * setting up recycler view
         */
        recycler_tweets.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = tweetListAdapter
            setHasFixedSize(true)
        }

        /**
         * clicks on logout and fab to add tweet
         */
        fab_add.setOnClickListener(this)
        iv_logout.setOnClickListener(this)

        /**
         * setting data on UI
         */
        setUserData(viewModel.getUserDetails())

    }

    private fun setUserData(user: FirebaseUser?) {
        tv_user_name.text = user?.displayName
        Glide.with(this)
                .load(user?.photoUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(iv_profile_picture)
    }


    /**
     * stopping the adapter from listening to
     * updates when the activity is in background
     */
    override fun onStop() {
        super.onStop()
        tweetListAdapter.stopListening()
    }

    /**
     * starting the adapter's listening to
     * updates when the activity comes to foreground
     */
    override fun onStart() {
        super.onStart()
        tweetListAdapter.startListening()
    }


    /**
     * When the user clicks on editing the tweet
     */
    override fun onTweetEdit(tweet: Tweet) {
        TweetComposeDialog.showDialog(this, object : TweetPostListener {
            override fun onPostButtonClicked(tweet: Tweet) {
                viewModel.addOrUpdatetweet(tweet)
            }
        }, tweet)
    }

    /**
     * When the user clicks on deleting the tweet
     */
    override fun onTweetDelete(tweet: Tweet) {
        deleteTweet(tweet)
    }

    /**
     * Handling the action on the click
     * of the logout and fab
     */
    override fun onClick(v: View?) {
        when(v){
            iv_logout -> {
                logout()
            }

            fab_add -> {
                TweetComposeDialog.showDialog(this, object : TweetPostListener {
                    override fun onPostButtonClicked(tweet: Tweet) {
                        viewModel.addOrUpdatetweet(tweet)
                    }
                })
            }
        }
    }

    /**
     * Using the custom confirmation popup,
     * we pass the icon, title andd get back the
     * clicks. So it can be used generic throughout
     * the app
     */
    private fun logout(){
       CustomConfirmationPopup.showAlert(this, getString(R.string.confirmation_logout), confirmationPopupListener = object : ConfirmationPopupListener{
           override fun onYesClicked() {
               viewModel.logout()
               startActivity(Intent(this@MainActivity,LoginActivity::class.java))
               finish()
           }
           override fun onNoClicked() {
              //
           }

       })
    }

    private fun deleteTweet(tweet: Tweet){
        CustomConfirmationPopup.showAlert(this, getString(R.string.confirmation_delete),confirmationPopupListener =  object : ConfirmationPopupListener{
            override fun onYesClicked() {
                viewModel.deleteTweet(tweet)
            }

            override fun onNoClicked() {
                //
            }

        })
    }


}