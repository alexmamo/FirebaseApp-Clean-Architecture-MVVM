package ro.alexmamo.firebaseapp.splash;

import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ro.alexmamo.firebaseapp.data.User;

import static ro.alexmamo.firebaseapp.utils.Actions.gotoAuthActivity;
import static ro.alexmamo.firebaseapp.utils.Actions.gotoMainActivity;
import static ro.alexmamo.firebaseapp.utils.HelperClass.logErrorMessage;

public class SplashActivity extends DaggerAppCompatActivity {
    @Inject SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUid();
    }

    private void getUid() {
        String uid = splashViewModel.getUidIfUserIsAuthenticated();
        if (uid != null) {
            getUserData(uid);
        } else {
            gotoAuthActivity(this);
            finish();
        }
    }

    private void getUserData(String uid) {
        splashViewModel.setUid(uid);
        splashViewModel.userLiveData.observe(this, dataOrException -> {
            if (dataOrException.data != null) {
                User user = dataOrException.data;
                gotoMainActivity(this, user);
                finish();
            }

            if (dataOrException.exception != null) {
                logErrorMessage(dataOrException.exception.getMessage());
            }
        });
    }
}