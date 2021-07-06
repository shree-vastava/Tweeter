package com.app.glints.data

import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * The repository handles the
 * communication to and fro the
 * FireStore DB and provides it to the viewmodel
 */
class Repository @Inject constructor (private val twitterCollectionReference: CollectionReference) {

    suspend fun addTweet(tweet: Tweet) = withContext(Dispatchers.IO){
        twitterCollectionReference.document(getCurrentUser()?.uid!!)
            .collection("user_tweets")
            .document(tweet.id)
            .set(tweet)
            .addOnSuccessListener{

            }
    }

    suspend fun deleteTweet(tweet: Tweet) = withContext(Dispatchers.IO){
        twitterCollectionReference.document(getCurrentUser()?.uid!!)
            .collection("user_tweets")
            .document(tweet.id)
            .delete()
            .addOnSuccessListener{

            }
    }

    fun getFStoreOptions():FirestoreRecyclerOptions<Tweet> {
        return FirestoreRecyclerOptions.Builder<Tweet>()
            .setQuery(twitterCollectionReference.document(getCurrentUser()?.uid!!).collection("user_tweets").orderBy("timestamp", Query.Direction.DESCENDING), Tweet::class.java)
            .build()
    }

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}