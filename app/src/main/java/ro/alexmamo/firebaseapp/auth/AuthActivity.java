package ro.alexmamo.firebaseapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

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
import ro.alexmamo.firebaseapp.data.User;
import ro.alexmamo.firebaseapp.databinding.ActivityAuthBinding;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ro.alexmamo.firebaseapp.utils.Actions.gotoMainActivity;
import static ro.alexmamo.firebaseapp.utils.Constants.RC_SIGN_IN;
import static ro.alexmamo.firebaseapp.utils.HelperClass.logErrorMessage;

public class AuthActivity extends DaggerAppCompatActivity {
    @Inject GoogleSignInClient googleSignInClient;
    @Inject AuthViewModel authViewModel;
    private ActivityAuthBinding activityAuthBinding;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAuthBinding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        progressBar = activityAuthBinding.progressBar;
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
                logErrorMessage(e.getMessage());
            }
        }
    }

    private void getGoogleAuthCredential(GoogleSignInAccount googleSignInAccount) {
        String googleTokenId = googleSignInAccount.getIdToken();
        AuthCredential googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null);
        signInWithGoogleAuthCredential(googleAuthCredential);
    }

    private void signInWithGoogleAuthCredential(AuthCredential googleAuthCredential) {
        displayProgressBar();
        authViewModel.signInWithGoogle(googleAuthCredential);
        authViewModel.authenticatedUserLiveData.observe(this, dataOrException -> {
            if (dataOrException.data != null) {
                User authenticatedUser = dataOrException.data;
                if (authenticatedUser.isNew) {
                    createNewUser(authenticatedUser);
                } else {
                    gotoMainActivity(this, authenticatedUser);
                    hideProgressBar();
                }
            }

            if (dataOrException.exception != null) {
                logErrorMessage(dataOrException.exception.getMessage());
            }
        });
    }

    private void createNewUser(User authenticatedUser) {
        displayProgressBar();
        authViewModel.createUser(authenticatedUser);
        authViewModel.createdUserLiveData.observe(this, dataOrException -> {
            if (dataOrException.data != null) {
                User createdUser = dataOrException.data;
                gotoMainActivity(this, createdUser);
                hideProgressBar();
            }

            if (dataOrException.exception != null) {
                logErrorMessage(dataOrException.exception.getMessage());
            }
        });
    }

    private void displayProgressBar() {
        if (progressBar.getVisibility() == GONE) {
            progressBar.setVisibility(VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (progressBar.getVisibility() == VISIBLE) {
            progressBar.setVisibility(GONE);
        }
    }
}