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
        servletContext.setAttribute("table", formTable(productList));

        getServletContext().getRequestDispatcher("/WEB-INF/table.jsp").forward(req, resp);
    }

    private String formTable(List<ProductDTO> productDTOS) {
        StringBuilder table = new StringBuilder(
                "<td>id</td>\n" +
                        "<td>name</td>\n" +
                        "<td>price</td>\n" +
                        "<td>quantity</td>\n" +
                        "<td>available</td>\n" +
                        "<td>actions</td>\n"
        );

        for (ProductDTO p : productDTOS) {
            table.append("<td>").append(p.getId()).append("</td>\n")
                    .append("<td>").append(p.getName()).append("</td>\n")
                    .append("<td>").append(p.getPrice()).append("</td>\n")
                    .append("<td>").append(p.getQuantity()).append("</td>\n")
                    .append("<td>").append(p.getAvailability() ? "Available" : "Unavailable").append("</td>\n")
                    .append("<td> edit delete </td>\n");
        }
        return table.toString();
    }
}


