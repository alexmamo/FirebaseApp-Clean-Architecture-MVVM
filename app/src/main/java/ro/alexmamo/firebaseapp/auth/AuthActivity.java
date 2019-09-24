package ro.alexmamo.firebaseapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.databinding.ActivityAuthBinding;
import ro.alexmamo.firebaseapp.main.MainActivity;

import static ro.alexmamo.firebaseapp.utils.Constants.RC_SIGN_IN;
import static ro.alexmamo.firebaseapp.utils.Constants.TAG;
import static ro.alexmamo.firebaseapp.utils.Constants.USER;

public class AuthActivity extends DaggerAppCompatActivity {
    @Inject GoogleSignInClient googleSignInClient;
    @Inject AuthViewModel authViewModel;
    private ActivityAuthBinding activityAuthBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAuthBinding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        initSignInButton();
    }

    private void initSignInButton() {
        activityAuthBinding.googleSignInButton.setOnClickListener(view -> signIn());
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                if (googleSignInAccount != null) {
                    getGoogleAuthCredential(googleSignInAccount);
                }
            } catch (ApiException e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }

    private void getGoogleAuthCredential(GoogleSignInAccount googleSignInAccount) {
        String googleTokenId = googleSignInAccount.getIdToken();
        AuthCredential googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null);
        signInWithGoogleAuthCredential(googleAuthCredential);
    }

    private void signInWithGoogleAuthCredential(AuthCredential googleAuthCredential) {
        authViewModel.signInWithGoogle(googleAuthCredential);
        authViewModel.authenticatedUserLiveData.observe(this, authenticatedUser -> {
            if (authenticatedUser.isNew) {
                createNewUser(authenticatedUser);
            } else {
                goToMainActivity(authenticatedUser);
            }
        });
    }

    private void createNewUser(User authenticatedUser) {
        authViewModel.createUser(authenticatedUser);
        authViewModel.createdUserLiveData.observe(this, user -> {
            if (user.isCreated) {
                displayWelcomeMessage(user.name);
            }
            goToMainActivity(user);
        });
    }

    private void displayWelcomeMessage(String name) {
        String welcomeMessage = getWelcomeMessage(name);
        Toast.makeText(this, welcomeMessage, Toast.LENGTH_LONG).show();
    }

    private String getWelcomeMessage(String name) {
        return "Welcome " + name + "! Your account was successfully created!";
    }

    private void goToMainActivity(User user) {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        intent.putExtra(USER, user);
        startActivity(intent);
        finish();
    }
}