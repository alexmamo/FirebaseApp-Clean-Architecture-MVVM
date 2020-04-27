package ro.alexmamo.firebaseapp.di.splash;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ro.alexmamo.firebaseapp.data.User;

@Module
public class SplashModule {
    @Singleton
    @Provides
    static User provideUser() {
        return new User();
    }
}