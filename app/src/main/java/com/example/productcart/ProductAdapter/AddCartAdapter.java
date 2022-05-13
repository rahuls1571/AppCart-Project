package com.example.productcart.ProductAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.productcart.AddCartInterface;
import com.example.productcart.DataBase.Product;
import com.example.productcart.DataBase.ProductDataBase;
import com.example.productcart.EstimateDetails;
import com.example.productcart.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AddCartAdapter extends RecyclerView.Adapter<AddCartAdapter.ViewHolder> {

    private List<Product> productList;
    private List<String> discountList = new ArrayList<>();
    private List<String> price_List = new ArrayList<>();
    private List<String> taxList = new ArrayList<>();
    private Activity context;
    private ProductDataBase productDataBase;
    private EstimateDetails estimateDetails;

    public AddCartAdapter(Activity context, List<Product> productList, EstimateDetails estimateDetails){
        this.context = context;
        this.productList = productList;
        this.estimateDetails = estimateDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view_layout,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Initialize Product data.
        Product product = productList.get(position);

        // Set View to Product View Layout!
        holder.cart_product_name.setText("Name: "+ product.getProduct_Name());
        holder.cart_product_price.setText("Price: "+ String.valueOf(product.getPrice()));
        holder.cart_product_quantity.setText("Quantity: "+ String.valueOf(product.getQuantity()));
        holder.cart_product_description.setText("Description: " +product.getDescription());
        holder.cart_product_discount.setText("Discount: "+ product.getDiscount() +"%");

        float discount_price = (product.getPrice() * (Integer.parseInt(product.getDiscount())/100f));
        holder.cart_product_discount_price.setText("Discount Price: "+ discount_price);
        float tax =  (product.getPrice() - discount_price) * (0.05f);
        holder.cart_product_tax.setText("Tax 5%: " + tax);

        discountList.add( ""+ (discount_price));
        taxList.add(""+  tax );
        price_List.add(""+ product.getPrice());

        estimateDetails.AddCartDetails(price_List,discountList,taxList);

        // Image Upload!
        Uri uri = Uri.parse(product.getImage());
        Glide.with(context).load(uri).error(R.drawable.default_image_background).into(holder.cart_product_image);

        // Initialize DataBase
        productDataBase = ProductDataBase.getInstance(context);

        holder.cart_product_edit.setOnClickListener(view -> {
                Product_Edit_DialogBox(holder);
        });

        holder.cart_product_delete.setOnClickListener(view -> {
                Product_Delete_DialogBox(holder);
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Initialize variable
        TextView cart_product_name,cart_product_price, cart_product_quantity, cart_product_description, cart_product_discount, cart_product_discount_price, cart_product_tax;
        ImageView cart_product_image;
        Button cart_product_edit, cart_product_delete;

        public ViewHolder(@NonNull View v) {
            super(v);
            cart_product_name        = v.findViewById(R.id.cart_product_name);
            cart_product_price       = v.findViewById(R.id.cart_product_price);
            cart_product_quantity    = v.findViewById(R.id.cart_product_quantity);
            cart_product_description = v.findViewById(R.id.cart_product_description);
            cart_product_discount    = v.findViewById(R.id.cart_product_discount);
            cart_product_image       = v.findViewById(R.id.cart_product_image);
            cart_product_edit        = v.findViewById(R.id.cart_product_edit);
            cart_product_delete      = v.findViewById(R.id.cart_product_delete);
            cart_product_discount_price        = v.findViewById(R.id.cart_product_discountprice);
            cart_product_tax      = v.findViewById(R.id.cart_product_tax);
        }
    }

    public void Product_Edit_DialogBox(AddCartAdapter.ViewHolder holder){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_box,null);
        final EditText quantity = view.findViewById(R.id.dialog_box_quantity);
        Button btn_cancel = view.findViewById(R.id.dialog_box_cancel);
        Button btn_ok = view.findViewById(R.id.dialog_box_ok);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do nothing but close the dialog
                if(!TextUtils.isEmpty(quantity.getText().toString())){
                    Product p = productList.get(holder.getAdapterPosition());
                    // Update form database!
                    p.setQuantity(Integer.parseInt(quantity.getText().toString()));
                    productDataBase.ProductDao().UpdateProduct(p);
                    estimateDetails.AddCartDetails(price_List,discountList,taxList);
                    notifyDataSetChanged();
                }

                alertDialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void Product_Delete_DialogBox(AddCartAdapter.ViewHolder holder){
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

            discountList.remove(pos);
            taxList.remove(pos);
            price_List.remove(pos);

            estimateDetails.AddCartDetails(price_List,discountList,taxList);
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