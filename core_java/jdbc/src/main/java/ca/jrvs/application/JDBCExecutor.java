package ca.jrvs.application;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCExecutor {
    public static void main(String[] args) {
        DataBaseConnectionManager dataBaseConnectionManager = new DataBaseConnectionManager("localhost", "hplussport", "postgres", "password");
        List<Customer> customers = new ArrayList<>();
        try {
            Connection connection = dataBaseConnectionManager.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            customerDAO.findAllSorted(20).forEach(customer -> {System.out.println(customer.getFirstName());});

            for(int i=1;i<3;i++){
                customerDAO.findAllPaged(10, i).forEach(customer -> {System.out.println(customer.getEmail());});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

