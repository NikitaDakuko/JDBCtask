package org.nikita.jdbctask.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/CreateProduct")
public class StorePreferencesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/coffeePreferences.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] coffeeTypes = req.getParameterValues("coffeeType");
        HttpSession session = req.getSession();
        session.setAttribute("userCoffeeTypes", coffeeTypes);

        resp.sendRedirect("coffeeDashboard");
    }
}