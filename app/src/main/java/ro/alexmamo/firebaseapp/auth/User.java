package ro.alexmamo.firebaseapp.auth;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class User implements Serializable {
    @SuppressWarnings("WeakerAccess")
    public String uid;
    public String name;
    @SuppressWarnings("WeakerAccess")
    public String photoUrl;
    @ServerTimestamp
    @SuppressWarnings("WeakerAccess")
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

    @Exclude
    public String getUid() {
        return "Uid: " + uid;
    }

    @Exclude
    public String getName() {
        return "Name: " + name;
    }

    @Exclude
    public String getCreatedAt() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
        String date = dateFormat.format(createdAt);
        return "Account created at: " + date;
    }
}