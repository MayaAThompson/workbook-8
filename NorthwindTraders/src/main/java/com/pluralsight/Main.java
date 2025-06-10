package com.pluralsight;

import com.pluralsight.utils.IOUtils;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        try {
            mySqlQuery();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was a Sql issue");
        }
    }

    private static void mySqlQuery() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = IOUtils.messageAndResponse("User: ");
        String password = IOUtils.messageAndResponse("Pass: ");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);

        Statement statement = connection.createStatement();

        String query = "SELECT * FROM products ORDER BY ProductID;";

        ResultSet results = statement.executeQuery(query);
        StringBuilder stringOut = new StringBuilder();
        while (results.next()) {
            int productId = results.getInt("ProductID");
            String productName = results.getString("ProductName");
            double unitPrice = results.getDouble("UnitPrice");
            int unitsInStock = results.getInt("UnitsInStock");

            String singleResult = String.format("Product ID: %d\nProduct Name: %s\nPrice: %.2f\nUnits in Stock: %d", productId, productName, unitPrice, unitsInStock);
            stringOut.append(singleResult);
            stringOut.append("\n\n--------------------\n\n");

        }
        System.out.println(stringOut);
        connection.close();
    }


}
