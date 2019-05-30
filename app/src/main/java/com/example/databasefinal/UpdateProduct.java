package com.example.databasefinal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class UpdateProduct extends AppCompatActivity {

    public static final String PRODUCT_ID = "product_id";
    static final String UPDATED_PRODUCT = "product_text";
    static final String UPDATED_PRICE = "price_text";
    static final String UPDATED_STORE = "store_text";
    private EditText etProduct;
    private EditText etPrice;
    private EditText etStore;
    private Bundle bundle;
    private String productId;
    private LiveData<Product> products;

    private String updatedProdut = "";
    private String updatedPrice = "";
    private String updatedStore = "";

    UpdateProductViewModel productModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etProduct = findViewById(R.id.etProduct);
        etPrice = findViewById(R.id.etPrice);
        etStore = findViewById(R.id.etStore);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            productId = bundle.getString("product_id");
        }

        productModel = ViewModelProviders.of(this).get(UpdateProductViewModel.class);
        products = productModel.getProduct(productId);
        products.observe(this, new Observer<Product>() {
            @Override
            public void onChanged(@Nullable Product product) {

                etProduct.setText(product.getProduct());
                etPrice.setText(product.getPrice());
                etStore.setText(product.getStore());
            }
        });
    }

    public void updateProduct(View view) {
        if (etPrice.getText().toString().isEmpty()) {
            updatedPrice = "Price";
        } else {
            updatedPrice = etPrice.getText().toString();
        }
        if (etStore.getText().toString().isEmpty()) {
            updatedStore = "Store Name";
        } else {
            updatedStore = etStore.getText().toString();
        }
        String updatedProduct = etProduct.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(PRODUCT_ID, productId);
        resultIntent.putExtra(UPDATED_PRODUCT, updatedProduct);
        resultIntent.putExtra(UPDATED_PRICE, updatedPrice);
        resultIntent.putExtra(UPDATED_STORE, updatedStore);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelUpdate(View view) {
        finish();
    }
}
