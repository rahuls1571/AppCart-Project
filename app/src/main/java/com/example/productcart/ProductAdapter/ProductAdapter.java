package com.example.productcart.ProductAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.productcart.AddCartActivity;
import com.example.productcart.AddCartInterface;
import com.example.productcart.DataBase.Product;
import com.example.productcart.DataBase.ProductDataBase;
import com.example.productcart.ProductUpdateActivity;
import com.example.productcart.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    // Initialize variable
    private List<Product> productList;
    private HashSet<Integer> add_cart_list = new HashSet<>();
    private Activity context;
    private ProductDataBase productDataBase;
    private AddCartInterface addCartInterface;
    // Created Constructor
    public ProductAdapter(Activity context, List<Product> productList, AddCartInterface addCartInterface){
        this.context = context;
        this.productList = productList;
        this.addCartInterface = addCartInterface;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Initialize Product data.
        Product product = productList.get(position);

        // Set View to Product View Layout!
        holder.room_product_name.setText("Name: "+ product.getProduct_Name());
        holder.room_product_price.setText("Price: " + product.getPrice());
        holder.room_product_quantity.setText("Quantity: "+ String.valueOf(product.getQuantity()));
        holder.room_product_description.setText("Description: "+ product.getDescription());
        holder.room_product_discount.setText("Discount: " +product.getDiscount() +"%");
        // Image Upload!
        Uri uri = Uri.parse(product.getImage());
        Glide.with(context).load(uri).error(R.drawable.default_image_background).into(holder.room_product_image);

        // Initialize DataBase
        productDataBase = ProductDataBase.getInstance(context);


        // Product delete!
        holder.room_product_delete.setOnClickListener(view -> {
            Product_Delete_DialogBox(holder);
        });


        // Product Update!
        holder.room_product_update.setOnClickListener(view -> {

                Intent intent = new Intent(context, ProductUpdateActivity.class);
                String data = product.getProduct_Name() +","+ product.getPrice() +","+ product.getQuantity() +","+ product.getDescription() +"," + product.getDiscount() +","+
                        product.getImage() + "," + product.getProduct_Id();
                intent.putExtra("product_update",data);
                context.startActivity(intent);

        });


        // Product Add Cart!
        holder.room_product_addcart.setOnClickListener(view -> {
            if(!add_cart_list.contains(position)){
                add_cart_list.add(position);
                addCartInterface.CartList(add_cart_list);
                Toast.makeText(context, "Cart Add" + add_cart_list.size(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Already Cart Add", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Initialize variable
        TextView room_product_name,room_product_price, room_product_quantity, room_product_description, room_product_discount;
        ImageView room_product_image;
        Button room_product_addcart, room_product_update, room_product_delete;

        public ViewHolder(@NonNull View v) {
            super(v);
            room_product_name        = v.findViewById(R.id.room_product_name);
            room_product_price       = v.findViewById(R.id.room_product_price);
            room_product_quantity    = v.findViewById(R.id.room_product_quantity);
            room_product_description = v.findViewById(R.id.room_product_description);
            room_product_discount    = v.findViewById(R.id.room_product_discount);
            room_product_image       = v.findViewById(R.id.room_product_image);
            room_product_addcart     = v.findViewById(R.id.room_product_addcart);
            room_product_update        = v.findViewById(R.id.room_product_update);
            room_product_delete      = v.findViewById(R.id.room_product_delete);


        }
    }


    public void Product_Delete_DialogBox(ViewHolder holder){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Product Delete");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", (dialog, which) -> {
            // Do nothing but close the dialog
            Product p = productList.get(holder.getAdapterPosition());
            // Delete form database!
            productDataBase.ProductDao().DeleteProduct(p);
            // Notify when data is deleted!
            int pos = holder.getAdapterPosition();
            productList.remove(pos);
            if(add_cart_list != null){
                add_cart_list.remove(pos);
                addCartInterface.CartList(add_cart_list);
            }
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, productList.size());

            dialog.dismiss();
        });

        builder.setNegativeButton("NO", (dialog, which) -> {
            // Do nothing
            dialog.dismiss();
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
