package ro.alexmamo.firebaseapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import ro.alexmamo.firebaseapp.data.User;

import static ro.alexmamo.firebaseapp.utils.Constants.TAG;
import static ro.alexmamo.firebaseapp.utils.Constants.WELCOME;
import static ro.alexmamo.firebaseapp.utils.Constants.YOUR_UID_IS;
import static ro.alexmamo.firebaseapp.utils.Constants.YOU_ARE_LOGGED_IN_AS;

public class HelperClass {
    public static void logErrorMessage(String errorMessage) {
        Log.d(TAG, errorMessage);
    }

    public static String getProductNameFirstLetterCapital(String productName) {
        return productName.substring(0, 1).toUpperCase() + productName.substring(1);
    }

    public static void displayWelcomeMessageIfUserIsNew(User user, Context context) {
        if (user.isNew) {
            String welcomeMessage = WELCOME + user.name;
            Toast.makeText(context, welcomeMessage, Toast.LENGTH_LONG).show();
        }
    }

    public static String getLoggedInMessage(String name) {
        return YOU_ARE_LOGGED_IN_AS + name;
    }

    public static String getUidMessage(String uid) {
        return YOUR_UID_IS + uid;
    }
}