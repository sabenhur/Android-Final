package com.example.databasefinal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements ProductListAdapter.OnDeleteClickListener {

    private static final int NEW_PRODUCT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_PRODUCT_ACTIVITY_REQUEST_CODE = 2;
    private String TAG = this.getClass().getSimpleName();
    private ProductViewModel productViewModel;
    private ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        productListAdapter = new ProductListAdapter(this, this);
        recyclerView.setAdapter(productListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewProductActivity.class);
                startActivityForResult(intent, NEW_PRODUCT_ACTIVITY_REQUEST_CODE);
            }
        });

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        productViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> productss) {
                productListAdapter.setProduct(productss);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PRODUCT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to insert Product
            final String product_id = UUID.randomUUID().toString();
            Product product = new Product(product_id, data.getStringExtra(NewProductActivity.PRODUCT_ADDED), data.getStringExtra(NewProductActivity.PRICE_ADDED), data.getStringExtra(NewProductActivity.STORE_ADDED));
            productViewModel.insert(product);

            Toast.makeText(
                    getApplicationContext(),
                    "Product Saved",
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == UPDATE_PRODUCT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to update the Product
            Product product = new Product(
                    data.getStringExtra(UpdateProduct.PRODUCT_ID),
                    data.getStringExtra(UpdateProduct.UPDATED_PRODUCT),
                    data.getStringExtra(UpdateProduct.UPDATED_PRICE),
                    data.getStringExtra(UpdateProduct.UPDATED_STORE));
            productViewModel.update(product);

            Toast.makeText(
                    getApplicationContext(),
                    "Product Updated",
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Couldn't Save",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void OnDeleteClickListener(Product myProduct) {
        // Code for Delete operation
        productViewModel.delete(myProduct);
    }
}

