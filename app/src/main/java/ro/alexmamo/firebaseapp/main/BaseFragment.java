package ro.alexmamo.firebaseapp.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    protected T dataBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        dataBinding = DataBindingUtil.inflate(inflater, getFragmentLayout(), null, false);
        return dataBinding.getRoot();
    }

    protected abstract int getFragmentLayout();
}