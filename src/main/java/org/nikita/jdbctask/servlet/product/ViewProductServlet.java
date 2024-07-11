package org.nikita.jdbctask.servlet.product;

import jakarta.servlet.ServletContext;
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

@WebServlet("/products")
public class ViewProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO dao = new ProductDAO();
        List<ProductDTO> productList = new ArrayList<>();

        try {
            Long id = Long.parseLong(req.getParameter("id"));
            productList.add(dao.findById(id));
        } catch (Exception e) {
            productList = dao.getAll();
        }

        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("tableName", "Products");
        servletContext.setAttribute("records", productList);
        getServletContext().getRequestDispatcher("/WEB-INF/productTable.jsp").forward(req, resp);
    }
}


