package ro.alexmamo.firebaseapp.auth;

interface AuthCallback {
    void onAuthCallback(User user, boolean isNewUser);
}