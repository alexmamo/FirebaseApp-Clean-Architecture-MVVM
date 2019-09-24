package ro.alexmamo.firebaseapp.splash;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ro.alexmamo.firebaseapp.auth.AuthActivity;
import ro.alexmamo.firebaseapp.auth.User;
import ro.alexmamo.firebaseapp.main.MainActivity;

import static ro.alexmamo.firebaseapp.utils.Constants.USER;

public class SplashActivity extends DaggerAppCompatActivity {
    @Inject SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIfUserIsAuthenticated();
    }

    private void checkIfUserIsAuthenticated() {
        splashViewModel.checkIfUserIsAuthenticated();
        splashViewModel.isUserAuthenticatedMutableLiveData.observe(this, user -> {
            if (!user.isAuthenticated) {
                goToAuthInActivity();
                finish();
            } else {
                getUserFromDatabase(user.uid);
            }
        });
    }

    private void goToAuthInActivity() {
        Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
        startActivity(intent);
    }

    private void getUserFromDatabase(String uid) {
        splashViewModel.setUid(uid);
        splashViewModel.userMutableLiveData.observe(this, user -> {
            goToMainActivity(user);
            finish();
        });
    }

    private void goToMainActivity(User user) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }
}