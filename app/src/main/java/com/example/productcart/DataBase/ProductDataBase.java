package com.example.productcart.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Product.class}, version = 1)
//@TypeConverters({ImageBitmapString.class}) // This will convert bitmap into string and vice - versa
public abstract class ProductDataBase extends RoomDatabase {

    // Create Database Instance
    private static ProductDataBase ProductDataBase;

    private static String Product_DataBase_Name = "ProductDatabase";

    // Singleton
    public synchronized static ProductDataBase getInstance(Context context){
        if(ProductDataBase == null){
            ProductDataBase = Room.databaseBuilder(context.getApplicationContext()
                              ,ProductDataBase.class,Product_DataBase_Name)
                              .allowMainThreadQueries()
                              .fallbackToDestructiveMigration()
                              .build();
        }
        return ProductDataBase;
    }

    // Create Dao
    public abstract ProductDao ProductDao();
}
