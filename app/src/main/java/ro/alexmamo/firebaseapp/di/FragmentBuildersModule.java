package ro.alexmamo.firebaseapp.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ro.alexmamo.firebaseapp.main.main.MainFragment;
import ro.alexmamo.firebaseapp.main.products.ProductsFragment;
import ro.alexmamo.firebaseapp.main.profile.ProfileFragment;

@Module
@SuppressWarnings("unused")
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract ProductsFragment contributeProductsFragment();
}