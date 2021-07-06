package com.app.glints.ui.tweet

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.glints.data.Repository
import com.app.glints.data.Tweet
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

/**
 * The viewmodel is used to fetch the information from
 * repository and provide it to the UI.
 * And the other way around, fetch info from the UI
 * and provide to the repository to process.
 */
class MainViewModel @ViewModelInject constructor(private val repository: Repository): ViewModel() {

    fun getUserDetails(): FirebaseUser?{
        return repository.getCurrentUser()
    }

    fun getFSOptions():FirestoreRecyclerOptions<Tweet>{
        return repository.getFStoreOptions()
    }

    fun addOrUpdatetweet(tweet: Tweet){
        viewModelScope.launch {
            repository.addTweet(tweet)
        }
    }

    fun deleteTweet(tweet: Tweet){
        viewModelScope.launch {
            repository.deleteTweet(tweet)
        }
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut()
    }
}