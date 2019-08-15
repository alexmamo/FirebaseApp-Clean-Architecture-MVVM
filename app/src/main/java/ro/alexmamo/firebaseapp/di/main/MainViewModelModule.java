package ro.alexmamo.firebaseapp.di.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ro.alexmamo.firebaseapp.di.AppViewModelFactory;
import ro.alexmamo.firebaseapp.di.ViewModelKey;
import ro.alexmamo.firebaseapp.main.MainViewModel;

@Module
@SuppressWarnings("unused")
public abstract class MainViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindAppViewModelFactory(AppViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel provideMainViewModel(MainViewModel viewModel);
}