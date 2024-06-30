package org.nikita.jdbctask;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/storePreferences")
public class StorePreferencesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/coffee_preferences.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] coffeeTypes = req.getParameterValues("coffeeType");
        HttpSession session = req.getSession();
        session.setAttribute("userCoffeeTypes", coffeeTypes);

        resp.sendRedirect("coffeeDashboard");
    }
}