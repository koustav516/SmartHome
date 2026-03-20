package com.smarthome.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.sql.SQLException;

import com.smarthome.service.SubmitTicketService;

@MultipartConfig
public class SubmitTicketServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        String productDetails = request.getParameter("productDetails");
        Part imagePart = request.getPart("imageUpload");
        InputStream imageStream = null;

        if (imagePart != null && imagePart.getSize() > 0) {
            imageStream = imagePart.getInputStream();
        }

        try {
            SubmitTicketService ticketService = new SubmitTicketService();
            String ticketId = ticketService.submitTicket(userId, productDetails, imageStream);

            request.setAttribute("ticketId", ticketId);
            request.getRequestDispatcher("ticketConfirmation.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ticket submission failed.");
        } finally {
            if (imageStream != null) {
                imageStream.close();
            }
        }
    }

}
