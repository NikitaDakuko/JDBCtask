package org.nikita.jdbctask.servlet.order.approval;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.mapper.ProductMapper;
import org.postgresql.util.PGmoney;

import java.io.IOException;

@WebServlet("/createOrderApproval")
public class CreateOrderApprovalServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/product.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDTO p = new ProductDTO(
                req.getParameter("productName"),
                new PGmoney(Integer.parseInt(req.getParameter("productPrice"))),
                Integer.parseInt(req.getParameter("productQuantity")),
                Boolean.parseBoolean(req.getParameter("productAvailability")));

        ProductDAO productDAO = new ProductDAO();
        productDAO.create(p);
        ProductMapper mapper = new ProductMapper();
        resp.getOutputStream().println(mapper.fromDTO(p).toString());
    }
}
