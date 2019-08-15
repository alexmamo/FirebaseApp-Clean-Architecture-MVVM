package ro.alexmamo.firebaseapp.main.products;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.google.firebase.firestore.CollectionReference;

import javax.inject.Inject;
import javax.inject.Named;

import static ro.alexmamo.firebaseapp.utils.Constants.PRODUCTS_REF;

public class ProductsViewModel extends ViewModel {
    private PagedList.Config config;
    private CollectionReference productsRef;
    private ProductDataSourceFactory sourceFactory;
    private LiveData<PagedList<Product>> pagedListLiveData;

    @Inject
    ProductsViewModel(PagedList.Config config, @Named(PRODUCTS_REF) CollectionReference productsRef) {
        this.config = config;
        this.productsRef = productsRef;
        sourceFactory = new ProductDataSourceFactory(null, productsRef);
        pagedListLiveData = new LivePagedListBuilder<>(sourceFactory, config).build();
    }

    LiveData<PagedList<Product>> getProductPagedListLiveData() {
        return pagedListLiveData;
    }

    void replaceSubscription(LifecycleOwner lifecycleOwner, String searchText) {
        pagedListLiveData.removeObservers(lifecycleOwner);
        if (searchText == null) {
            sourceFactory = new ProductDataSourceFactory(null, productsRef);
        } else {
            sourceFactory = new ProductDataSourceFactory(searchText, productsRef);
        }
        pagedListLiveData = new LivePagedListBuilder<>(sourceFactory, config).build();
    }
}