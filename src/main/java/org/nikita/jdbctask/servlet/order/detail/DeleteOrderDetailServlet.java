package org.nikita.jdbctask.servlet.order.detail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.OrderDetailDAO;

import java.io.IOException;

@WebServlet("/deleteOrderDetails")
public class DeleteOrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        orderDetailDAO.delete(id);
        req.getRequestDispatcher("/orderDetails").forward(req, resp);
    }
}
