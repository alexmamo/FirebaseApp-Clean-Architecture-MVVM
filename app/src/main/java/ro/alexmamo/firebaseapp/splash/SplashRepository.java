package ro.alexmamo.firebaseapp.splash;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ro.alexmamo.firebaseapp.auth.User;

import static ro.alexmamo.firebaseapp.utils.Constants.USERS_REF;
import static ro.alexmamo.firebaseapp.utils.HelperClass.logErrorMessage;

@SuppressWarnings("ConstantConditions")
@Singleton
class SplashRepository {
    private FirebaseAuth auth;
    private User user;
    private CollectionReference usersRef;

    @Inject
    SplashRepository(FirebaseAuth auth, User user, @Named(USERS_REF) CollectionReference usersRef) {
        this.auth = auth;
        this.user = user;
        this.usersRef = usersRef;
    }

    MutableLiveData<User> checkIfUserIsAuthenticatedInFirebase() {
        MutableLiveData<User> isUserAuthenticateInFirebaseMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) {
            user.isAuthenticated = false;
            isUserAuthenticateInFirebaseMutableLiveData.setValue(user);
        } else {
            user.uid = firebaseUser.getUid();
            user.isAuthenticated = true;
            isUserAuthenticateInFirebaseMutableLiveData.setValue(user);
        }
        return isUserAuthenticateInFirebaseMutableLiveData;
    }

    MutableLiveData<User> addUserToLiveData(String uid) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        usersRef.document(uid).get().addOnCompleteListener(userTask -> {
            if (userTask.isSuccessful()) {
                DocumentSnapshot document = userTask.getResult();
                if(document.exists()) {
                    User user = document.toObject(User.class);
                    userMutableLiveData.setValue(user);
                }
            } else {
                logErrorMessage(userTask.getException().getMessage());
            }
        });
        return userMutableLiveData;
    }
}