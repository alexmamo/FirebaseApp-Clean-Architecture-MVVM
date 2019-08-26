package ro.alexmamo.firebaseapp.main.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ro.alexmamo.firebaseapp.auth.User;

public class ProfileViewModel extends ViewModel {
    private ProfileRepository profileRepository;
    MutableLiveData<User> userLiveData;

    @Inject
    ProfileViewModel(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    void setUid(String uid) {
        userLiveData = profileRepository.addUserToLiveData(uid);
    }
}