package ro.alexmamo.firebaseapp.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ro.alexmamo.firebaseapp.OnAuthStateChangeListener;

public class MainViewModel extends ViewModel {
    private MainRepository mainRepository;
    LiveData<Boolean> isUserSignedOutLiveData;

    @Inject
    MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    void setAuthStateChangeListener(OnAuthStateChangeListener onAuthStateChangeListener) {
        mainRepository.onAuthStateChangeListener = onAuthStateChangeListener;
    }

    void signOut() {
        isUserSignedOutLiveData = mainRepository.signOut();
    }

    void addAuthListener() {
        mainRepository.addFirebaseAuthListener();
    }

    void removeAuthListener() {
        mainRepository.removeFirebaseAuthListener();
    }
}