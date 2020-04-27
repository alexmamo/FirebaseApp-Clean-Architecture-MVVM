package ro.alexmamo.firebaseapp.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<T extends ViewDataBinding> extends DaggerFragment {
    protected T dataBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        dataBinding = DataBindingUtil.inflate(inflater, getFragmentLayout(), null, false);
        return dataBinding.getRoot();
    }

    protected abstract int getFragmentLayout();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataBinding = null;
    }
}