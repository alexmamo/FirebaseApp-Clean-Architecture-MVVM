package ro.alexmamo.firebaseapp.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideBindingAdapters {
    @BindingAdapter("profilePhotoImageView")
    public static void setProfilePhotoToImageView(ImageView profilePhotoImageView, String profilePhotoUrl) {
        Context context = profilePhotoImageView.getContext();
        Glide.with(context)
                .load(profilePhotoUrl)
                .apply(RequestOptions.circleCropTransform())
                .apply(new RequestOptions().override(250, 250))
                .into(profilePhotoImageView);
    }
}