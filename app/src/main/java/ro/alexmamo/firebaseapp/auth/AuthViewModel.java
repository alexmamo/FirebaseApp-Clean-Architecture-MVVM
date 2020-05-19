package ro.alexmamo.firebaseapp.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.AuthCredential;

import javax.inject.Inject;

import ro.alexmamo.firebaseapp.data.DataOrException;
import ro.alexmamo.firebaseapp.data.User;

public class AuthViewModel extends ViewModel {
    private AuthRepository authRepository;
    LiveData<DataOrException<User, Exception>> authenticatedUserLiveData;
    LiveData<DataOrException<User, Exception>> createdUserLiveData;

    @Inject
    AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    void signInWithGoogle(AuthCredential googleAuthCredential) {
        authenticatedUserLiveData = authRepository.firebaseSignInWithGoogle(googleAuthCredential);
    }

    void createUser(User authenticatedUser) {
        createdUserLiveData = authRepository.createUserInFirestore(authenticatedUser);
    }
}