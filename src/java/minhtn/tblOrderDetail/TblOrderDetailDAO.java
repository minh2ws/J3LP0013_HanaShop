/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblOrderDetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import minhtn.cart.CartItem;
import minhtn.utilities.DBHelpers;

/**
 *
 * @author minhv
 */
public class TblOrderDetailDAO implements Serializable {
    public boolean createOrder(String orderId, List<CartItem> listItem) 
        throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = DBHelpers.makeConnection();
            
            if (cn != null) {
                String sql = "INSERT INTO tblOrderDetail (orderId, productId, quantity, total) "
                        + "VALUES (?, ?, ?, ?)";
                ps = cn.prepareStatement(sql);
                
                //set auto commit to false;
                cn.setAutoCommit(false);
                
                //variable for check add success or not
                int totalRow = 0;
                for (CartItem cartItem : listItem) {
                    ps.setString(1, orderId);
                    ps.setString(2, cartItem.getFood().getProductId());
                    ps.setInt(3, cartItem.getQuantity());
                    ps.setDouble(4, cartItem.getTotal());
                    
                    ps.addBatch();
                    totalRow++;
                }

                int result[] = ps.executeBatch();
                cn.commit();
                
                if (result.length == totalRow)
                    return true;
            } //end if connection is existed
        } finally {
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
        return false;
    }
    
    public List<FoodItemDTO> getOrderDetail(String orderId)
        throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<FoodItemDTO> listFoodItem = null;
        
        try {
            //make connection
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT p.name, p.price, od.quantity, od.total \n" +
                    "FROM tblOrderDetail od JOIN tblProducts p ON od.productId = p.productId \n" +
                    "WHERE od.orderId = ?";
                
                //prepare query string
                ps = cn.prepareStatement(sql);
                ps.setString(1, orderId);
                
                //execute query
                rs = ps.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    double total = rs.getDouble("total");
                    
                    if (listFoodItem == null)
                        listFoodItem = new ArrayList<>();
                    
                    FoodItemDTO dto = new FoodItemDTO(name, price, quantity, total);
                    
                    listFoodItem.add(dto);
                }
            } //end if connection is connected
        } finally {
            if (rs != null)
                rs.close();
            
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();   
        }
        
        return listFoodItem;
    }
}
