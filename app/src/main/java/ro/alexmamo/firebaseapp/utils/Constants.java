package ro.alexmamo.firebaseapp.utils;

import com.google.firebase.firestore.Query;

public interface Constants {
    String TAG = "FirebaseAppTag";
    int RC_SIGN_IN = 123;
    int ITEMS_PER_PAGE = 3;
    String ESCAPE_CHARACTER = "\uf8ff";
    String USERS_COLLECTION = "users";
    String USERS_REF = "usersRef";
    String PRODUCTS_COLLECTION = "products";
    String PRODUCTS_REF = "productsRef";
    String PRODUCT_NAME_PROPERTY = "name";
    Query.Direction ASCENDING = Query.Direction.ASCENDING;
    String YOU_ARE_LOGGED_IN_AS = "You are logged in as: ";
}