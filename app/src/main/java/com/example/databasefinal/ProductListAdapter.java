package com.example.databasefinal;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>{

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Product myProduct);
    }

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Product> mProducts;
    private OnDeleteClickListener onDeleteClickListener;

    public ProductListAdapter(Context context, OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        if (mProducts != null) {
            Product product = mProducts.get(position);
         //   holder.productName.setText(mProducts.get(position).getProduct());
         //   holder.price.setText(mProducts.get(position).getPrice());
         //   holder.productName.setText(mProducts.get(position).getStore());
            holder.setData(product.getProduct(), product.getPrice(), product.getStore(), position);
            holder.setListeners();
        } else {
            // Covers the case of data not being ready yet.
            holder.productName.setText("No Products");
            holder.price.setText("No Price");
            holder.store.setText("No Store");
        }
    }

    @Override
    public int getItemCount() {
        if (mProducts != null)
            return mProducts.size();
        else return 0;
    }

    public void setProduct(List<Product> products) {
        mProducts = products;
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView productName, price, store;
        private int mPosition;
        private ImageView imgDelete, imgEdit;

        public ProductViewHolder(View itemView) { //creates space for the text
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            store = itemView.findViewById(R.id.store);
            imgDelete = itemView.findViewById(R.id.ivRowDelete);
            imgEdit = itemView.findViewById(R.id.ivRowEdit);
        }

        public void setData(String product, String priceOfProduct, String storeName, int position) {
            productName.setText(product);
            price.setText(priceOfProduct);
            store.setText(storeName);
            mPosition = position;
        }

        public void setListeners() {
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UpdateProduct.class);
                    intent.putExtra("product_id", mProducts.get(mPosition).getId());
                    ((Activity)mContext).startActivityForResult(intent, MainActivity.UPDATE_PRODUCT_ACTIVITY_REQUEST_CODE);
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeleteClickListener != null) {
                        onDeleteClickListener.OnDeleteClickListener(mProducts.get(mPosition));
                    }
                }
            });
        }
    }

}
