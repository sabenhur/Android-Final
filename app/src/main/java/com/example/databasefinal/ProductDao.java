package com.example.databasefinal;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Query("SELECT * FROM shopping")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM shopping WHERE id=:productId")
    LiveData<Product> getProduct(String productId);

    @Update
    void update(Product product);

    @Delete
    int delete(Product product);
}
