/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblCategory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import minhtn.utilities.DBHelpers;

/**
 *
 * @author minhv
 */
public class TblCategoryDAO implements Serializable{
    private List<TblCategoryDTO> categoryList;

    public List<TblCategoryDTO> getCategoryList() {
        return categoryList;
    }
    
    public void loadCategory() throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            //make connection
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT cateId, categoryName " +
                            "FROM tblCategory";
                //prepare query string
                ps = cn.prepareStatement(sql);
                //execute query
                rs = ps.executeQuery();
                
                //add result to list
                while (rs.next()) { 
                    //check list
                    if (categoryList == null) {
                        categoryList = new ArrayList<>();
                    }
                    
                    String cateId = rs.getString("cateId");
                    String categoryName = rs.getString("categoryName");
                    
                    TblCategoryDTO dto = new TblCategoryDTO(cateId, categoryName);
                    categoryList.add(dto);
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
    }
}
