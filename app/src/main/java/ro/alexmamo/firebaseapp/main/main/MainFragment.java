package ro.alexmamo.firebaseapp.main.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.databinding.FragmentMainBinding;
import ro.alexmamo.firebaseapp.main.MainActivity;

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        FragmentMainBinding fragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, null, false);
        View rootView = fragmentMainBinding.getRoot();
        String name = getNameFromMainActivity();
        fragmentMainBinding.setName(name);
        return rootView;
    }

    private String getNameFromMainActivity() {
        MainActivity mainActivity =((MainActivity) getActivity());
        if (mainActivity != null) {
            return mainActivity.getNameFromIntent();
        }
        return null;
    }
}