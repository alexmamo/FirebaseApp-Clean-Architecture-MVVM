package ro.alexmamo.firebaseapp.main.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.main.MainActivity;

import static ro.alexmamo.firebaseapp.utils.Constants.YOU_ARE_LOGGED_IN_AS;

public class MainFragment extends Fragment {
    private View mainFragmentView;
    private TextView nameTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mainFragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        initMessageTextView();
        String name = getNameFromMainActivity();
        setNameTextView(name);
        return mainFragmentView;
    }

    private String getNameFromMainActivity() {
        MainActivity mainActivity =((MainActivity) getActivity());
        if (mainActivity != null) {
            return mainActivity.getNameFromIntent();
        }
        return null;
    }

    private void initMessageTextView() {
        nameTextView = mainFragmentView.findViewById(R.id.name_text_view);
    }

    private void setNameTextView(String name) {
        String message = YOU_ARE_LOGGED_IN_AS + name;
        nameTextView.setText(message);
    }
}