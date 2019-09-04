package ro.alexmamo.firebaseapp.di;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.auth.User;

import static ro.alexmamo.firebaseapp.utils.Constants.PRODUCTS_COLLECTION;
import static ro.alexmamo.firebaseapp.utils.Constants.PRODUCTS_REF;
import static ro.alexmamo.firebaseapp.utils.Constants.USERS_COLLECTION;
import static ro.alexmamo.firebaseapp.utils.Constants.USERS_REF;

@Module
class AppModule {
    @Singleton
    @Provides
    static User provideUser() {
        return new User();
    }

    @Singleton
    @Provides
    static GoogleSignInOptions provideGoogleSignInOptions(Application application) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    @Singleton
    @Provides
    static GoogleSignInClient provideGoogleSignInClient(Application application, GoogleSignInOptions googleSignInOptions) {
        return GoogleSignIn.getClient(application, googleSignInOptions);
    }

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

    @Singleton
    @Provides
    @Named(PRODUCTS_REF)
    static CollectionReference provideProductsCollectionReference(FirebaseFirestore rootRef) {
        return rootRef.collection(PRODUCTS_COLLECTION);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application) {
        return Glide.with(application);
    }
}