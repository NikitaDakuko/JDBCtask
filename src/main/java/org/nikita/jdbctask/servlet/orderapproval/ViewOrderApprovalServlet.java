package org.nikita.jdbctask.servlet.orderapproval;

import jakarta.servlet.ServletContext;
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

/**
 * Servlet for viewing all products in database.
 * Can view individual product by passing ID as a parameter
 */
@WebServlet("/orderApprovals")
public class ViewOrderApprovalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderApprovalDAO db = new OrderApprovalDAO();
        List<OrderApprovalDTO> orderApprovals = new ArrayList<>();

        try {
            orderApprovals.add(db.findById(Long.parseLong(req.getParameter("id"))));
        }
        catch (Exception ignored) {
            orderApprovals = db.getAll();
        }

        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("tableName", "Order Approvals");
        servletContext.setAttribute("records", orderApprovals);
        getServletContext().getRequestDispatcher("/WEB-INF/orderApprovalTable.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse  response) throws IOException, ServletException {
        doGet(request, response);
    }
}


