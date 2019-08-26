# FirebaseApp - Clean Architecture with MVVM.

FirebaseApp, is an aplication build using Java 8 as an example for integrating two very important Firebase services: [Firebase Authenication](https://firebase.google.com/docs/auth) and [Cloud Firestore](https://firebase.google.com/docs/firestore).

The authentication process is based on using a Google account. To keep things simple, the app uses a very simple database schema that look like in the image below:

![alt text](https://i.ibb.co/rkmLvqY/Db.jpg)

For getting the data, the app uses [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) and the pagination is build using the [Pagination Library](https://developer.android.com/topic/libraries/architecture/paging) together with MVVM Architecture Pattern. The app has a search feature which also uses pagination. For dependency injection the app uses [Dagger2](https://dagger.dev/).

For displaying the data, the app uses [Android Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started).

To make this app work, follow the instructions given in the official documentation regarding [how to add Firebase to your project](https://firebase.google.com/docs/android/setup). Add the JSON file in your app folder, add some dummy products and see its features.

See it on youtube: https://www.youtube.com/watch?v=esYPwbGW7YY
