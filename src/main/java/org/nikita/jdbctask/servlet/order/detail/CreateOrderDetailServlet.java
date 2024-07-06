package org.nikita.jdbctask.servlet.order.detail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.OrderDetailDAO;
import org.nikita.jdbctask.dto.OrderDetailDTO;

import java.io.IOException;

@WebServlet("/createOrderDetails")
public class CreateOrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/product.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDetailDTO orderDetail = new OrderDetailDTO(null, null, null);

        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        orderDetailDAO.create(orderDetail);
        resp.getOutputStream().println(orderDetail.toString());
    }
}
