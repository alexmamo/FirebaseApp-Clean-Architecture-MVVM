package ro.alexmamo.firebaseapp.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ro.alexmamo.firebaseapp.SplashActivity;
import ro.alexmamo.firebaseapp.auth.AuthActivity;
import ro.alexmamo.firebaseapp.main.MainActivity;

@Module
@SuppressWarnings("unused")
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract MainActivity contributeMainActivity();
}