package com.smarthome.servlets;

import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smarthome.models.Ticket;
import com.smarthome.service.SubmitTicketService;

public class TicketStatusServlet extends HttpServlet {

    SubmitTicketService ticketService;

    public void init() {
        ticketService = new SubmitTicketService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String ticketNumber = request.getParameter("ticketNumber");

        try {
            Ticket ticket = ticketService.getTicketByNumber(ticketNumber);
            System.out.println("Ticket id: " + ticket.getTicketNumber());
            if (ticket != null && ticket.getImage() != null) {
                byte[] imageBytes = ticket.getImage();
                InputStream imageStream = new ByteArrayInputStream(imageBytes);
                String description = ticket.getProductDetails();
                String decision = ticket.getDecision();
                if (decision == null || decision.isEmpty()) {
                    decision = ticketService.analyzeImageWithOpenAI(imageStream, ticketNumber, description);
                    ticketService.saveDecisionToDatabase(ticketNumber, decision);
                }

                request.setAttribute("decision", decision);
            } else {
                request.setAttribute("error", "Ticket not found or no image provided.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("ticketStatus.jsp").forward(request, response);
    }

}
