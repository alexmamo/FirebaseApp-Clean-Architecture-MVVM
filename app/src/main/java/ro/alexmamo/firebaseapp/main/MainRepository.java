package ro.alexmamo.firebaseapp.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;

import ro.alexmamo.firebaseapp.OnAuthStateChangeListener;

@Singleton
class MainRepository implements FirebaseAuth.AuthStateListener {
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    OnAuthStateChangeListener onAuthStateChangeListener;

    @Inject
    MainRepository(FirebaseAuth auth, GoogleSignInClient googleSignInClient) {
        this.auth = auth;
        this.googleSignInClient = googleSignInClient;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) {
            onAuthStateChangeListener.onAuthStateChanged(true);
        }
    }

    void addFirebaseAuthListener() {
        auth.addAuthStateListener(this);
    }

    void removeFirebaseAuthListener() {
        auth.removeAuthStateListener(this);
    }

    MutableLiveData<Boolean> signOut() {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseSignOut();
            googleSignOut();
            mutableLiveData.setValue(true);
        }
        return mutableLiveData;
    }

    private void firebaseSignOut() {
        auth.signOut();
    }

    private void googleSignOut() {
        googleSignInClient.signOut();
    }
}