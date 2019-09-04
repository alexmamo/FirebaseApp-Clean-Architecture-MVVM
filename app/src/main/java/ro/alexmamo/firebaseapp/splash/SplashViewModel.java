package ro.alexmamo.firebaseapp.splash;

import androidx.lifecycle.MutableLiveData;
        import androidx.lifecycle.ViewModel;

        import javax.inject.Inject;

        import ro.alexmamo.firebaseapp.auth.User;

public class SplashViewModel extends ViewModel {
    private SplashRepository splashRepository;
    MutableLiveData<User> isUserAuthenticatedMutableLiveData = new MutableLiveData<>();
    MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    @Inject
    SplashViewModel(SplashRepository splashRepository) {
        this.splashRepository = splashRepository;
    }

    void checkIfUserIsAuthenticated() {
        isUserAuthenticatedMutableLiveData = splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }

    void setUid(String uid) {
        userMutableLiveData = splashRepository.addUserToLiveData(uid);
    }
}