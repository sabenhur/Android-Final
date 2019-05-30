package com.example.databasefinal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private ProductDao productDao;
    private ProductRoomDatabase productDB;
    private LiveData<List<Product>> mAllProducts;

    public ProductViewModel(Application application) {
        super(application);

        productDB = ProductRoomDatabase.getDatabase(application);
        productDao = productDB.productDao();
        mAllProducts = productDao.getAllProducts();
    }

    public void insert(Product product) {
        new InsertAsyncTask(productDao).execute(product);
    }

    LiveData<List<Product>> getAllProducts() {
        return mAllProducts;
    }

    public void update(Product product) {
        new UpdateAsyncTask(productDao).execute(product);
    }

    public void delete(Product product) {
        new DeleteAsyncTask(productDao).execute(product);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class OperationsAsyncTask extends AsyncTask<Product, Void, Void> {

        ProductDao mAsyncTaskDao;

        OperationsAsyncTask(ProductDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask {

        InsertAsyncTask(ProductDao mProductDao) {
            super(mProductDao);
        }

        @Override
        protected Void doInBackground(Product... products) {
            mAsyncTaskDao.insert(products[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends OperationsAsyncTask {

        UpdateAsyncTask(ProductDao productDao) {
            super(productDao);
        }

        @Override
        protected Void doInBackground(Product... products) {
            mAsyncTaskDao.update(products[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationsAsyncTask {

        public DeleteAsyncTask(ProductDao productDao) {
            super(productDao);
        }

        @Override
        protected Void doInBackground(Product... products) {
            mAsyncTaskDao.delete(products[0]);
            return null;
        }
    }

}
