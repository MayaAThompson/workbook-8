package com.pluralsight;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        try {
            mySqlQuery();
        } catch (SQLException e) {
            System.out.println("There was a Sql issue");
        }
    }

    private static void mySqlQuery() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root";
        String password = "yearup";
        Connection connection = DriverManager.getConnection(url, user, password);

        Statement statement = connection.createStatement();

        String query = "SELECT * FROM products ORDER BY ProductName;";

        ResultSet results = statement.executeQuery(query);
        while (results.next()) {
            String result = results.getString("ProductName");
            System.out.println(result);
        }
        connection.close();
    }


}
