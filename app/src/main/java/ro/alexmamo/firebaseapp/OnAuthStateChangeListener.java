package ro.alexmamo.firebaseapp;

public interface OnAuthStateChangeListener {
    void onAuthStateChanged(boolean isUserLoggedOut);
}