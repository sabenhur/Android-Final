package com.example.databasefinal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

public class UpdateProductViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private ProductDao productDao;
    private ProductRoomDatabase db;

    public UpdateProductViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "Update ViewModel");
        db = ProductRoomDatabase.getDatabase(application);
        productDao = db.productDao();
    }

    public LiveData<Product> getProduct(String productId) {
        return productDao.getProduct(productId);
    }

}
