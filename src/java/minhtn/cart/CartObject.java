/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.cart;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import minhtn.tblProducts.TblProductsDAO;
import minhtn.tblProducts.TblProductsDTO;

/**
 *
 * @author minhv
 */
public class CartObject implements Serializable {
    private List<CartItem> listItem;
    private int totalItem;

    public List<CartItem> getListItem() {
        return listItem;
    }

    public int getTotalItem() {
        totalItem = listItem.size();
        return totalItem;
    }
    
    public void addItemToCart(String productId) throws SQLException, NamingException {
        //1. Check list item
        if (listItem == null)
            listItem = new ArrayList<>();
        
        int quantity = 1;
        
        CartItem item = findItem(productId);
        
        //2. Check if item is existed or not
        if (item == null) {
            TblProductsDAO dao = new TblProductsDAO();
            TblProductsDTO food = dao.getFood(productId);
            item = new CartItem(food, quantity);
            item.updateTotal();
            listItem.add(item);
        } else {
            quantity = item.getQuantity() + 1;
            item.setQuantity(quantity);
            item.updateTotal();
        }
    }
    
    public CartItem findItem(String productId) {
        for (CartItem cartItem : listItem) {
            if(cartItem.getFood().getProductId().equals(productId))
                return cartItem;
        }
        return null;
    }
    
    public double getTotalPriceOfCart() {
        double totalPrice = 0;
        for (CartItem cartItem : listItem) {
            totalPrice += cartItem.getTotal();
        }
        return totalPrice;
    }
    
    public void updateQuantityOfItem(String productId, int quantity) {
        CartItem item = findItem(productId);
        item.setQuantity(quantity);
        item.updateTotal();
    }
    
    public void removeItem(String productId) {
        if (listItem == null) 
            return;
        
        CartItem item = findItem(productId);
        if (item != null) {
            listItem.remove(item);
            
            if (listItem.isEmpty()) 
                listItem = null;
            //remove cart if map is empty
        }
    }
}
