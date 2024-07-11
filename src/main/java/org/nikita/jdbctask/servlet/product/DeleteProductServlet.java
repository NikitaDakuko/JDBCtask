package org.nikita.jdbctask.servlet.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.ProductDAO;

import java.io.IOException;

/**
 * Servlet for deleting products
 * takes ID parameter
 */
@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        ProductDAO dao = new ProductDAO();
        dao.delete(id);
        req.getRequestDispatcher("products").forward(req, resp);
    }
}
