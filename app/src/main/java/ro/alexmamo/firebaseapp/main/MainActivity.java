package ro.alexmamo.firebaseapp.main;

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

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ro.alexmamo.firebaseapp.OnAuthStateChangeListener;
import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.data.User;
import ro.alexmamo.firebaseapp.databinding.ActivityMainBinding;
import ro.alexmamo.firebaseapp.databinding.NavHeaderBinding;

import static ro.alexmamo.firebaseapp.utils.Actions.gotoAuthActivity;
import static ro.alexmamo.firebaseapp.utils.Constants.USER;
import static ro.alexmamo.firebaseapp.utils.HelperClass.displayWelcomeMessageIfUserIsNew;

public class MainActivity extends DaggerAppCompatActivity implements OnAuthStateChangeListener,
        NavigationView.OnNavigationItemSelectedListener {
    @Inject GoogleSignInClient googleSignInClient;
    @Inject FirebaseAuth firebaseAuth;
    @Inject MainViewModel mainViewModel;
    private ActivityMainBinding activityMainBinding;
    private DrawerLayout drawerLayout;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel.setAuthStateChangeListener(this);
        User user = getUserFromIntent();
        displayWelcomeMessageIfUserIsNew(user, this);
        bindUserDataToHeaderView(user);
        initToolBar();
        initNavigationDrawer();
    }

    public User getUserFromIntent() {
        return (User) getIntent().getSerializableExtra(USER);
    }

    public void bindUserDataToHeaderView(User user) {
        View headerView = activityMainBinding.navigationView.getHeaderView(0);
        NavHeaderBinding navHeaderBinding = DataBindingUtil.bind(headerView);
        if (navHeaderBinding != null) {
            navHeaderBinding.setUser(user);
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
    public void onAuthStateChanged(boolean isUserLoggedOut) {
        gotoAuthActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainViewModel.addAuthListener();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mainViewModel.removeAuthListener();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.sign_out_button) {
            mainViewModel.signOut();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}