package ro.alexmamo.firebaseapp.auth;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    public String uid;
    public String name;
    @SuppressWarnings("WeakerAccess")
    public String photoUrl;
    @ServerTimestamp
    public Date createdAt;
    @Exclude
    boolean isNew, isCreated;

    @SuppressWarnings("unused")
    public User() {}

    User(String uid, String name, String photoUrl) {
        this.uid = uid;
        this.name = name;
        this.photoUrl = photoUrl;
    }
}