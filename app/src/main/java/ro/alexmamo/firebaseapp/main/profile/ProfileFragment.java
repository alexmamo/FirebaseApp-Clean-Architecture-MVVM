package ro.alexmamo.firebaseapp.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.auth.User;
import ro.alexmamo.firebaseapp.databinding.FragmentProfileBinding;
import ro.alexmamo.firebaseapp.main.MainActivity;

public class ProfileFragment extends DaggerFragment implements Observer<User> {
    @Inject ProfileViewModel profileViewModel;
    private FragmentProfileBinding fragmentProfileBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, null, false);
        View rootView = fragmentProfileBinding.getRoot();
        String uid = getUidFromMainActivity();
        setUserIdInProfileViewModel(uid);
        getUserFromDatabase();
        return rootView;
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
        profileViewModel.userLiveData.observe(this, this);
    }

    @Override
    public void onChanged(User user) {
        fragmentProfileBinding.setUser(user);
        hideProgressBar();
    }

    private void hideProgressBar() {
        fragmentProfileBinding.progressBar.setVisibility(View.GONE);
    }
}