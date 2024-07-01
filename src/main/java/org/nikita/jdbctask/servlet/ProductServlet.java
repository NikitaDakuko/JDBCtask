package org.nikita.jdbctask.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.entity.Money;
import org.nikita.jdbctask.entity.Product;

import java.io.IOException;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/product.html").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product p = new Product(
                req.getParameter("productName"),
                new Money(
                        Integer.parseInt(req.getParameter("productPrice")),
                        req.getParameter("productCurrency")),
                Integer.parseInt(req.getParameter("productQuantity")),
                Boolean.getBoolean(req.getParameter("productAvailability")));

        resp.getOutputStream().println(p.toString());
    }
}
