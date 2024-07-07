package org.nikita.jdbctask.servlet.orderapproval;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.OrderApprovalDAO;

import java.io.IOException;

@WebServlet("/deleteOrderApproval")
public class DeleteOrderApprovalServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        OrderApprovalDAO orderApprovalDAO = new OrderApprovalDAO();
        orderApprovalDAO.delete(id);
        req.getRequestDispatcher("/orderApprovals").forward(req, resp);
    }
}
