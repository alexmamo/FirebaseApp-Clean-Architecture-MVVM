package ro.alexmamo.firebaseapp.main.products;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.google.firebase.firestore.CollectionReference;

public class ProductDataSourceFactory extends DataSource.Factory<Integer, Product> {
    private MutableLiveData<PageKeyedDataSource<Integer, Product>> liveData = new MutableLiveData<>();
    private String searchText;
    private CollectionReference productsRef;

    ProductDataSourceFactory(String searchText, CollectionReference productsRef) {
        this.searchText = searchText;
        this.productsRef = productsRef;
    }

    @NonNull
    @Override
    public DataSource<Integer, Product> create() {
        ProductDataSource productDataSource = new ProductDataSource(searchText, productsRef);
        liveData.postValue(productDataSource);
        return productDataSource;
    }
}