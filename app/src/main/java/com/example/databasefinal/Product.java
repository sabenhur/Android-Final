package com.example.databasefinal;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "shopping")
public class Product {

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getProduct() {
        return this.mProduct;
    }

    @NonNull
    public String getPrice(){
        return this.mPrice;
    }

    @NonNull
    public String getStore(){
        return this.mStore;
    }


    @PrimaryKey//(autoincrement = true)
    @NonNull
    private String id;

    @NonNull
    @ColumnInfo(name = "product")
    private String mProduct;

    @NonNull //nyt
    @ColumnInfo(name = "price")
    private String mPrice;

    @NonNull //nyt
    @ColumnInfo(name = "store")
    private String mStore;

    public Product(String id, String product, String price, String store) {
        this.id = id;
        this.mProduct = product;
        this.mPrice = price;
        this.mStore = store;
    }

   /* public void setId(int id) {
        this.id = id;
    }*/
}
