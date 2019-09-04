package ro.alexmamo.firebaseapp.utils;

import android.util.Log;

import static ro.alexmamo.firebaseapp.utils.Constants.TAG;
import static ro.alexmamo.firebaseapp.utils.Constants.YOUR_UID_IS;
import static ro.alexmamo.firebaseapp.utils.Constants.YOU_ARE_LOGGED_IN_AS;

public class HelperClass {
    public static void logErrorMessage(String errorMessage) {
        Log.d(TAG, errorMessage);
    }

    public static String getProductNameFirstLetterCapital(String productName) {
        return productName.substring(0, 1).toUpperCase() + productName.substring(1);
    }

    public static String getLoggedInMessage(String name) {
        return YOU_ARE_LOGGED_IN_AS + name;
    }

    public static String getUidMessage(String uid) {
        return YOUR_UID_IS + uid;
    }
}