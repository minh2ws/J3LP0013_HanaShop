/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import minhtn.tblOrder.TblOrderDAO;
import minhtn.tblOrderDetail.TblOrderDetailDAO;
import minhtn.tblProducts.TblProductsDAO;
import minhtn.tblProducts.TblProductsDTO;
import minhtn.tblUsers.TblUserDTO;
import minhtn.utilities.GenerateCode;

/**
 *
 * @author minhv
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {
    
    private final String HOME_SERVLET = "HomeServlet";
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
        
        try {
            String cmbPaymentMethod = request.getParameter("cmbPaymentMethod");
            HttpSession session = request.getSession(false);
            if (session != null) {
                //get user infor
                TblUserDTO user = (TblUserDTO) session.getAttribute("ACCOUNT");
                String userId = "";
                if (user != null)
                    userId = user.getUserID();
                
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    if (cart.getListItem() != null) {
                        //check error
                        CartError error = new CartError();
                        boolean isError = false;
                        
                        String notifyError = checkAvailableFood(cart, session);
                        if (!notifyError.trim().isEmpty()) {
                            error.setQuantityError(notifyError);
                            isError = true;
                        } else {
                            notifyError = checkFoodQuantity(cart);
                            if (!notifyError.trim().isEmpty()) {
                                error.setQuantityError(notifyError);
                                isError = true;
                            }
                        }
                        
                        if (isError) {
                            request.setAttribute("CART_ERRORS", error);
                        } else {
                            String orderId = GenerateCode.generateOrderID();
                            double totalPrice = cart.getTotalPriceOfCart();
                            
                            TblOrderDAO dao = new TblOrderDAO();
                            boolean isSuccess = dao.createOrder(orderId, cmbPaymentMethod, totalPrice, userId);
                            if (isSuccess) {
                                TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                                isSuccess = orderDetailDAO.createOrder(orderId, cart.getListItem());
                                if (isSuccess) {
                                    //remove cart
                                    session.setAttribute("CART", null);
                                    url = HOME_SERVLET;
                                }
                            }
                        }
                    }
                } //end if cart is existed
            } //end if session is existed
        } catch (SQLException e) {
            log("CheckOutServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("CheckOutServlet _ NamingException: " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }
    
    public String checkAvailableFood(CartObject cart, HttpSession session) throws SQLException, NamingException{
        String error = "";
        boolean isError = false;
        TblProductsDAO dao = new TblProductsDAO();
        
        List<TblProductsDTO> listUnavailableFood = null;
        
        List<CartItem> listItem = cart.getListItem();
        
        for (CartItem cartItem : listItem) {
            TblProductsDTO food = cartItem.getFood();
            String productId = food.getProductId();
            
            boolean isAvailable = dao.checkFoodItem(productId);
            if (!isAvailable) {
                if (listUnavailableFood == null) 
                    listUnavailableFood = new ArrayList<>();
                
                listUnavailableFood.add(food);
                
                isError = true;
            }
        }
        
        if (isError) {
            error = "Food isn't available or out of stock. Food: ";
            for (TblProductsDTO food : listUnavailableFood) {
                cart.removeItem(food.getProductId());
                error += food.getName() + ", ";
            }
            error += ". All this item will be removed.";
        }
        
        //remove cart        
        if (cart.getListItem() == null) {
            session.setAttribute("CART", null);
        }
        return error;
    }
    
    public String checkFoodQuantity(CartObject cart) throws SQLException, NamingException {
	String error = "";
        TblProductsDAO dao = new TblProductsDAO();
	for (CartItem cartItem : cart.getListItem()) {
	    TblProductsDTO food = cartItem.getFood();
            int quantity = cartItem.getQuantity();
            TblProductsDTO foodInStore = dao.getFood(food.getProductId());
            if (foodInStore.getQuantity() < quantity) {
                //update error
                error += ("Quantity " + foodInStore.getName() + " is only have " + foodInStore.getQuantity() + ".\n");
            }
            
            food.setQuantity(foodInStore.getQuantity());
	}
	return error;
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
