package com.example.productcart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.productcart.DataBase.Product;
import com.example.productcart.DataBase.ProductDataBase;

import java.util.Objects;

public class ProductUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    Button update_product_update, update_product_image_choose, update_product_view;
    EditText update_product_name, update_product_price, update_product_quantity, update_product_description, update_product_discount;
    ImageView update_product_image;

    ProductDataBase productDataBase;
    Uri product_uri;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_update);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Product View");
        // Initialize button
        update_product_update = findViewById(R.id.update_product_update);
        update_product_image_choose = findViewById(R.id.update_product_image_choose);
        update_product_view = findViewById(R.id.update_product_view);

        update_product_update.setOnClickListener(this);
        update_product_image_choose.setOnClickListener(this);
        update_product_view.setOnClickListener(this);

        // Initialize EditText
        update_product_name = findViewById(R.id.update_product_name);
        update_product_price = findViewById(R.id.update_product_price);
        update_product_quantity = findViewById(R.id.update_product_quantity);
        update_product_description = findViewById(R.id.update_product_description);
        update_product_discount = findViewById(R.id.update_product_discount);

        // Initialize Image
        update_product_image = findViewById(R.id.update_product_image);

        // Initialize DataBase
        productDataBase = ProductDataBase.getInstance(this);

        Intent intent = getIntent();
        String[] str = intent.getStringExtra("product_update").split(",");
        String name        = str[0];
        String price       = str[1];
        String quantity    = str[2];
        String description = str[3];
        String discount    = str[4];
        String image       = str[5];
        id                 = Integer.parseInt(str[6]);

        update_product_name.setText(name);
        update_product_price.setText(price);
        update_product_quantity.setText(quantity);
        update_product_description.setText(description);
        update_product_discount.setText(discount);
        Uri uri = Uri.parse(image);
        product_uri = uri;
        Glide.with(this).load(uri).error(R.drawable.default_image_background).into(update_product_image);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.update_product_update:
                    String name = update_product_name.getText().toString();
                    String price = update_product_price.getText().toString();
                    String quantity = update_product_quantity.getText().toString();
                    String description = update_product_description.getText().toString();
                    String discount = update_product_discount.getText().toString();
                    String image = product_uri.toString();

                    productDataBase.ProductDao().UpdateProduct(id,name, Float.parseFloat(price), Integer.parseInt(quantity), description, discount, image);
                    Toast.makeText(this, "update", Toast.LENGTH_SHORT).show();

                break;

            case R.id.update_product_image_choose:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, 102);
                break;

            case R.id.update_product_view:
                Intent intent1 = new Intent(ProductUpdateActivity.this, ProductViewActivity.class);
                startActivity(intent1);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 102){
            Uri uri = data.getData();
            product_uri = uri;
            update_product_image.setImageURI(uri);
        }



    }
}