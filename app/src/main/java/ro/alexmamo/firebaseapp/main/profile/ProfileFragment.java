package ro.alexmamo.firebaseapp.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.auth.User;
import ro.alexmamo.firebaseapp.di.AppViewModelFactory;
import ro.alexmamo.firebaseapp.main.MainActivity;

public class ProfileFragment extends DaggerFragment implements Observer<User> {
    @Inject AppViewModelFactory factory;
    private View profileFragmentView;
    private String uid;
    private TextView nameTextView, uidTextView, createdAtTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        profileFragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        uid = getUidFromMainActivity();
        initMessageTextView();
        initProfileViewModel();
        return profileFragmentView;
    }

    private String getUidFromMainActivity() {
        MainActivity mainActivity =((MainActivity) getActivity());
        if (mainActivity != null) {
            return mainActivity.getUid();
        }
        return null;
    }

    private void initMessageTextView() {
        nameTextView = profileFragmentView.findViewById(R.id.name_text_view);
        uidTextView = profileFragmentView.findViewById(R.id.uid_text_view);
        createdAtTextView = profileFragmentView.findViewById(R.id.created_at_text_view);
    }

    private void initProfileViewModel() {
        ProfileViewModel profileViewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
        profileViewModel.setUid(uid);
        profileViewModel.getUserLiveData().observe(this, this);
    }

    @Override
    public void onChanged(User user) {
        setNameTextView(user.name);
        setUidTextView(user.uid);
        setCreatedAtTextView(user.createdAt);
        hideProgressBar();
    }

    private void setNameTextView(String name) {
        String nameText = "Name: " + name;
        nameTextView.setText(nameText);
    }

    private void setUidTextView(String uid) {
        String uidText = "Uid: " + uid;
        uidTextView.setText(uidText);
    }

    private void setCreatedAtTextView(Date createdAt) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
        String date = dateFormat.format(createdAt);
        String createdAtText = "Account created at: " + date;
        createdAtTextView.setText(createdAtText);
    }

    private void hideProgressBar() {
        profileFragmentView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }
}