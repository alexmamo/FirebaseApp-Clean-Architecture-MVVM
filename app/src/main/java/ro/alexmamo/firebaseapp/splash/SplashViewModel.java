package ro.alexmamo.firebaseapp.splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ro.alexmamo.firebaseapp.data.User;

public class SplashViewModel extends ViewModel {
    private SplashRepository splashRepository;
    LiveData<User> isUserAuthenticatedLiveData;
    LiveData<User> userLiveData;

    @Inject
    SplashViewModel(SplashRepository splashRepository) {
        this.splashRepository = splashRepository;
    }

    void checkIfUserIsAuthenticated() {
        isUserAuthenticatedLiveData = splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }

    void setUid(String uid) {
        userLiveData = splashRepository.addUserToLiveData(uid);
    }
}