package ro.alexmamo.firebaseapp.main.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.alexmamo.firebaseapp.R;

public class ProductsAdapter extends PagedListAdapter<Product, ProductsAdapter.ProductViewHolder> {
    private Context context;
    private OnProductClickListener onProductClickListener;

    ProductsAdapter(Context context, OnProductClickListener onProductClickListener) {
        super(diffCallback);
        this.context = context;
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(itemView, onProductClickListener);
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

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView, priceTextView;
        OnProductClickListener onProductClickListener;

        ProductViewHolder(View itemView, OnProductClickListener onProductClickListener) {
            super(itemView);
            this.onProductClickListener = onProductClickListener;
            itemView.setOnClickListener(this);
            initViews();
        }

        void initViews() {
            nameTextView = itemView.findViewById(R.id.name_text_view);
            priceTextView = itemView.findViewById(R.id.price_text_view);
        }

        void bindProduct(Product product) {
            setNameTextView(product.name);
            setPriceTextView(String.valueOf(product.price));
        }

        void setNameTextView(String name) {
            String nameFirstLetterCapital = name.substring(0, 1).toUpperCase() + name.substring(1);
            nameTextView.setText(nameFirstLetterCapital);
        }

        void setPriceTextView(String price) {
            priceTextView.setText(price);
        }

        @Override
        public void onClick(View v) {
            List<Product> productList = getCurrentList();
            if(productList != null) {
                int position = getAdapterPosition();
                Product clickedProduct = productList.get(position);
                onProductClickListener.onProductClick(clickedProduct);
            }
        }
    }

    interface OnProductClickListener {
        void onProductClick(Product clickedProduct);
    }
}