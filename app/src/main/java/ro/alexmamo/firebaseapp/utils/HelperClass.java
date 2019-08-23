package ro.alexmamo.firebaseapp.utils;

import android.util.Log;

import static ro.alexmamo.firebaseapp.utils.Constants.TAG;

public class HelperClass {
    public static void logErrorMessage(String errorMessage) {
        Log.d(TAG, errorMessage);
    }

    public static String getProductNameFirstLetterCapital(String productName) {
        return productName.substring(0, 1).toUpperCase() + productName.substring(1);
    }
}