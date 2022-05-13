package com.example.productcart;

import java.util.List;

public interface EstimateDetails {
    void AddCartDetails(List<String> product_price, List<String> product_discount, List<String> product_tax);
}
