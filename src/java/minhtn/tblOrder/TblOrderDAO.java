/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblOrder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import minhtn.tblUsers.TblUserDTO;
import minhtn.utilities.DBHelpers;

/**
 *
 * @author minhv
 */
public class TblOrderDAO implements Serializable {
    private List<TblOrderDTO> listOrder;

    public List<TblOrderDTO> getListOrder() {
        return listOrder;
    }
    
    public boolean createOrder(String orderId, String paymentMethod, 
            double totalPrice, String userId) 
        throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = DBHelpers.makeConnection();
            
            if (cn != null) {
                String sql = "INSERT INTO tblOrder (orderId, total, userId, methodId) "
                        + "VALUES (?, ?, ?, ?)";
                ps = cn.prepareStatement(sql);
                ps.setString(1, orderId);
                ps.setDouble(2, totalPrice);
                ps.setString(3, userId);
                ps.setString(4, paymentMethod);
                
                int row = ps.executeUpdate();
                if (row > 0)
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
    
    public void getOrder(String fromDate, String toDate, TblUserDTO user) 
            throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //make connection
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT orderId, orderDate, total, userId, methodName \n" +
                    "FROM tblOrder o JOIN tblPaymentMethod p ON o.methodId = p.methodId \n";
                
                
                if (!fromDate.trim().isEmpty() && !toDate.trim().isEmpty()) {
                    sql += "WHERE (orderDate BETWEEN '" + fromDate + "' AND '" + toDate + " 23:59:59') ";
                        if (!user.getRole().equals("ADMIN"))
                            sql += " AND userId = '" + user.getUserID() + "' ";
                } else {
                    if (!user.getRole().equals("ADMIN"))
                    sql += " WHERE userId = '" + user.getUserID() + "' ";
                }
                
                //prepare query string
                ps = cn.prepareStatement(sql);
                
                //execute query
                rs = ps.executeQuery();
                while (rs.next()) {
                    String orderId = rs.getString("orderId");
                    Date orderDate = rs.getDate("orderDate");
                    String userId = rs.getString("userId");
                    double total = rs.getDouble("total");
                    String methodName = rs.getString("methodName");
                    
                    if (listOrder == null)
                        listOrder = new ArrayList<>();
                    
                    TblOrderDTO dto = new TblOrderDTO(orderId, orderDate, total, userId, methodName);
                    
                    listOrder.add(dto);
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
    }
}
