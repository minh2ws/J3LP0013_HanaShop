/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import minhtn.tblLog.TblLogDAO;
import minhtn.tblProducts.TblProductsDAO;
import minhtn.tblUsers.TblUserDTO;
import minhtn.utilities.GenerateCode;
import minhtn.utilities.ImageUtil;

/**
 *
 * @author minhv
 */
@WebServlet(name = "UpdateFoodServlet", urlPatterns = {"/UpdateFoodServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,   //2MB
        maxFileSize = 1024 * 1024 * 10) //10MB
public class UpdateFoodServlet extends HttpServlet {
    
    private final String PATH = "E:\\DriveSyncBackUp\\SyncAndBackUp\\FPTU\\CN5\\LAB231\\Image\\J3LP0013_HanaShop\\";
    private final String SEARCH_FOOD_SERVLET = "SearchFoodServlet";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = SEARCH_FOOD_SERVLET;
        
        try {
            String productId = request.getParameter("productId");
            String txtFoodName = request.getParameter("txtFoodName");
            String txtFoodDescription = request.getParameter("txtFoodDescription");
            String txtFoodPrice = request.getParameter("txtFoodPrice");
            String txtFoodQuantity = request.getParameter("txtFoodQuantity");
            String cmbCategoryFood = request.getParameter("cmbCategoryFood");
            Part imagePart = request.getPart("image");
            
            String imageLink = null;
            double price = 0;
            int quantity = 0;
            
            try {
                if (!txtFoodPrice.trim().isEmpty())
                    price = Double.parseDouble(txtFoodPrice);
                
            } catch (NumberFormatException e) {
                log("UpdateFoodServlet _ NumberFormatException: " + e.getMessage());
            }
            
            try {
                if (!txtFoodQuantity.trim().isEmpty())
                    quantity = Integer.parseInt(txtFoodQuantity);
                
            } catch (NumberFormatException e) {
                log("UpdateFoodServlet _ NumberFormatException: " + e.getMessage());
            }
            
            //check size of image
            if (imagePart.getSize() == 0)
                imageLink = request.getParameter("oldImageLink");
            else 
                imageLink = ImageUtil.writeToDisk(imagePart, PATH);
            
            TblProductsDAO dao = new TblProductsDAO();
            boolean isSuccess = dao.updateFood(productId, txtFoodName, quantity, imageLink, txtFoodDescription, price, cmbCategoryFood);
            if (isSuccess) {
                HttpSession session = request.getSession(false);
                TblUserDTO user = (TblUserDTO) session.getAttribute("ACCOUNT");
                String logId = GenerateCode.generateLogID();
                TblLogDAO logDAO = new TblLogDAO();
                logDAO.writeLog(logId, productId, user.getUserID());
            }
        } catch (IOException e) {
            log("UpdateFoodServlet _ IOException: " + e.getMessage());
        } catch (SQLException e) {
            log("UpdateFoodServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("UpdateFoodServlet _ NamingException: " + e.getMessage());
        }  finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
