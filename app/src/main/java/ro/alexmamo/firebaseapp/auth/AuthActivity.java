package ro.alexmamo.firebaseapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.di.AppViewModelFactory;
import ro.alexmamo.firebaseapp.main.MainActivity;

import static ro.alexmamo.firebaseapp.utils.Constants.GOOGLE_API_CLIENT_ERROR;
import static ro.alexmamo.firebaseapp.utils.Constants.RC_SIGN_IN;
import static ro.alexmamo.firebaseapp.utils.Constants.TAG;
import static ro.alexmamo.firebaseapp.utils.Constants.WELCOME;

public class AuthActivity extends DaggerAppCompatActivity {
    @Inject AppViewModelFactory factory;
    private GoogleApiClient googleApiClient;
    private AuthViewModel authViewModel;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initSignInButton();
        initGoogleApiClient();
        initAuthViewModel();
        initAuthRepository();
    }

    private void initSignInButton() {
        SignInButton googleSignInButton = findViewById(R.id.google_sign_in_button);
        googleSignInButton.setOnClickListener(view -> signIn());
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void initGoogleApiClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, result -> Log.d(TAG, GOOGLE_API_CLIENT_ERROR))
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    private void initAuthViewModel() {
        authViewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);
    }

    private void initAuthRepository() {
        authRepository = authViewModel.getAuthRepository();
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
        signInWithGoogle(googleAuthCredential);
    }

    private void signInWithGoogle(AuthCredential googleAuthCredential) {
        authRepository.signInWithGoogle(googleAuthCredential, (user, userIsNew) -> {
            if (userIsNew) {
                toastWelcomeMessage(WELCOME + user.name);
            }
            checkIfUserExistsInDb(user);
        });
    }

    public void toastWelcomeMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void checkIfUserExistsInDb(User user) {
        authRepository.doesTheUserExist(user, userDoesNotExist -> {
            if (userDoesNotExist) {
                createUser(user);
            } else {
                goToMainActivity(user.uid, user.name);
            }
        });
    }

    private void createUser(User user) {
        authRepository.createUser(user, isUserCreated -> {
            if (isUserCreated) {
                goToMainActivity(user.uid, user.name);
            }
        });
    }

    private void goToMainActivity(String uid, String name) {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("name", name);
        startActivity(intent);
        finish();
    }
}