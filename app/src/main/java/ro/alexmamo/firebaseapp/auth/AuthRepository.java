package ro.alexmamo.firebaseapp.auth;

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

    void signInWithGoogle(AuthCredential googleAuthCredential, AuthCallback callback) {
        firebaseAuth.signInWithCredential(googleAuthCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    String uid = firebaseUser.getUid();
                    String userName = firebaseUser.getDisplayName();
                    String photoUrl = Objects.requireNonNull(firebaseUser.getPhotoUrl()).toString();
                    User user = new User(uid, userName, photoUrl);
                    callback.onAuthCallback(user, isNewUser);
                }
            }
        });
    }

    void doesTheUserExist(User user, UserExistenceCallback callback) {
        DocumentReference uidRef = usersRef.document(user.uid);
        uidRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (!document.exists()) {
                    callback.onUserExistenceCallback(true);
                } else {
                    callback.onUserExistenceCallback(false);
                }
            }
        });
    }

    void createUser(User user, UserCreationCallback callback) {
        DocumentReference uidRef = usersRef.document(user.uid);
        uidRef.set(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onUserCreationCallback(true);
            }
        });
    }
}