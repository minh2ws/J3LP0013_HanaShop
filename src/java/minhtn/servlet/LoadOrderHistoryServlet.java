/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhtn.tblOrder.TblOrderDAO;
import minhtn.tblOrder.TblOrderDTO;
import minhtn.tblOrderDetail.FoodItemDTO;
import minhtn.tblOrderDetail.TblOrderDetailDAO;
import minhtn.tblUsers.TblUserDTO;

/**
 *
 * @author minhv
 */
@WebServlet(name = "LoadOrderHistoryServlet", urlPatterns = {"/LoadOrderHistoryServlet"})
public class LoadOrderHistoryServlet extends HttpServlet {
    
    private final String ORDER_HISTORY_PAGE = "order-history.jsp";

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
        
        String url = ORDER_HISTORY_PAGE;
        
        try {
            String txtFromDate = request.getParameter("txtFromDate");
            String txtToDate = request.getParameter("txtToDate");
            
            if (txtFromDate == null)
                txtFromDate = "";
            
            if (txtToDate == null)
                txtToDate = "";
                
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblUserDTO user = (TblUserDTO) session.getAttribute("ACCOUNT");
               
                TblOrderDAO dao = new TblOrderDAO();
                dao.getOrder(txtFromDate, txtToDate, user);
                List<TblOrderDTO> listOrder = dao.getListOrder();
                
                Map<String, List<FoodItemDTO>> mapOrderDetail = null;
                
                if (listOrder != null) {
                    mapOrderDetail = new HashMap<>();
                    TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                    
                    for (TblOrderDTO tblOrderDTO : listOrder) {
                        List<FoodItemDTO> listFoodItem = orderDetailDAO.getOrderDetail(tblOrderDTO.getOrderId());
                        mapOrderDetail.put(tblOrderDTO.getOrderId(), listFoodItem);
                    }
                }
                
                request.setAttribute("ORDER_LIST", listOrder);
                
                request.setAttribute("ORDER_DETAIL_MAP", mapOrderDetail);
            } //end if session is existed
        } catch (SQLException e) {
            log("ViewFoodServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("ViewFoodServlet _ NamingException: " + e.getMessage());
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
