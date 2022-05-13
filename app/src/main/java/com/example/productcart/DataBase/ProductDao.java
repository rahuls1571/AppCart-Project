package com.example.productcart.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void InsertProduct(Product product);

    @Update
    void UpdateProduct(Product product);

    @Delete
    void DeleteProduct(Product product);


    @Query("SELECT * FROM Product ORDER BY Product_Id DESC")
    List<Product> getAllProduct();

    @Query("UPDATE Product SET Name = :name, Price = :price, Quantity = :quantity, Description = :description, Discount = :discount, Image = :image  Where Product_Id = :id")
    void UpdateProduct(int id,String name, float price, int quantity, String description, String discount, String image);


}
