package ca.jrvs.application;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {
    public static void main(String[] args) {
        DataBaseConnectionManager dataBaseConnectionManager = new DataBaseConnectionManager("localhost", "hplussport", "postgres", "password");
        Customer customer;
        try {
            Connection connection = dataBaseConnectionManager.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);

            customer = customerDAO.findById(10000);
            System.out.println("before update:" +customer.getEmail());

            customer.setEmail("newEmail@email.com");
            customer = customerDAO.update(customer);
            System.out.println("after update:" +customer.getEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

