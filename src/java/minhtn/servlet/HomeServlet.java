/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import minhtn.food.FoodItem;
import minhtn.tblCategory.TblCategoryDAO;
import minhtn.tblCategory.TblCategoryDTO;
import minhtn.tblProducts.TblProductsDAO;
import minhtn.tblUsers.TblUserDTO;
import minhtn.utilities.PageCalculator;

/**
 *
 * @author minhv
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/HomeServlet"})
public class HomeServlet extends HttpServlet {

    private final String HOME_PAGE = "home.jsp";

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
        PrintWriter out = response.getWriter();

        String url = HOME_PAGE;
        String role = "GUEST";

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblUserDTO dto = (TblUserDTO) session.getAttribute("ACCOUNT");
                if (dto != null) {
                    role = dto.getRole();
                } //end if user is exist
            } //end if user is login

            loadAllFood(request, role);
            request.setAttribute("LOAD_DEFAULT_PAGE", 1);

            //load category list
            loadAllCategory(request);
            
            //load suggest food
            loadSuggestFood(request);
        } catch (SQLException e) {
            log("HomeServlet _ SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("HomeServlet _ NamingException: " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    private void loadAllFood(HttpServletRequest request, String role)
            throws SQLException, NamingException {
        TblProductsDAO dao = new TblProductsDAO();
        dao.getAllFood(role);
        List<FoodItem> listFood = dao.getFoodList();
        request.setAttribute("FOOD_LIST", listFood);

        int totalResult = dao.getTotalResult(role);
        int totalPage = PageCalculator.calculateTotalPage(totalResult, 20);
        request.setAttribute("TOTAL_PAGES", totalPage);
    }

    private void loadAllCategory(HttpServletRequest request)
            throws SQLException, NamingException {
        TblCategoryDAO dao = new TblCategoryDAO();
        dao.loadCategory();
        List<TblCategoryDTO> listCategory = dao.getCategoryList();
        request.setAttribute("CATEGORY_LIST", listCategory);
    }
    
    private void loadSuggestFood(HttpServletRequest request) 
        throws SQLException, NamingException {
        TblProductsDAO dao = new TblProductsDAO();
        dao.getSuggestFood();
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
