package org.nikita.jdbctask.servlet.orderapproval;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.OrderApprovalDAO;
import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.enums.OrderStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/editOrderApproval")
public class EditOrderApprovalServlet extends HttpServlet {

    private Long id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.id = Long.parseLong(req.getParameter("id"));
        req.getRequestDispatcher("/WEB-INF/orderDetail.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderApprovalDTO p = new OrderApprovalDTO(
                new OrderDetailDTO(
                        OrderStatus.valueOf(req.getParameter("orderStatus")),
                        null, //new ProductDTOMapper().parseProductsString(req.getParameter("products")),
                        new BigDecimal(req.getParameter("totalAmount")))
        );

        try {
            new OrderApprovalDAO().update(p);
            resp.getOutputStream().println(p.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
