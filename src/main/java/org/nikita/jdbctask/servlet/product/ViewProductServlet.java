package org.nikita.jdbctask.servlet.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.dto.ProductDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet for viewing all products in database.
 * Can view individual product by passing ID as a parameter
 */
@WebServlet("/products")
public class ViewProductServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO dao = new ProductDAO();
        List<ProductDTO> productList = new ArrayList<>();

        try {
            Long id = Long.parseLong(req.getParameter("id"));
            productList.add(dao.findById(id));
        } catch (Exception e) {
            productList = dao.getAll();
        }

        req.setAttribute("tableName", "Products");
        req.setAttribute("records", productList);
        req.getRequestDispatcher("/WEB-INF/productTable.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse  response) throws IOException, ServletException{
        doGet(request, response);
    }
}


