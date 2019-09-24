package ro.alexmamo.firebaseapp.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static ro.alexmamo.firebaseapp.utils.Constants.USERS_COLLECTION;
import static ro.alexmamo.firebaseapp.utils.Constants.USERS_REF;

@Module
class AppModule {
    @Singleton
    @Provides
    static FirebaseAuth provideFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    static FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Singleton
    @Provides
    @Named(USERS_REF)
    static CollectionReference provideUsersCollectionReference(FirebaseFirestore rootRef) {
        return rootRef.collection(USERS_COLLECTION);
    }
}