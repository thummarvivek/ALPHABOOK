
package com.example.alphabook.api.category;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class CategoryResult {

    @SerializedName("category")
    @Expose
    private List<Category> category = null;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

}
