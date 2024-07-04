package org.nikita.jdbctask.servlet.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.entity.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/products")
public class ViewProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO db = new ProductDAO();
        List<Product> productList = new ArrayList<>();

        try {
            Long id = Long.parseLong(req.getParameter("id"));
            productList.add(db.findById(id));
        } catch (Exception e){
            productList = db.getAll();
        } finally {
            resp.getOutputStream().println(productList.toString());
        }
    }
}


