package com.example.productcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.productcart.DataBase.Product;
import com.example.productcart.DataBase.ProductDataBase;
import com.example.productcart.ProductAdapter.ProductAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ProductViewActivity extends AppCompatActivity implements AddCartInterface {

    List<Product> productList = new ArrayList<>();
    ProductDataBase productDataBase;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Button add_cart_button;
    int len = 0;
    HashSet<Integer> product_addcart_pos = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Product View");
        // Initialize Recycle view
        recyclerView = findViewById(R.id.recycle_view);
        // Button
        add_cart_button = findViewById(R.id.view_cart);
        // Initialize DataBase
        productDataBase = ProductDataBase.getInstance(this);
        // Store Datavalue in the list view!
        productList = productDataBase.ProductDao().getAllProduct();
        // Initialize LinearLayoutManager
        linearLayoutManager = new LinearLayoutManager(this);
        // set layout to recycle view
        recyclerView.setLayoutManager(linearLayoutManager);

        // Initialize Adapter
   productAdapter = new ProductAdapter(this,productList, this);

        // Set Adapter
        recyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();

        add_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductViewActivity.this,AddCartActivity.class);
                intent.putIntegerArrayListExtra("cart",new ArrayList<>(product_addcart_pos));
                startActivity(intent);
            }
        });


    }

    @Override
    public void CartList(HashSet<Integer> productList) {
        product_addcart_pos = productList;
    }

}