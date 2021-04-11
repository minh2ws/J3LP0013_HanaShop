/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblUsers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import minhtn.utilities.DBHelpers;

/**
 *
 * @author minhv
 */
public class TblUserDAO implements Serializable {

    public TblUserDTO checkLogin(String username, String password)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //1. Make connection
            cn = DBHelpers.makeConnection();
            //2. Create SQL query string
            if (cn != null) {
                String sql = "SELECT userId, password, role, fullname "
                        + "FROM tblUsers "
                        + "WHERE userID = ? AND password = ?";
                ps = cn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String userId = rs.getString("userId");
                    String pass = rs.getString("password");
                    if (userId.equals(username) && pass.equals(password)) {
                        String role = rs.getString("role");
                        String fullname = rs.getString("fullname");
                        TblUserDTO user = new TblUserDTO(username, password, fullname, role);
                        return user;
                    }
                    return null;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return null;
    }
    
    public TblUserDTO checkLoginWithGoogle(String email)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //1. Make connection
            cn = DBHelpers.makeConnection();
            //2. Create SQL query string
            if (cn != null) {
                String sql = "SELECT userId, password, role, fullname "
                        + "FROM tblUsers "
                        + "WHERE userID = ?";
                ps = cn.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    String fullname = rs.getString("fullname");
                    TblUserDTO user = new TblUserDTO(email, password, fullname, role);
                    return user;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return null;
    }
}