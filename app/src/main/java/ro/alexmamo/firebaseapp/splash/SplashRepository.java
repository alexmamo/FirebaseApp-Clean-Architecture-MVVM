package ro.alexmamo.firebaseapp.splash;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ro.alexmamo.firebaseapp.data.DataOrException;
import ro.alexmamo.firebaseapp.data.User;

import static ro.alexmamo.firebaseapp.utils.Constants.USERS_REF;

@SuppressWarnings("ConstantConditions")
@Singleton
class SplashRepository {
    private FirebaseAuth auth;
    private CollectionReference usersRef;

    @Inject
    SplashRepository(FirebaseAuth auth, @Named(USERS_REF) CollectionReference usersRef) {
        this.auth = auth;
        this.usersRef = usersRef;
    }

    String getUidIfUserIsAuthenticatedInFirebase() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            return firebaseUser.getUid();
        } else {
            return null;
        }
    }

    MutableLiveData<DataOrException<User, Exception>> getUserFromFirestore(String uid) {
        MutableLiveData<DataOrException<User, Exception>> userMutableLiveData = new MutableLiveData<>();
        usersRef.document(uid).get().addOnCompleteListener(userTask -> {
            DataOrException<User, Exception> dataOrException = new DataOrException<>();
            if (userTask.isSuccessful()) {
                DocumentSnapshot userDoc = userTask.getResult();
                if (userDoc.exists()) {
                    dataOrException.data = userDoc.toObject(User.class);
                }
            } else {
                dataOrException.exception = userTask.getException();
            }
            userMutableLiveData.setValue(dataOrException);
        });
        return userMutableLiveData;
    }
}