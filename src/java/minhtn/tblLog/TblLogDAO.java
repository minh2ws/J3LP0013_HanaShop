/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblLog;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import minhtn.utilities.DBHelpers;

/**
 *
 * @author minhv
 */
public class TblLogDAO implements Serializable{
    /**
     * Write log when admin update
     * @param logId
     * @param productId
     * @param userId
     * @return true or false
     * @throws SQLException
     * @throws NamingException 
     */
    public boolean writeLog(String logId, String productId, String userId)
            throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO tblLog(logId, productId, userId) "
                        + "VALUES(?, ?, ?)";

                ps = cn.prepareStatement(sql);
                ps.setString(1, logId);
                ps.setString(2, productId);
                ps.setString(3, userId);
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }//end if connection
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return false;
    }
}
