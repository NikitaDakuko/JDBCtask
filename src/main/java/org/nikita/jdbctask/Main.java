package org.nikita.jdbctask;

import org.nikita.jdbctask.dao.ProductDAO;

public class Main {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        productDAO.findById(1L);
    }
}