
package com.example.alphabook.api.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("category_id")
    @Expose
    private String categoryId;

    @SerializedName("univerboard")
    @Expose
    private String univerBoard;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("sub_cat_1")
    @Expose
    private Object subCat1;
    @SerializedName("sub_cat_2")
    @Expose
    private Object subCat2;
    @SerializedName("sub_cat_3")
    @Expose
    private Object subCat3;
    @SerializedName("sub_cat_4")
    @Expose
    private Object subCat4;
    @SerializedName("sub_cat_5")
    @Expose
    private Object subCat5;
    @SerializedName("sub_cat_6")
    @Expose
    private Object subCat6;
    @SerializedName("sub_cat_7")
    @Expose
    private Object subCat7;
    @SerializedName("sub_cat_8")
    @Expose
    private Object subCat8;
    @SerializedName("sub_cat_9")
    @Expose
    private Object subCat9;
    @SerializedName("sub_cat_10")
    @Expose
    private Object subCat10;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUniverBoard() {
        return univerBoard;
    }

    public void setUniverBoard(String univerBoard) {
        this.univerBoard = univerBoard;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getSubCat1() {
        return subCat1;
    }

    public void setSubCat1(Object subCat1) {
        this.subCat1 = subCat1;
    }

    public Object getSubCat2() {
        return subCat2;
    }

    public void setSubCat2(Object subCat2) {
        this.subCat2 = subCat2;
    }

    public Object getSubCat3() {
        return subCat3;
    }

    public void setSubCat3(Object subCat3) {
        this.subCat3 = subCat3;
    }

    public Object getSubCat4() {
        return subCat4;
    }

    public void setSubCat4(Object subCat4) {
        this.subCat4 = subCat4;
    }

    public Object getSubCat5() {
        return subCat5;
    }

    public void setSubCat5(Object subCat5) {
        this.subCat5 = subCat5;
    }

    public Object getSubCat6() {
        return subCat6;
    }

    public void setSubCat6(Object subCat6) {
        this.subCat6 = subCat6;
    }

    public Object getSubCat7() {
        return subCat7;
    }

    public void setSubCat7(Object subCat7) {
        this.subCat7 = subCat7;
    }

    public Object getSubCat8() {
        return subCat8;
    }

    public void setSubCat8(Object subCat8) {
        this.subCat8 = subCat8;
    }

    public Object getSubCat9() {
        return subCat9;
    }

    public void setSubCat9(Object subCat9) {
        this.subCat9 = subCat9;
    }

    public Object getSubCat10() {
        return subCat10;
    }

    public void setSubCat10(Object subCat10) {
        this.subCat10 = subCat10;
    }

}
