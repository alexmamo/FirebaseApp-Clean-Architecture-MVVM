package ro.alexmamo.firebaseapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ro.alexmamo.firebaseapp.auth.AuthActivity;
import ro.alexmamo.firebaseapp.main.MainActivity;

public class SplashActivity extends DaggerAppCompatActivity {
    @Inject FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isUserAuthenticated()) {
            goToAuthInActivity();
        } else {
            goToMainActivity();
        }

        finish();
    }

    private boolean isUserAuthenticated() {
        firebaseUser = auth.getCurrentUser();
        return firebaseUser != null;
    }

    private void goToAuthInActivity() {
        Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
        startActivity(intent);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();
        intent.putExtra("uid", uid);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}