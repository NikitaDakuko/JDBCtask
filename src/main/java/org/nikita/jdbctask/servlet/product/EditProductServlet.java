package org.nikita.jdbctask.servlet.product;

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

@WebServlet("/editProduct")
public class EditProductServlet extends HttpServlet {

    private Long id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.id = Long.parseLong(req.getParameter("id"));
        req.getRequestDispatcher("/WEB-INF/product.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDTO p = new ProductDTO(
                this.id,
                req.getParameter("productName"),
                new PGmoney(Integer.parseInt(req.getParameter("productPrice"))),
                Integer.parseInt(req.getParameter("productQuantity")),
                Boolean.parseBoolean(req.getParameter("productAvailability")));

        ProductDAO dao = new ProductDAO();
        dao.update(p);
        ProductMapper mapper = new ProductMapper();
        resp.getOutputStream().println(mapper.fromDTO(p).toString());
    }
}
