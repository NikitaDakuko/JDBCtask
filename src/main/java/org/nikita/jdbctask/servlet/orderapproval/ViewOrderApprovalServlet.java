package org.nikita.jdbctask.servlet.orderapproval;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.OrderApprovalDAO;
import org.nikita.jdbctask.dto.OrderApprovalDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orderApprovals")
public class ViewOrderApprovalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderApprovalDAO db = new OrderApprovalDAO();
        List<OrderApprovalDTO> orderApprovals = new ArrayList<>();

        try {
            Long id = Long.parseLong(req.getParameter("id"));
            orderApprovals.add(db.findById(id));
        } catch (Exception e) {
            orderApprovals = db.getAll();
        }
        resp.getOutputStream().println(orderApprovals.toString());
    }
}


