package com.pluralsight;

import com.pluralsight.utils.IOUtils;
import java.sql.*;

public class Main {
    static String user = IOUtils.messageAndResponse("User: ");
    static String password = IOUtils.messageAndResponse("Pass: ");

    public static void main(String[] args) {

        boolean keepMenuRunning = true;

        while (keepMenuRunning) {
            int choice = mainMenuUI();

            switch (choice) {
                case 1 -> displayAllProducts();
                case 2 -> displayAllCustomers();
                case 0 -> keepMenuRunning = false;
                default -> System.out.println("Please choose an available option.");
            }
            IOUtils.pauseReturn();
        }
    }

    private static void displayAllCustomers() {
        String url = "jdbc:mysql://localhost:3306/northwind";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers ORDER BY Country;");

            ResultSet results = statement.executeQuery();
            StringBuilder stringOut = new StringBuilder();
            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phoneNumber = results.getString("Phone");

                String singleResult = String.format("Contact Name: %s\nCompany Name: %s\nCity: %s\nCountry: %s\nPhone Number: %s", contactName, companyName, city, country, phoneNumber);
                stringOut.append(singleResult);
                stringOut.append("\n\n--------------------\n\n");

            }
            System.out.println(stringOut);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was a Sql issue");
        }
    }

    private static void displayAllProducts() {
        String url = "jdbc:mysql://localhost:3306/northwind";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM products ORDER BY ProductName;");

            ResultSet results = statement.executeQuery();
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
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was a Sql issue");
        }
    }

    public static int mainMenuUI() {
        System.out.println("""
                What do you want to do?
                1) Display all products
                2) Display all customers
                0) Exit""");
        return IOUtils.messageAndResponseInt("Choose an option: ");
    }

}
