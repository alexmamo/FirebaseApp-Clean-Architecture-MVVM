package ro.alexmamo.firebaseapp.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import ro.alexmamo.firebaseapp.BaseApplication;
import ro.alexmamo.firebaseapp.di.auth.AuthModule;
import ro.alexmamo.firebaseapp.di.main.products.ProductsModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                AppModule.class,
                ActivityBuildersModule.class,
                AppViewModelModule.class,
                AuthModule.class,
                ProductsModule.class
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}