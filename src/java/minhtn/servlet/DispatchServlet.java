/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhtn.tblUsers.TblUserDTO;

/**
 *
 * @author minhv
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,   //2MB
        maxFileSize = 1024 * 1024 * 10) //10MB
public class DispatchServlet extends HttpServlet {

    private final String LOGIN_SERVLET = "LoginServlet";
    private final String LOGOUT_SERVLET = "LogoutServlet";
    private final String SEARCH_SERVLET = "SearchFoodServlet";
    private final String HOME_SERVLET = "HomeServlet";
    private final String LOAD_IMAGE_FOOD_SERVLET = "LoadImageFoodServlet";
    private final String MANAGE_FOOD_SERVLET = "ManageFoodServlet";
    private final String LOGIN_PAGE = "login.html";
    private final String UPDATE_STATUS_FOOD_SERVLET = "UpdateStatusProductServlet";
    private final String UPDATE_FOOD_SERVLET = "UpdateFoodServlet";
    private final String LOAD_CREATE_FOOD_PAGE = "LoadCreateFoodServlet";
    private final String CREATE_FOOD_SERVLET = "CreateFoodServlet";
    private final String VIEW_FOOD_SERVLET = "ViewFoodServlet";
    private final String ADD_TO_CART_SERVLET = "AddToCartServlet";
    private final String VIEW_CART_SERVLET = "ViewCartServlet";
    private final String UPDATE_ITEM_SERVLET = "UpdateItemServlet";
    private final String DELETE_ITEM_SERVLET = "DeleteItemServlet";
    private final String CHECK_OUT_SERVLET = "CheckOutServlet";
    private final String LOAD_ORDER_HISTORY_SERVLET = "LoadOrderHistoryServlet";

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

        String url = LOGIN_SERVLET;
        String button = request.getParameter("btAction");

        try {
            if (button == null) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = MANAGE_FOOD_SERVLET;
                } else {
                    url = HOME_SERVLET;
                }
            } else if ("Login".equals(button)) {
                url = LOGIN_SERVLET;
            } else if ("Logout".equals(button)) {
                url = LOGOUT_SERVLET;
            } else if ("Search".equals(button)) {
                url = SEARCH_SERVLET;
            } else if ("Home".equals(button)) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = MANAGE_FOOD_SERVLET;
                } else {
                    url = HOME_SERVLET;
                }
            } else if ("Image".equals(button)) {
                url = LOAD_IMAGE_FOOD_SERVLET;
            } else if ("Update Status".equals(button)) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = UPDATE_STATUS_FOOD_SERVLET;
                } else {
                    url = LOGIN_PAGE;
                }
            } else if ("Update Food".equals(button)) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = UPDATE_FOOD_SERVLET;
                } else {
                    url = LOGIN_PAGE;
                }
            } else if ("CreateFood".equals(button)) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = LOAD_CREATE_FOOD_PAGE;
                } else {
                    url = LOGIN_PAGE;
                }
            } else if ("Create Food".equals(button)) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = CREATE_FOOD_SERVLET;
                } else {
                    url = LOGIN_PAGE;
                }
            } else if ("View Detail".equals(button)) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = MANAGE_FOOD_SERVLET;
                } else {
                    url = VIEW_FOOD_SERVLET;
                }
            } else if ("AddToCart".equals(button)) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = MANAGE_FOOD_SERVLET;
                } else {
                    boolean isLogin = checkIsLogin(request);
                    if (isLogin) {
                        url = ADD_TO_CART_SERVLET;
                    } else {
                        url = LOGIN_PAGE;
                    }
                }
            } else if ("ViewCart".equals(button)) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = MANAGE_FOOD_SERVLET;
                } else {
                    boolean isLogin = checkIsLogin(request);
                    if (isLogin) {
                        url = VIEW_CART_SERVLET;
                    } else {
                        url = LOGIN_PAGE;
                    }
                }
            } else if ("Update".equals(button)) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = MANAGE_FOOD_SERVLET;
                } else {
                    boolean isLogin = checkIsLogin(request);
                    if (isLogin) {
                        url = UPDATE_ITEM_SERVLET;
                    } else {
                        url = LOGIN_PAGE;
                    }
                }
            } else if ("Delete".equals(button)) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = MANAGE_FOOD_SERVLET;
                } else {
                    boolean isLogin = checkIsLogin(request);
                    if (isLogin) {
                        url = DELETE_ITEM_SERVLET;
                    } else {
                        url = LOGIN_PAGE;
                    }
                } 
            } else if ("Checkout".equals(button)) {
                boolean isAdmin = checkRole(request);
                if (isAdmin) {
                    url = MANAGE_FOOD_SERVLET;
                } else {
                    boolean isLogin = checkIsLogin(request);
                    if (isLogin) {
                        url = CHECK_OUT_SERVLET;
                    } else {
                        url = LOGIN_PAGE;
                    }
                } 
            } else if ("OrderHistory".equals(button)) {
                boolean isLogin = checkIsLogin(request);
                if (isLogin) {
                    url = LOAD_ORDER_HISTORY_SERVLET;
                } else {
                    url = LOGIN_PAGE;
                }
            } else if ("Search Order".equals(button)) {
                boolean isLogin = checkIsLogin(request);
                if (isLogin) {
                    url = LOAD_ORDER_HISTORY_SERVLET;
                } else {
                    url = LOGIN_PAGE;
                }
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    private boolean checkRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            TblUserDTO dto = (TblUserDTO) session.getAttribute("ACCOUNT");
            if (dto != null) {
                String role = dto.getRole();
                if (role.equals("ADMIN")) {
                    return true;
                }
            }
        } //end if session is existed
        return false;
    }

    private boolean checkIsLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            TblUserDTO dto = (TblUserDTO) session.getAttribute("ACCOUNT");
            if (dto != null) {
                return true;
            }
        } //end if session is existed
        return false;
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
