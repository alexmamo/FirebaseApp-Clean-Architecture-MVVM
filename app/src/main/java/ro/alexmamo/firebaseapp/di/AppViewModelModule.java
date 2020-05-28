package ro.alexmamo.firebaseapp.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ro.alexmamo.firebaseapp.auth.AuthViewModel;
import ro.alexmamo.firebaseapp.main.products.ProductsViewModel;
import ro.alexmamo.firebaseapp.splash.SplashViewModel;

@Module
@SuppressWarnings("unused")
abstract class AppViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindAppViewModelFactory(AppViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    abstract ViewModel provideAuthViewModel(AuthViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel provideSplashViewModel(SplashViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel.class)
    abstract ViewModel provideProductsViewModel(ProductsViewModel viewModel);
}