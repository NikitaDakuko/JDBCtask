package org.nikita.jdbctask.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nikita.jdbctask.DatabaseConfig;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("createSchema")
public class CreateSchemaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DatabaseConfig.recreateSchema(DatabaseConfig.getConnection());
        } catch (SQLException e) {
            resp.getOutputStream().println("Could not create schema, SQLException :" + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
