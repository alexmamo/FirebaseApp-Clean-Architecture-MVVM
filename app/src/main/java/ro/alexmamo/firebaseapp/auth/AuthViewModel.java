package ro.alexmamo.firebaseapp.auth;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {
    private AuthRepository authRepository;

    @Inject
    AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    AuthRepository getAuthRepository() {
        return authRepository;
    }
}