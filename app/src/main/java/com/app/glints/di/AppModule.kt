package com.app.glints.di


import com.app.glints.data.Repository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * This class provides the dependencies
 * used in the application
 */

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseDatabvaseReference(): DatabaseReference = FirebaseDatabase.getInstance().getReference("Tweet")


    @Provides
    @Singleton
    fun provideRepository(twitterCollectionReference: CollectionReference): Repository = Repository(twitterCollectionReference)

    @Provides
    @Singleton
    fun provideTwitterCollectionReference(firestore: FirebaseFirestore): CollectionReference = firestore.collection("tweets")

    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()


}