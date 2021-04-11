/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblProducts;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import minhtn.food.FoodItem;
import minhtn.utilities.DBHelpers;

/**
 *
 * @author minhv
 */
public class TblProductsDAO implements Serializable {

    /**
     * List of product
     */
    private List<FoodItem> foodList;

    /**
     * Return list of product
     * @return list product - List
     */
    public List<FoodItem> getFoodList() {
        return foodList;
    }
    
    private List<FoodItem> foodSuggestList;

    public List<FoodItem> getFoodSuggestList() {
        return foodSuggestList;
    }

    /**
     * Status active
     */
    private final String ACTIVE_STATUS = "Active";

    /**
     * Get total product using for paging when loading home page
     * @param role
     * @return total product - integer
     * @throws SQLException
     * @throws NamingException 
     */
    public int getTotalResult(String role) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //make connection
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT COUNT(productId) as total "
                        + "FROM tblProducts "
                        + "WHERE quantity > ? ";
                //checking to create status query zone
                if (!role.equals("ADMIN"))
                    sql += " AND status = '" + ACTIVE_STATUS + "' ";

                //prepare query string
                ps = cn.prepareStatement(sql);
                if (role.equals("ADMIN"))
                    ps.setInt(1, -1);
                else
                    ps.setInt(1, 0);

