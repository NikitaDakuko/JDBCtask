package org.nikita.jdbctask.servlet.order.detail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.OrderDetailDAO;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.entity.OrderDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orderDetails")
public class ViewOrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        List<OrderDetailDTO> orderDetails = new ArrayList<>();

        try {
            Long id = Long.parseLong(req.getParameter("id"));
            orderDetails.add(orderDetailDAO.findById(id));
        } catch (Exception e) {
            orderDetails = orderDetailDAO.getAll();
        }
        resp.getOutputStream().println(orderDetails.toString());
    }
}


