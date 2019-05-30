package com.example.databasefinal;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Product.class, version = 1) 
public abstract class ProductRoomDatabase extends RoomDatabase {

    public abstract ProductDao productDao();

    private static volatile ProductRoomDatabase productRoomInstance;

    static ProductRoomDatabase getDatabase(final Context context) {
        if (productRoomInstance == null) {
            synchronized (ProductRoomDatabase.class) {
                if (productRoomInstance == null) {
                    productRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            ProductRoomDatabase.class, "product_database")
                            .build();
                }
            }
        }
        return productRoomInstance;
    }

}
