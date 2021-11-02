package com.thesaugat.ecommerce.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("price")
@Expose
private Integer price;
@SerializedName("discount_price")
@Expose
private Integer discountPrice;
@SerializedName("description")
@Expose
private String description;
@SerializedName("quantity")
@Expose
private String quantity;
@SerializedName("images")
@Expose
private List<String> images = null;
@SerializedName("categories")
@Expose
private List<Integer> categories = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Integer getPrice() {
return price;
}

public void setPrice(Integer price) {
this.price = price;
}

public Integer getDiscountPrice() {
return discountPrice;
}

public void setDiscountPrice(Integer discountPrice) {
this.discountPrice = discountPrice;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getQuantity() {
return quantity;
}

public void setQuantity(String quantity) {
this.quantity = quantity;
}

public List<String> getImages() {
return images;
}

public void setImages(List<String> images) {
this.images = images;
}

public List<Integer> getCategories() {
return categories;
}

public void setCategories(List<Integer> categories) {
this.categories = categories;
}

}