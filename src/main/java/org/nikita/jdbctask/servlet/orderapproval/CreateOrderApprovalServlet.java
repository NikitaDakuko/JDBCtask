package org.nikita.jdbctask.servlet.orderapproval;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.OrderApprovalDAO;
import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.enums.OrderStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/createOrderApproval")
public class CreateOrderApprovalServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/orderDetail.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            OrderApprovalDTO p = new OrderApprovalDTO(
                    new OrderDetailDTO(
                            OrderStatus.valueOf(req.getParameter("orderStatus")),
                            createDummies(req.getParameter("products")),
                            new BigDecimal(req.getParameter("totalAmount")))
            );

            new OrderApprovalDAO().create(p);
            resp.getOutputStream().println(p.toString());
        } catch (SQLException e) {
            System.out.println("Could not create order aprroval. SQLException: " + e);
        }
    }

    private List<ProductDTO> createDummies(String productsString) throws SQLException {
        List<Long> ids = new ArrayList<>();
        for (String s:productsString.replaceAll("\\s+","").split(",")){
            ids.add(Long.parseLong(s));
        }

//        List<ProductDTO> dummies = new ArrayList<>();
//        for(Long id: ids)
//            dummies.add(new ProductDTO(id, "", null, 0, false));
//        return dummies;

        return new ProductDAO().getMultiple(ids);
    }
}
