package ro.alexmamo.firebaseapp.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import dagger.android.support.DaggerFragment;
import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.auth.User;
import ro.alexmamo.firebaseapp.databinding.FragmentProfileBinding;
import ro.alexmamo.firebaseapp.main.MainActivity;

public class ProfileFragment extends DaggerFragment {
    private FragmentProfileBinding fragmentProfileBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, null, false);
        User user = getUserFromMainActivity();
        if (user != null) {
            bindUserDataToNameTextView(user);
        }
        return fragmentProfileBinding.getRoot();
    }

    private User getUserFromMainActivity() {
        MainActivity mainActivity =((MainActivity) getActivity());
        if (mainActivity != null) {
            return mainActivity.getUserFromIntent();
        }
        return null;
    }

    private void bindUserDataToNameTextView(User user) {
        fragmentProfileBinding.setUser(user);
    }
}