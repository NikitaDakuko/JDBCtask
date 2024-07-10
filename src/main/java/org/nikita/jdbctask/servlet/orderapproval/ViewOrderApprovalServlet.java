package org.nikita.jdbctask.servlet.orderapproval;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.OrderApprovalDAO;

import java.io.IOException;

@WebServlet("/orderApprovals")
public class ViewOrderApprovalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderApprovalDAO db = new OrderApprovalDAO();

        try {
            Long id = Long.parseLong(req.getParameter("id"));
            resp.getOutputStream().println(db.findById(id).toString());
        } catch (Exception ignored) {}

        resp.getOutputStream().println(db.getAll().toString());


    }
}


