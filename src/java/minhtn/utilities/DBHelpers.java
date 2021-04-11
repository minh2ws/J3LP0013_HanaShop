/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.utilities;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author minhv
 */
public class DBHelpers implements Serializable{
    public static Connection makeConnection() throws NamingException, SQLException {
        //1. Get current context
        Context context = new InitialContext();
        //2. Get server context
        Context tomcatContext = (Context) context.lookup("java:comp/env");
        //3. Get Data Source
        DataSource ds = (DataSource) tomcatContext.lookup("HanaShopDB");
        //4. Make connection
        Connection con = ds.getConnection();
        
        return con;
    }
}
