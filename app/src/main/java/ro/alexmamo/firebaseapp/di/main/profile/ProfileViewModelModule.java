package ro.alexmamo.firebaseapp.di.main.profile;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ro.alexmamo.firebaseapp.di.AppViewModelFactory;
import ro.alexmamo.firebaseapp.di.ViewModelKey;
import ro.alexmamo.firebaseapp.main.profile.ProfileViewModel;

@Module
@SuppressWarnings("unused")
public abstract class ProfileViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindAppViewModelFactory(AppViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel provideProfileViewModel(ProfileViewModel viewModel);
}