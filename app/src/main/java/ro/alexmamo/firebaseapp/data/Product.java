package ro.alexmamo.firebaseapp.data;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

import static ro.alexmamo.firebaseapp.utils.HelperClass.getProductNameFirstLetterCapital;

public class Product {
    public String id, name;
    @SuppressWarnings("WeakerAccess")
    public double price;
    @SuppressWarnings("unused")
    @ServerTimestamp
    public Date date;

    @SuppressWarnings("unused")
    public Product() {}

    @SuppressWarnings("unused")
    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return getProductNameFirstLetterCapital(name);
    }

    public String getPrice() {
        return String.valueOf(price);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}