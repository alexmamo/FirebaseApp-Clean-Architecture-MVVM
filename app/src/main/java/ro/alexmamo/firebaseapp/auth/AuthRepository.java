package ro.alexmamo.firebaseapp.auth;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import static ro.alexmamo.firebaseapp.utils.Constants.USERS_REF;
import static ro.alexmamo.firebaseapp.utils.HelperClass.logErrorMessage;

@SuppressWarnings("ConstantConditions")
@Singleton
class AuthRepository {
    private FirebaseAuth firebaseAuth;
    private CollectionReference usersRef;

    @Inject
    AuthRepository(FirebaseAuth firebaseAuth, @Named(USERS_REF) CollectionReference usersRef) {
        this.firebaseAuth = firebaseAuth;
        this.usersRef = usersRef;
    }

    MutableLiveData<User> firebaseSignInWithGoogle(AuthCredential googleAuthCredential) {
        MutableLiveData<User> authenticatedUserMutableLiveData = new MutableLiveData<>();
        firebaseAuth.signInWithCredential(googleAuthCredential).addOnCompleteListener(authTask -> {
            if (authTask.isSuccessful()) {
                boolean isNewUser = authTask.getResult().getAdditionalUserInfo().isNewUser();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    String uid = firebaseUser.getUid();
                    String userName = firebaseUser.getDisplayName();
                    String email = firebaseUser.getEmail();
                    String photoUrl = Objects.requireNonNull(firebaseUser.getPhotoUrl()).toString();
                    User user = new User(uid, userName, email, photoUrl);
                    user.isNew = isNewUser;
                    authenticatedUserMutableLiveData.setValue(user);
                }
            } else {
                logErrorMessage(authTask.getException().getMessage());
            }
        });
        return authenticatedUserMutableLiveData;
    }

    MutableLiveData<User> createUserInFirestoreIfNotExists(User authenticatedUser) {
        MutableLiveData<User> newUserMutableLiveData = new MutableLiveData<>();
        DocumentReference uidRef = usersRef.document(authenticatedUser.uid);
        uidRef.get().addOnCompleteListener(uidTask -> {
            if (uidTask.isSuccessful()) {
                DocumentSnapshot document = uidTask.getResult();
                if (!document.exists()) {
                    uidRef.set(authenticatedUser).addOnCompleteListener(userCreationTask -> {
                        if (userCreationTask.isSuccessful()) {
                            authenticatedUser.isCreated = true;
                            newUserMutableLiveData.setValue(authenticatedUser);
                        } else {
                            logErrorMessage(userCreationTask.getException().getMessage());
                        }
                    });
                } else {
                    newUserMutableLiveData.setValue(authenticatedUser);
                }
            } else {
                logErrorMessage(uidTask.getException().getMessage());
            }
        });
        return newUserMutableLiveData;
    }
}