package ro.alexmamo.firebaseapp.main.products;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Product {
    public String id, name;
    double price;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}