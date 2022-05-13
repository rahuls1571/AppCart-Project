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

import com.example.productcart.DataBase.Product;
import com.example.productcart.DataBase.ProductDataBase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button product_save, product_image_choose, product_view;
    EditText product_name, product_price, product_quantity, product_description, product_discount;
    ImageView product_image;
    ProductDataBase productDataBase;
    Uri product_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize button
        product_save = findViewById(R.id.product_save);
        product_image_choose = findViewById(R.id.product_image_choose);
        product_view = findViewById(R.id.product_view);

        product_save.setOnClickListener(this);
        product_image_choose.setOnClickListener(this);
        product_view.setOnClickListener(this);

        // Initialize EditText
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_quantity = findViewById(R.id.product_quantity);
        product_description = findViewById(R.id.product_description);
        product_discount = findViewById(R.id.product_discount);

        // Initialize Image
        product_image = findViewById(R.id.product_image);

        // Initialize DataBase
        productDataBase = ProductDataBase.getInstance(this);


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.product_save:
                String name = product_name.getText().toString();
                String price = product_price.getText().toString();
                String quantity = product_quantity.getText().toString();
                String description = product_description.getText().toString();
                String discount = product_discount.getText().toString();



                if( !TextUtils.isEmpty(name) && !TextUtils.isEmpty(price)
                    && !TextUtils.isEmpty(quantity) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(discount) && product_uri != null
                ) {
                    // save into Room database!
                    Product product = new Product();
                    String image = product_uri.toString();

                    product.setProduct_Name(name);
                    product.setPrice(Float.parseFloat(price));
                    product.setQuantity(Integer.parseInt(quantity));
                    product.setDescription(description);
                    product.setDiscount(discount);
                    product.setImage(image);

                    // Insert!
                    productDataBase.ProductDao().InsertProduct(product);
                    Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();

                    product_name.setText("");
                    product_price.setText("");
                    product_quantity.setText("");
                    product_description.setText("");
                    product_discount.setText("");
                    product_image.setImageResource(R.drawable.default_image_background);

                }

                else if( TextUtils.isEmpty(name) || TextUtils.isEmpty(price)
                        || TextUtils.isEmpty(quantity) || TextUtils.isEmpty(description) || !TextUtils.isEmpty(discount) || product_uri == null
                ) {
                    Toast.makeText(this, "Empty Check Details", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.product_image_choose:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, 102);
                break;

            case R.id.product_view:
                Intent intent1 = new Intent(MainActivity.this, ProductViewActivity.class);
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
                    product_image.setImageURI(uri);

            }



    }
}