package ro.alexmamo.firebaseapp.di.splash;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ro.alexmamo.firebaseapp.di.AppViewModelFactory;
import ro.alexmamo.firebaseapp.di.ViewModelKey;
import ro.alexmamo.firebaseapp.splash.SplashViewModel;

@Module
@SuppressWarnings("unused")
public abstract class SplashViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindAppViewModelFactory(AppViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel provideAuthViewModel(SplashViewModel viewModel);
}