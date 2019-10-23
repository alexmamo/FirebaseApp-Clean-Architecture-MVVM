package ro.alexmamo.firebaseapp.main.products;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import ro.alexmamo.firebaseapp.databinding.ProductDataBinding;

import static ro.alexmamo.firebaseapp.databinding.ProductDataBinding.*;

public class ProductsAdapter extends PagedListAdapter<Product, ProductsAdapter.ProductViewHolder> {
    private OnProductClickListener onProductClickListener;

    ProductsAdapter(OnProductClickListener onProductClickListener) {
        super(diffCallback);
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProductDataBinding productDataBinding = inflate(layoutInflater);
        return new ProductViewHolder(productDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder productViewHolder, int position) {
        Product product = getItem(position);
        if (product != null) {
            productViewHolder.bindProduct(product);
        }
    }

    private static DiffUtil.ItemCallback<Product> diffCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(Product oldProduct, Product newProduct) {
            return oldProduct.id.equals(newProduct.id);
        }

        @Override
        public boolean areContentsTheSame(Product oldProduct, @NonNull Product newProduct) {
            return oldProduct.equals(newProduct);
        }
    };

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ProductDataBinding productDataBinding;

        ProductViewHolder(ProductDataBinding productDataBinding) {
            super(productDataBinding.getRoot());
            this.productDataBinding = productDataBinding;
        }

        void bindProduct(Product product) {
            productDataBinding.setProduct(product);
            productDataBinding.setOnProductClickListener(onProductClickListener);
        }
    }

    public interface OnProductClickListener {
        void onProductClick(Product clickedProduct);
    }
}