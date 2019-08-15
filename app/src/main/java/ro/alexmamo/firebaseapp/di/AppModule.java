package ro.alexmamo.firebaseapp.di;

import android.app.Application;

import androidx.paging.PagedList;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static ro.alexmamo.firebaseapp.utils.Constants.ITEMS_PER_PAGE;
import static ro.alexmamo.firebaseapp.utils.Constants.PRODUCTS_COLLECTION;
import static ro.alexmamo.firebaseapp.utils.Constants.PRODUCTS_REF;
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

    @Singleton
    @Provides
    @Named(PRODUCTS_REF)
    static CollectionReference provideProductsCollectionReference(FirebaseFirestore rootRef) {
        return rootRef.collection(PRODUCTS_COLLECTION);
    }

    @Singleton
    @Provides
    static PagedList.Config providePagedListConfig() {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ITEMS_PER_PAGE)
                .build();
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application) {
        return Glide.with(application);
    }
}