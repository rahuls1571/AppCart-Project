package com.example.productcart.DataBase;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Product")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int Product_Id;

    @ColumnInfo(name = "Name")
    private String Product_Name;

    @ColumnInfo(name = "Description")
    private String Description;

    @ColumnInfo(name = "Image")//, typeAffinity = ColumnInfo.BLOB)
    private String Image;

    @ColumnInfo(name = "Quantity")
    private int Quantity;

    @ColumnInfo(name = "Price")
    private float Price;

    @ColumnInfo(name = "Discount")
    private String Discount;


    public Product(){

    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public int getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(int product_Id) {
        Product_Id = product_Id;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
