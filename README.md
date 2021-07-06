# Tweet App
![Imgur](https://imgur.com/DTNuPpX.jpg)
The Tweet application is a tweet posting application for a single user. It uses Firestore to store and fetch the tweets. The Firestore updates the screen realtime by using the FirestoreRecyclerAdapter

## App use case
- Post new tweets.
- See the tweets in a list sorted by timestamp
- Edit and delete a tweet.
- Login and logout using firebase google authentication

## App Architecture
![Imgur](https://imgur.com/FScZEpj.jpg)
The architecture used for this application is MVVM. The activity uses viewModel functions to save and fetch data to firestore via a repository.
The FirestoreRecyclerAdapter keeps listening to the changes in the database and updates in realtime.

Components used:
Hilt for Dependency injection
Coroutines for pushing data to Firestore
Firebase integration

## Third Party Libraries
The following 3rd party libraries are used:
- SDP intuit: to support multiple screen avoiding the dimens files.
- Glide: To load images
