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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhtn.cart.CartError;
import minhtn.cart.CartItem;
import minhtn.cart.CartObject;
import minhtn.tblProducts.TblProductsDAO;
import minhtn.tblProducts.TblProductsDTO;

/**
 *
 * @author minhv
 */
@WebServlet(name = "UpdateItemServlet", urlPatterns = {"/UpdateItemServlet"})
public class UpdateItemServlet extends HttpServlet {
    
    private final String VIEW_CART_SERVLET = "ViewCartServlet";

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
        
        String url = VIEW_CART_SERVLET;
        CartError error = null;
        boolean isError = false;
        
        try {
            String productId = request.getParameter("productId");
            String txtQuantity = request.getParameter("txtQuantity");
            int quantity = 0;
            
            HttpSession session = request.getSession(false);
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    if (!txtQuantity.trim().isEmpty())
                        quantity = Integer.parseInt(txtQuantity);
                    
                    TblProductsDAO dao = new TblProductsDAO();
                    TblProductsDTO food = dao.getFood(productId);
                    if (food.getQuantity() == 0 || food.getStatus().equals("Inactive")) {
                        //update error
                        error = new CartError();
                        error.setQuantityError(food.getName() + " is out of stock. This will be removed from cart" );
                        
                        //update cart
                        cart.removeItem(productId);
                        
                        isError = true;
                    } else if (food.getQuantity() < quantity) {
                        //update error
                        error = new CartError();
                        error.setQuantityError("Quantity " + food.getName() + " is only have " + food.getQuantity());
                        
                        //update food in cart item
                        CartItem item = cart.findItem(productId);
                        item.setFood(food);
                        
                        isError = true;
                    }
                    
                    if (isError) {
                        request.setAttribute("CART_ERRORS", error);
                    } else {
                        CartItem item = cart.findItem(productId);
                        item.setFood(food);
                        
                        cart.updateQuantityOfItem(productId, quantity);
                    }
                    
                    //update cart in session
                    if (cart.getListItem() == null)
                        //remove cart
                        session.setAttribute("CART", null);
                    else
                        session.setAttribute("CART", cart);
                } //end if cart is existed
            } //end if session is existed
        } catch (NumberFormatException e) {
            log("UpdateItemServlet _ NumberFormatException: " + e.getMessage());
        } catch (SQLException e) {
            log("UpdateItemServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("UpdateItemServlet _ NamingException: " + e.getMessage());
        } finally {
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
