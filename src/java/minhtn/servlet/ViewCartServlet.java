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
import javax.servlet.http.HttpSession;
import minhtn.cart.CartItem;
import minhtn.cart.CartObject;
import minhtn.tblPaymentMethod.TblPaymentMethodDAO;
import minhtn.tblPaymentMethod.TblPaymentMethodDTO;

/**
 *
 * @author minhv
 */
@WebServlet(name = "ViewCartServlet", urlPatterns = {"/ViewCartServlet"})
public class ViewCartServlet extends HttpServlet {

    private final String VIEW_CART_PAGE = "view-cart.jsp";

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

        String url = VIEW_CART_PAGE;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    //update each total price of each item in cart
                    for (CartItem cartItem : cart.getListItem()) {
                        cartItem.updateTotal();
                    }

                    //update cart in session
                    session.setAttribute("CART", cart);
                    //set cart to request
                    request.setAttribute("CUSTOMER_CART", cart);

                    //set total price to request
                    double totalPrice = cart.getTotalPriceOfCart();
                    request.setAttribute("TOTAL_PRICE", totalPrice);
                    
                    //load payment method
                    TblPaymentMethodDAO dao = new TblPaymentMethodDAO();
                    dao.getPaymentMethod();
                    List<TblPaymentMethodDTO> listPaymentMethod = dao.getListPaymentMethod();
                    request.setAttribute("PAYMENT_METHOD", listPaymentMethod);
                } //end if cart is existed
            } //end if session is existed
        } catch (SQLException e) {
            log("ViewCartServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("ViewCartServlet _ NamingException: " + e.getMessage());
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
