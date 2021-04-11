/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import minhtn.food.FoodItem;
import minhtn.tblProducts.TblProductsDAO;

/**
 *
 * @author minhv
 */
@WebServlet(name = "ViewFoodServlet", urlPatterns = {"/ViewFoodServlet"})
public class ViewFoodServlet extends HttpServlet {
    
    private final String FOOD_DETAIL_PAGE = "product-detail.jsp";

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
        
        String url = FOOD_DETAIL_PAGE;
        
        try {
            String productId = request.getParameter("productId");
             
            TblProductsDAO dao = new TblProductsDAO();
            FoodItem dto = dao.getFoodItem(productId);
            
            request.setAttribute("FOOD", dto);
            
            //load suggest food
            loadSuggestFood(request, dto.getFood().getCateId());
        } catch (SQLException e) {
            log("ViewFoodServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("ViewFoodServlet _ NamingException: " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }
    
    private void loadSuggestFood(HttpServletRequest request, String cateId) 
            throws SQLException, NamingException {
        TblProductsDAO dao = new TblProductsDAO();
        dao.getSuggestFood(cateId);
        List<FoodItem> listSuggestFood = dao.getFoodSuggestList();
        request.setAttribute("FOOD_SUGGEST_LIST", listSuggestFood);
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
