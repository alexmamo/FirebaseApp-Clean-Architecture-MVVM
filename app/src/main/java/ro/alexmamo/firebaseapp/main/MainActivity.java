package ro.alexmamo.firebaseapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.auth.AuthActivity;
import ro.alexmamo.firebaseapp.auth.User;
import ro.alexmamo.firebaseapp.databinding.ActivityMainBinding;
import ro.alexmamo.firebaseapp.databinding.NavHeaderBinding;

public class MainActivity  extends DaggerAppCompatActivity implements FirebaseAuth.AuthStateListener,
        NavigationView.OnNavigationItemSelectedListener {
    @Inject GoogleSignInClient googleSignInClient;
    @Inject FirebaseAuth auth;
    @Inject RequestManager requestManager;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        User user = getUserFromIntent();
        bindUserDataToHeaderView(user);
        initToolBar();
        initNavigationDrawer();
    }

    public User getUserFromIntent() {
        return (User) getIntent().getSerializableExtra("user");
    }

    public void bindUserDataToHeaderView(User user) {
        View headerView = activityMainBinding.navigationView.getHeaderView(0);
        NavHeaderBinding navHeaderBinding = DataBindingUtil.bind(headerView);
        if (navHeaderBinding != null) {
            navHeaderBinding.setUser(user);

            requestManager.load(user.photoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(new RequestOptions().override(250, 250))
                    .into(navHeaderBinding.profileUrlImageView);
        }
    }

    private void initToolBar() {
        Toolbar toolbar = activityMainBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initNavigationDrawer() {
        drawerLayout = activityMainBinding.drawerLayout;
        NavigationView navigationView = activityMainBinding.navigationView;
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();

        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                navController.navigate(R.id.profile_fragment);
                break;

            case R.id.nav_products:
                navController.navigate(R.id.products_fragment);
                break;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, drawerLayout);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            goToAuthActivity();
        }
    }

    private void goToAuthActivity() {
        Intent intent = new Intent(MainActivity.this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    private void signOut() {
        singOutFirebase();
        signOutGoogle();
    }

    private void singOutFirebase() {
        auth.signOut();
    }

    private void signOutGoogle() {
        googleSignInClient.signOut();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.sign_out_button) {
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}