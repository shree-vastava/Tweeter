package com.app.glints.data

import com.google.firebase.Timestamp
import com.google.firebase.database.Exclude

data class Tweet(
    @get:Exclude
    var id: String="",
    var text: String="",
    val timestamp: Timestamp= Timestamp.now()){
}