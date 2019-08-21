package ro.alexmamo.firebaseapp.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.auth.User;
import ro.alexmamo.firebaseapp.main.MainActivity;

public class ProfileFragment extends DaggerFragment implements Observer<User> {
    @Inject ProfileViewModel profileViewModel;
    private View profileFragmentView;
    private TextView nameTextView, uidTextView, createdAtTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        profileFragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews();
        String uid = getUidFromMainActivity();
        setUserIdInProfileViewModel(uid);
        getUserFromDatabase();
        return profileFragmentView;
    }

    private void initViews() {
        nameTextView = profileFragmentView.findViewById(R.id.name_text_view);
        uidTextView = profileFragmentView.findViewById(R.id.uid_text_view);
        createdAtTextView = profileFragmentView.findViewById(R.id.created_at_text_view);
    }

    private String getUidFromMainActivity() {
        MainActivity mainActivity =((MainActivity) getActivity());
        if (mainActivity != null) {
            return mainActivity.getUidFromIntent();
        }
        return null;
    }

    private void setUserIdInProfileViewModel(String uid) {
        profileViewModel.setUid(uid);
    }

    private void getUserFromDatabase() {
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