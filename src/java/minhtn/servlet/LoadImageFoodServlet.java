/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author minhv
 */
@WebServlet(name = "LoadImageFoodServlet", urlPatterns = {"/LoadImageFoodServlet"})
public class LoadImageFoodServlet extends HttpServlet {
    
    private final String PATH = "E:\\DriveSyncBackUp\\SyncAndBackUp\\FPTU\\CN5\\LAB231\\Image\\J3LP0013_HanaShop\\";

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
        String filePath = request.getParameter("file");

//        BufferedInputStream bfInput = null;
//        BufferedOutputStream bfOutput = null;
        File imageFile;

        try {
            imageFile = new File(PATH + filePath);
            
            //image/png text/html
            response.setContentType(getServletContext().getMimeType(imageFile.getName()));
            //set header of image
            response.setHeader("Content-Length", String.valueOf(imageFile.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + imageFile.getName() + "\"");

            
//            bfInput = new BufferedInputStream(new FileInputStream(imageFile));
//            bfOutput = new BufferedOutputStream(response.getOutputStream());
//
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = bfInput.read(buffer)) > 0) {
//                bfOutput.write(buffer, 0, length);
//            }

            Files.copy(imageFile.toPath(), response.getOutputStream());
        } catch (NullPointerException e) {
            log("LoadImageFoodServlet _ NullPointerException: " + e.getMessage());
        } catch (IOException e) {
            log("LoadImageFoodServlet _ IOException: " + e.getMessage());
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
