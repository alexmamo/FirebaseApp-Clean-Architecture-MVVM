package ro.alexmamo.firebaseapp.di.main.products;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ro.alexmamo.firebaseapp.di.AppViewModelFactory;
import ro.alexmamo.firebaseapp.di.ViewModelKey;
import ro.alexmamo.firebaseapp.main.products.ProductsViewModel;

@Module
@SuppressWarnings("unused")
public abstract class ProductsViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindAppViewModelFactory(AppViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel.class)
    abstract ViewModel provideProductsViewModel(ProductsViewModel viewModel);
}