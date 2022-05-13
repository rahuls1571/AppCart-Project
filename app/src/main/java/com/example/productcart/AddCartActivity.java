package com.example.productcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productcart.DataBase.Product;
import com.example.productcart.DataBase.ProductDataBase;
import com.example.productcart.ProductAdapter.AddCartAdapter;
import com.example.productcart.ProductAdapter.ProductAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AddCartActivity extends AppCompatActivity implements EstimateDetails {

    List<Product> productList = new ArrayList<>();
    List<Product> cartList = new ArrayList<>();
    List<String> product_prices = new ArrayList<>();
    List<String> product_discounts = new ArrayList<>();
    List<String> product_taxs = new ArrayList<>();
    ProductDataBase productDataBase;
    AddCartAdapter addCartAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    TextView product_total_price, product_total_discount, product_total_tax, product_total_cost;
    float Total_price = 0.0f;
    float Total_discount = 0.0f;
    float Total_tax = 0.0f;
    float Total_cost = 0.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cart);

        recyclerView = findViewById(R.id.recycle_view_addcart);
        product_total_price = findViewById(R.id.product_total_price);
        product_total_discount = findViewById(R.id.product_total_discount);
        product_total_tax = findViewById(R.id.product_total_tax);
        product_total_cost = findViewById(R.id.product_total_cost);


        Intent intent = getIntent();
        ArrayList<Integer> set = intent.getIntegerArrayListExtra("cart");

        // Initialize DataBase
        productDataBase = ProductDataBase.getInstance(this);

        // Store Datavalue in the list view!
        productList = productDataBase.ProductDao().getAllProduct();

        for(int i=0; i<set.size(); i++){
            cartList.add(productList.get(set.get(i)));
        }


        // Initialize LinearLayoutManager
        linearLayoutManager = new LinearLayoutManager(this);
        // set layout to recycle view
        recyclerView.setLayoutManager(linearLayoutManager);

        // Initialize Adapter
        addCartAdapter = new AddCartAdapter(this,cartList , this);
        // Set Adapter
        recyclerView.setAdapter(addCartAdapter);
        addCartAdapter.notifyDataSetChanged();

    }

    @Override
    public void AddCartDetails(List<String> product_price, List<String> product_discount, List<String> product_tax) {
        product_prices.clear();
        product_taxs.clear();
        product_discounts.clear();

        if(product_price != null && product_discount != null && product_tax != null){


            product_prices.addAll(product_price);
            product_discounts.addAll(product_discount);
            product_taxs.addAll(product_tax);

            Total_price = 0.0f;
            Total_tax = 0.0f;
            Total_discount = 0.0f;
            Total_cost = 0.0f;

            for(int i=0; i<product_price.size(); i++){
                Total_price +=  Float.parseFloat(product_prices.get(i));
                Total_discount += Float.parseFloat(product_discounts.get(i));
                Total_tax += Float.parseFloat(product_taxs.get(i));
            }

            Total_cost = Total_price - Total_discount + Total_tax;

            product_total_price.setText(""+ Total_price);
            product_total_discount.setText(""+ Total_discount);
            product_total_tax.setText(""+ Total_tax);
            product_total_cost.setText(""+ Total_cost);

        }
    }
}