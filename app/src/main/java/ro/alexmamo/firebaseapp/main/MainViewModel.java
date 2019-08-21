package ro.alexmamo.firebaseapp.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;

    @Inject
    MainViewModel(MainRepository mainRepository) {
        firebaseUserMutableLiveData = mainRepository.getFirebaseUserMutableLiveData();
    }

    MutableLiveData<FirebaseUser> getFirebaseUserLiveData() {
        return firebaseUserMutableLiveData;
    }
}