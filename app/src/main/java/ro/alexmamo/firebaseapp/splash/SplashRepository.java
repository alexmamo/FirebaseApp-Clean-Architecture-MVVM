package ro.alexmamo.firebaseapp.splash;

import androidx.lifecycle.MutableLiveData;

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
    private CollectionReference usersRef;

    @Inject
    SplashRepository(@Named(USERS_REF) CollectionReference usersRef) {
        this.usersRef = usersRef;
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