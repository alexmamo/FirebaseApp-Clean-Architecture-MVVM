package ro.alexmamo.firebaseapp.main;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class MainRepository {
    private FirebaseUser firebaseUser;

    @Inject
    MainRepository(FirebaseAuth firebaseAuth) {
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        MutableLiveData<FirebaseUser> firebaseUserMutableLiveData = new MutableLiveData<>();
        firebaseUserMutableLiveData.setValue(firebaseUser);
        return firebaseUserMutableLiveData;
    }
}