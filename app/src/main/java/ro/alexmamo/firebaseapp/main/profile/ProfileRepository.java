package ro.alexmamo.firebaseapp.main.profile;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ro.alexmamo.firebaseapp.auth.User;

import static ro.alexmamo.firebaseapp.utils.Constants.USERS_REF;
import static ro.alexmamo.firebaseapp.utils.HelperClass.logErrorMessage;

@SuppressWarnings("ConstantConditions")
@Singleton
class ProfileRepository {
    private FirebaseAuth auth;
    private CollectionReference usersRef;
    private DocumentReference uidRef;

    @Inject
    ProfileRepository(FirebaseAuth auth, @Named(USERS_REF) CollectionReference usersRef) {
        this.auth = auth;
        this.usersRef = usersRef;
    }

    MutableLiveData<User> addUserToLiveData(String uid) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        if (uid != null) {
            uidRef = usersRef.document(uid);
        } else {
            FirebaseUser firebaseUser = auth.getCurrentUser();
            if (firebaseUser != null) {
                uidRef = usersRef.document(firebaseUser.getUid());
            }
        }
        uidRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()) {
                    User user = document.toObject(User.class);
                    userMutableLiveData.setValue(user);
                }
            } else {
                logErrorMessage(task.getException().getMessage());
            }
        });
        return userMutableLiveData;
    }
}