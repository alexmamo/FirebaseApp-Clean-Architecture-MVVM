package ro.alexmamo.firebaseapp.utils;

import android.app.Activity;
import android.content.Intent;

import ro.alexmamo.firebaseapp.auth.AuthActivity;
import ro.alexmamo.firebaseapp.data.User;
import ro.alexmamo.firebaseapp.main.MainActivity;

import static ro.alexmamo.firebaseapp.utils.Constants.USER;

public class Actions {
    public static void gotoMainActivity(Activity activity, User user) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(USER, user);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void gotoAuthActivity(Activity activity) {
        Intent intent = new Intent(activity, AuthActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}