                //execute query
                rs = ps.executeQuery();
                if (rs.next())
                    return rs.getInt("total");
            } //end if connection is connected
        } finally {
            if (rs != null)
                rs.close();
            
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();   
        }
        return 0;
    }

    /**
     * Get all product using for home page, add all product to list
     * @param role
     * @throws SQLException
     * @throws NamingException 
     */
    public void getAllFood(String role) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //make connection
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT productId, name, status, quantity, "
                        + "image, description, price, createOfDate, "
                        + "p.cateId, categoryName "
                        + "FROM tblProducts p, tblCategory c "
                        + "WHERE c.cateId = p.cateId "
                        + "AND quantity > ? ";

                //checking to create status query zone
                //check is admin or not
                if (!role.equals("ADMIN"))
                    sql += " AND status = '" + ACTIVE_STATUS + "' ";
                

                sql += "GROUP BY productId, name, status, quantity, image, "
                        + "description, price, createOfDate, p.cateId, "
                        + "categoryName "
                        + "ORDER BY createOfDate DESC "
                        + "OFFSET 0 ROWS "
                        + "FETCH NEXT 20 ROWS ONLY";

                ps = cn.prepareStatement(sql);
                if (role.equals("ADMIN"))
                    ps.setInt(1, -1);
                else
                    ps.setInt(1, 0);

                rs = ps.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("productId");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    Date createOfDate = rs.getDate("createOfDate");
                    String cateId = rs.getString("cateId");
                    String categoryName = rs.getString("categoryName");

                    TblProductsDTO dto = new TblProductsDTO(productId, name,
                            status, quantity, image, description, price,
                            createOfDate, cateId);
                    FoodItem item = new FoodItem(dto, categoryName);

                    if (foodList == null) 
                        foodList = new ArrayList<>();
                    
                    foodList.add(item);
                }
            }
        } finally {
            if (rs != null) 
                rs.close();
            
            if (ps != null) 
                ps.close();
            
            if (cn != null) 
                cn.close();
        }
    }

    /**
     * Search Food base on name, price range, category
     * @param searchValue
     * @param categoryId
     * @param priceRange
     * @param role
     * @param page
     * @throws SQLException
     * @throws NamingException 
     */
    public void searchFood(String searchValue, String categoryId,
            String priceRange, String role, int page)
            throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT productId, name, status, quantity, "
                        + "image, description, price, createOfDate, "
                        + "p.cateId, categoryName "
                        + "FROM tblProducts p, tblCategory c "
                        + "WHERE c.cateId = p.cateId "
                        + "AND quantity > ? ";
                //check searchValue is not empty for create String sql
                if (!searchValue.trim().isEmpty()) 
                    //string sql
                    sql += "AND name like N'%" + searchValue + "%' ";
                

                //check priceRange is not empty for create String sql
                if (!priceRange.trim().isEmpty()) {
                    if (priceRange.contains("-")) {
                        //split priceRange to get number
                        String[] arr = priceRange.split("-");
                        double minPrice = Double.parseDouble(arr[0]);
                        double maxPrice = Double.parseDouble(arr[1]);
                        //string sql
                        sql += "AND (price BETWEEN " + minPrice + " AND " + maxPrice + ") ";
                    } else {
                        //string sql
                        priceRange = priceRange.replace(">", "");
                        sql += "AND price > " + priceRange + " ";
                    }
                }

                //check category is not empty for create String sql
                if (!categoryId.trim().isEmpty()) 
                    sql += "AND c.cateId = '" + categoryId + "' ";                

                //checking to create status query zone
                //check is admin or not
                if (!role.equals("ADMIN"))
                    sql += " AND status = '" + ACTIVE_STATUS + "' ";

                sql += "GROUP BY productId, name, quantity, image, description, "
                        + "price, createOfDate, status, p.cateId, "
                        + "categoryName "
                        + "ORDER BY createOfDate DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT 20 ROWS ONLY";

                ps = cn.prepareStatement(sql);
                 if (role.equals("ADMIN"))
                    ps.setInt(1, -1);
                else
                    ps.setInt(1, 0);

                //set rows to search food
                ps.setInt(2, (page - 1) * 20);
                //page 1 => (1 - 1) * 20
                //start from row 0
                //page 2 => (2 - 1) * 20
                //start from row 20
                //and continue for next page

                //execute query
                rs = ps.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("productId");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    Date createOfDate = rs.getDate("createOfDate");
                    String cateId = rs.getString("cateId");
                    String categoryName = rs.getString("categoryName");

                    TblProductsDTO dto = new TblProductsDTO(productId, name, status, quantity, image, description, price, createOfDate, cateId);
                    FoodItem item = new FoodItem(dto, categoryName);
                    if (foodList == null) 
                        foodList = new ArrayList<>();
                    //end if food list is not exist
                    foodList.add(item);
                } //end of result set
            }//end if connection is connected
        } finally {
            if (rs != null) 
                rs.close();
            
            if (ps != null) 
                ps.close();
            
            if (cn != null) 
                cn.close();
        }
    }
    
    /**
     * Count total searched for paging
     * @param searchValue
     * @param categoryId
     * @param priceRange
     * @param role
     * @return total product searched
     * @throws SQLException
     * @throws NamingException 
     */
    public int getTotalFoodSearch(String searchValue, String categoryId,
            String priceRange, String role)
            throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT count(productId) AS total "
                        + "FROM tblProducts p, tblCategory c "
                        + "WHERE c.cateId = p.cateId "
                        + "AND quantity > ? ";
                //check searchValue is not empty for create String sql
                if (!searchValue.trim().isEmpty()) 
                    //string sql
                    sql += "AND name like N'%" + searchValue + "%' ";
                

                //check priceRange is not empty for create String sql
                if (!priceRange.trim().isEmpty()) {
                    if (priceRange.contains("-")) {
                        //split priceRange to get number
                        String[] arr = priceRange.split("-");
                        double minPrice = Double.parseDouble(arr[0]);
                        double maxPrice = Double.parseDouble(arr[1]);
                        //string sql
                        sql += "AND (price BETWEEN " + minPrice + " AND " + maxPrice + ") ";
                    } else {
                        //string sql
                        priceRange = priceRange.replace(">", "");
                        sql += "AND price > " + priceRange + " ";
                    }
                }

                //check category is not empty for create String sql
                if (!categoryId.trim().isEmpty()) 
                    sql += "AND c.cateId = '" + categoryId + "' ";                

                //checking to create status query zone
                //check is admin or not
                if (!role.equals("ADMIN"))
                    sql += " AND status = '" + ACTIVE_STATUS + "' ";

                ps = cn.prepareStatement(sql);
                 if (role.equals("ADMIN"))
                    ps.setInt(1, -1);
                else
                    ps.setInt(1, 0);

                //execute query
                rs = ps.executeQuery();
                if (rs.next())
                    return rs.getInt("total");
            }//end if connection is connected
        } finally {
            if (rs != null) 
                rs.close();
            
            if (ps != null) 
                ps.close();
            
            if (cn != null) 
                cn.close();
        }
        return 0;
    }
    
    /**
     * Update status of product
     * @param productId
     * @param status
     * @throws SQLException
     * @throws NamingException 
     */
    public void updateStatusQuestion(String productId, String status) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = DBHelpers.makeConnection();
            
            if (cn != null) {
                String sql = "UPDATE tblProducts "
                        + "SET status = ? "
                        + "WHERE productId = ? ";
                ps = cn.prepareStatement(sql);
                ps.setString(1, status);
                ps.setString(2, productId);
                
                ps.executeUpdate();
            } //end if connection is connected
        } finally {            
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
    }
    
    public boolean updateFood(String productId, String name,  int quantity, 
            String image, String description, double price, String cateId)
            throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = DBHelpers.makeConnection();
            
            if (cn != null) {
                String sql = "UPDATE tblProducts "
                        + "SET name = ?, quantity = ?, image = ?, description = ?, "
                        + "price = ?, cateId = ? "
                        + "WHERE productId = ? ";
                ps = cn.prepareStatement(sql);
                ps.setString(1, name);
                ps.setInt(2, quantity);
                ps.setString(3, image);
                ps.setString(4, description);
                ps.setDouble(5, price);
                ps.setString(6, cateId);
                ps.setString(7, productId);
                
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            } //end if connection is existed
        } finally {
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
        return false;
    }
    
    public boolean insertFood(String productId, String name, String status, int quantity, 
            String image, String description, double price, String cateId)
            throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = DBHelpers.makeConnection();
            
            if (cn != null) {
                String sql = "INSERT INTO tblProducts (productId, name, status, quantity, image, description, price, cateId) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                ps = cn.prepareStatement(sql);
                ps.setString(1, productId);
                ps.setString(2, name);
                ps.setString(3, status);
                ps.setInt(4, quantity);
                ps.setString(5, image);
                ps.setString(6, description);
                ps.setDouble(7, price);
                ps.setString(8, cateId);
                
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            } //end if connection is existed
        } finally {
            if (ps != null)
                ps.close();
            
            if (cn != null)
                cn.close();
        }
        return false;
    }
    
    public TblProductsDTO getFood(String productId) throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //make connection
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT name, status, quantity, image, description, price, createOfDate, cateId "
                        + "FROM tblProducts "
                        + "WHERE productId = ? ";
                //prepare query string
                ps = cn.prepareStatement(sql);
                ps.setString(1, productId);
                
                //execute query
                rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    Date createOfDate = rs.getDate("createOfDate");
                    String cateId = rs.getString("cateId");
                    return new TblProductsDTO(productId, name, status, quantity, image, description, price, createOfDate, cateId);
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
        return null;
    }
    
    public FoodItem getFoodItem(String productId) throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //make connection
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT name, status, quantity, "
                        + "image, description, price, createOfDate, "
                        + "p.cateId, categoryName "
                        + "FROM tblProducts p, tblCategory c "
                        + "WHERE c.cateId = p.cateId AND productId = ? ";
                //prepare query string
                ps = cn.prepareStatement(sql);
                ps.setString(1, productId);
                
                //execute query
                rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    Date createOfDate = rs.getDate("createOfDate");
                    String cateId = rs.getString("cateId");
                    String categoryName = rs.getString("categoryName");
                    TblProductsDTO food = new TblProductsDTO(productId, name, status, quantity, image, description, price, createOfDate, cateId);
                    return new FoodItem(food, categoryName);
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
        return null;
    }
    
    public boolean checkFoodItem(String productId) throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //make connection
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT name "
                        + "FROM tblProducts "
                        + "WHERE productId = ? AND status = ? AND quantity > ?";
                //prepare query string
                ps = cn.prepareStatement(sql);
                ps.setString(1, productId);
                ps.setString(2, "Active");
                ps.setInt(3, 0);
                
                //execute query
                rs = ps.executeQuery();
                if (rs.next()) {
                    return true;
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
        return false;
    }
    
    public void getSuggestFood() throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //make connection
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT productId, name, status, quantity, image, description, price, createOfDate, p.cateId, categoryName \n" +
                                "FROM tblProducts p, tblCategory c \n" +
                                "WHERE c.cateId = p.cateId \n" +
                                "AND quantity > 0  AND status = 'Active' AND productId IN (SELECT TOP(2) od.productId\n" +
                                                                                            "FROM tblOrderDetail od JOIN tblProducts P ON od.productId = p.productId\n" +
                                                                                            "WHERE p.quantity > 0  AND status = 'Active'\n" +
                                                                                            "GROUP BY od.productId\n" +
                                                                                            "ORDER BY SUM(od.quantity) DESC)";
                ps = cn.prepareStatement(sql);

                rs = ps.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("productId");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    Date createOfDate = rs.getDate("createOfDate");
                    String cateId = rs.getString("cateId");
                    String categoryName = rs.getString("categoryName");

                    TblProductsDTO dto = new TblProductsDTO(productId, name,
                            status, quantity, image, description, price,
                            createOfDate, cateId);
                    FoodItem item = new FoodItem(dto, categoryName);

                    if (foodSuggestList == null) 
                        foodSuggestList = new ArrayList<>();
                    
                    foodSuggestList.add(item);
                }
            }
        } finally {
            if (rs != null) 
                rs.close();
            
            if (ps != null) 
                ps.close();
            
            if (cn != null) 
                cn.close();
        }
    }
    
    public void getSuggestFood(String cateId) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //make connection
            cn = DBHelpers.makeConnection();
            //check connection
            if (cn != null) {
                //create query string
                String sql = "SELECT TOP(3) productId, name, status, quantity, image, description, price, createOfDate, p.cateId, categoryName \n" +
                    "FROM tblProducts p, tblCategory c \n" +
                    "WHERE c.cateId = p.cateId \n" +
                    "AND quantity > 0  AND status = 'Active' AND p.cateId != ? \n" +
                    "ORDER BY NEWID()";
                ps = cn.prepareStatement(sql);
                ps.setString(1, cateId);

                rs = ps.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("productId");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    Date createOfDate = rs.getDate("createOfDate");
                    String cateIdOrder = rs.getString("cateId");
                    String categoryName = rs.getString("categoryName");

                    TblProductsDTO dto = new TblProductsDTO(productId, name,
                            status, quantity, image, description, price,
                            createOfDate, cateIdOrder);
                    FoodItem item = new FoodItem(dto, categoryName);

                    if (foodSuggestList == null) 
                        foodSuggestList = new ArrayList<>();
                    
                    foodSuggestList.add(item);
                }
            }
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
