package ro.alexmamo.firebaseapp.splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ro.alexmamo.firebaseapp.data.DataOrException;
import ro.alexmamo.firebaseapp.data.User;

public class SplashViewModel extends ViewModel {
    private SplashRepository splashRepository;
    LiveData<DataOrException<User, Exception>> userLiveData;

    @Inject
    SplashViewModel(SplashRepository splashRepository) {
        this.splashRepository = splashRepository;
    }

    boolean checkIfUserIsAuthenticated() {
        return splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }

    String getUid() {
        return splashRepository.getFirebaseUserUid();
    }

    void setUid(String uid) {
        userLiveData = splashRepository.getUserDataFromFirestore(uid);
    }
}