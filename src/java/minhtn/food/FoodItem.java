/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.food;

import java.io.Serializable;
import minhtn.tblProducts.TblProductsDTO;

/**
 *
 * @author minhv
 */
public class FoodItem implements Serializable{
    private TblProductsDTO food;
    private String category;

    public FoodItem() {
    }

    public FoodItem(TblProductsDTO food, String category) {
        this.food = food;
        this.category = category;
    }

    /**
     * @return the food
     */
    public TblProductsDTO getFood() {
        return food;
    }

    /**
     * @param food the food to set
     */
    public void setFood(TblProductsDTO food) {
        this.food = food;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
}
