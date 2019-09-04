package ro.alexmamo.firebaseapp.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ro.alexmamo.firebaseapp.auth.AuthActivity;
import ro.alexmamo.firebaseapp.auth.User;
import ro.alexmamo.firebaseapp.main.MainActivity;

public class SplashActivity extends DaggerAppCompatActivity implements Observer<User> {
    @Inject FirebaseAuth auth;
    @Inject SplashViewModel splashViewModel;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isUserAuthenticated()) {
            goToAuthInActivity();
            finish();
        } else {
            String uid = firebaseUser.getUid();
            getUserFromDatabase(uid);
        }
    }

    private boolean isUserAuthenticated() {
        firebaseUser = auth.getCurrentUser();
        return firebaseUser != null;
    }

    private void goToAuthInActivity() {
        Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
        startActivity(intent);
    }

    private void getUserFromDatabase(String uid) {
        splashViewModel.setUid(uid);
        splashViewModel.userMutableLiveData.observe(this, this);
    }

    @Override
    public void onChanged(User user) {
        goToMainActivity1(user);
        finish();
    }

    private void goToMainActivity1(User user) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}