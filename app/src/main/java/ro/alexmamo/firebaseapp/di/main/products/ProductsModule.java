package ro.alexmamo.firebaseapp.di.main.products;

import androidx.paging.PagedList;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static ro.alexmamo.firebaseapp.utils.Constants.PRODUCTS_COLLECTION;
import static ro.alexmamo.firebaseapp.utils.Constants.PRODUCTS_PER_PAGE;
import static ro.alexmamo.firebaseapp.utils.Constants.PRODUCTS_REF;

@Module
public class ProductsModule {
    @Singleton
    @Provides
    static PagedList.Config providePagedListConfig() {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PRODUCTS_PER_PAGE)
                .build();
    }

    @Singleton
    @Provides
    @Named(PRODUCTS_REF)
    static CollectionReference provideProductsCollectionReference(FirebaseFirestore rootRef) {
        return rootRef.collection(PRODUCTS_COLLECTION);
    }
}