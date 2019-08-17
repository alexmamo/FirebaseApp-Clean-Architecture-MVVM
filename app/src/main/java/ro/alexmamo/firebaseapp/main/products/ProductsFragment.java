package ro.alexmamo.firebaseapp.main.products;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ro.alexmamo.firebaseapp.R;
import ro.alexmamo.firebaseapp.di.AppViewModelFactory;

public class ProductsFragment extends DaggerFragment implements Observer<PagedList<Product>>,
        ProductsAdapter.OnProductClickListener {
    @Inject AppViewModelFactory factory;
    private View productsFragmentView;
    private RecyclerView productsRecyclerView;
    private ProductsAdapter productsAdapter;
    private ProductsViewModel viewModel;
    private SearchView searchView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        productsFragmentView = inflater.inflate(R.layout.fragment_products, container, false);
        setHasOptionsMenu(true);
        initProductsRecyclerView();
        initProductsAdapter();
        initProductsViewModel();
        loadProducts();
        return productsFragmentView;
    }

    private void initProductsRecyclerView() {
        productsRecyclerView = productsFragmentView.findViewById(R.id.products_recycler_view);
    }

    private void initProductsAdapter() {
        productsAdapter = new ProductsAdapter(getActivity(), this);
        productsRecyclerView.setAdapter(productsAdapter);
    }

    private void initProductsViewModel() {
        viewModel = new ViewModelProvider(this, factory).get(ProductsViewModel.class);
    }

    private void loadProducts() {
        viewModel.getProductPagedListLiveData().observe(this, this);
    }

    private void reloadProducts() {
        viewModel.replaceSubscription(this, null);
        loadProducts();
    }

    private void loadSearchedProducts(String searchText) {
        viewModel.replaceSubscription(this, searchText);
        loadProducts();
    }

    @Override
    public void onChanged(PagedList<Product> products) {
        productsAdapter.submitList(products);
        hideProgressBar();
    }

    private void hideProgressBar() {
        productsFragmentView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    @Override
    public void onProductClick(Product clickedProduct) {
        toastProductName(getActivity(), clickedProduct.name);
    }

    private void toastProductName(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_products, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        initSearchView(searchItem);
        initSearchViewEditText();
    }

    private void initSearchView(MenuItem searchItem) {
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnCloseListener(() -> {
            reloadProducts();
            return false;
        });
    }

    private void initSearchViewEditText() {
        EditText searchViewEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchViewEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                if (searchText.length() > 0) {
                    loadSearchedProducts(searchText.toLowerCase());
                }

                return false;
            }
        });
    }
}