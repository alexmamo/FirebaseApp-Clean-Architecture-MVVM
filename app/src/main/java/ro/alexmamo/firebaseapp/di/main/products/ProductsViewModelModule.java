package ro.alexmamo.firebaseapp.di.main.products;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import ro.alexmamo.firebaseapp.di.AppViewModelFactory;
import ro.alexmamo.firebaseapp.di.ViewModelKey;
import ro.alexmamo.firebaseapp.main.products.ProductsViewModel;

import static ro.alexmamo.firebaseapp.utils.Constants.ITEMS_PER_PAGE;

@Module
@SuppressWarnings("unused")
public abstract class ProductsViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindAppViewModelFactory(AppViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel.class)
    abstract ViewModel provideProductsViewModel(ProductsViewModel viewModel);

    @Singleton
    @Provides
    static PagedList.Config providePagedListConfig() {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ITEMS_PER_PAGE)
                .build();
    }
}