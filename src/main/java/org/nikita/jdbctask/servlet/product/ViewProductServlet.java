package org.nikita.jdbctask.servlet.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.mapper.dto.ProductDTOMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("products")
public class ViewProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO dao = new ProductDAO();
        ProductDTOMapper mapper = new ProductDTOMapper();
        List<ProductDTO> productList = new ArrayList<>();

        try {
            Long id = Long.parseLong(req.getParameter("id"));
            productList.add(mapper.fromResult(dao.findById(id)));
        } catch (Exception e) {
            productList = mapper.listFromResult(dao.getAll());
        }
        resp.getOutputStream().println(productList.toString());
    }
}


