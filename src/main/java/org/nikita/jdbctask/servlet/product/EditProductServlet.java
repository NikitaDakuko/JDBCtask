package org.nikita.jdbctask.servlet.product;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.entity.Money;
import org.nikita.jdbctask.entity.Product;

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
        Product p = new Product(
                this.id,
                req.getParameter("productName"),
                new Money(
                        Integer.parseInt(req.getParameter("productPrice")),
                        req.getParameter("productCurrency")),
                Integer.parseInt(req.getParameter("productQuantity")),
                Boolean.parseBoolean(req.getParameter("productAvailability")));

        ProductDAO.update(p);
        resp.getOutputStream().println(p.toString());
    }
}
