package ro.alexmamo.firebaseapp.main.profile;

import android.os.Bundle;

import androidx.annotation.Nullable;

import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.auth.User;
import ro.alexmamo.firebaseapp.databinding.FragmentProfileBinding;
import ro.alexmamo.firebaseapp.main.BaseFragment;
import ro.alexmamo.firebaseapp.main.MainActivity;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        User user = getUserFromMainActivity();
        if (user != null) {
            bindUserDataToViews(user);
        }
    }

    private User getUserFromMainActivity() {
        MainActivity mainActivity =((MainActivity) getActivity());
        if (mainActivity != null) {
            return mainActivity.getUserFromIntent();
        }
        return null;
    }

    private void bindUserDataToViews(User user) {
        dataBinding.setUser(user);
    }
}