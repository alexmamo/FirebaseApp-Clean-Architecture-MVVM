package ro.alexmamo.firebaseapp.di.auth;

import android.app.Application;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ro.alexmamo.firebaseapp.R;

@Module
public class AuthModule {
    @Singleton
    @Provides
    static GoogleSignInOptions provideGoogleSignInOptions(Application application) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    @Singleton
    @Provides
    static GoogleSignInClient provideGoogleSignInClient(Application application, GoogleSignInOptions googleSignInOptions) {
        return GoogleSignIn.getClient(application, googleSignInOptions);
    }
}