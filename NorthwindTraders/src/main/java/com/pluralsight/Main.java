package com.pluralsight;

import com.pluralsight.utils.IOUtils;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class Main {
    static String user = "root";
    static String password = System.getenv("DB_PASSWORD");

    public static void main(String[] args) {
        if (password != null)
            System.out.println("saved password retrieved");
        else
            System.out.println("DB_PASSWORD environment variable not set.");

        String url = "jdbc:mysql://localhost:3306/northwind";

        try (BasicDataSource dataSource = new BasicDataSource()) {
            dataSource.setUrl(url);
            dataSource.setUsername(user);
            dataSource.setPassword(password);
            boolean keepMenuRunning = true;

            while (keepMenuRunning) {
                int choice = mainMenuUI();

                switch (choice) {
                    case 1 -> displayAllProducts(dataSource);
                    case 2 -> displayAllCustomers(dataSource);
                    case 3 -> displayAllCategories(dataSource);
                    case 0 -> keepMenuRunning = false;
                    default -> System.out.println("Please choose an available option.");
                }
//            IOUtils.pauseReturn();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("oops, MySQL ran into an issue");
        }
    }

    private static void displayAllCategories(BasicDataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM categories ORDER BY CategoryID");

            ResultSet categories = statement.executeQuery();

            while (categories.next()) {
                int categoryId = categories.getInt("CategoryID");
                String categoryName = categories.getString("CategoryName");

                System.out.println(categoryId + ") " + categoryName);
            }
            int choice = IOUtils.messageAndResponseInt("Choose a category number: ");

            String query = "SELECT * FROM products WHERE CategoryID = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, choice);

            ResultSet products = statement.executeQuery();
            StringBuilder stringOut = new StringBuilder();

            while (products.next()) {
                int productId = products.getInt("ProductID");
                String productName = products.getString("ProductName");
                double unitPrice = products.getDouble("UnitPrice");
                int unitsInStock = products.getInt("UnitsInStock");

                String singleResult = String.format("Product ID: %d\nProduct Name: %s\nPrice: %.2f\nUnits in Stock: %d", productId, productName, unitPrice, unitsInStock);
                stringOut.append(singleResult);
                stringOut.append("\n\n--------------------\n\n");

            }
            System.out.println(stringOut);
        } catch (SQLException e) {
            System.out.println("there was a SQL problem");
        }
    }

    private static void displayAllCustomers(BasicDataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {

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
        } catch (SQLException e) {
            System.out.println("There was a Sql issue");
        }
    }

    private static void displayAllProducts(BasicDataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {

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
        } catch (SQLException e) {
            System.out.println("There was a Sql issue");
        }
    }

    public static int mainMenuUI() {
        System.out.println("""
                What do you want to do?
                1) Display all products
                2) Display all customers
                3) Display all categories
                0) Exit""");
        return IOUtils.messageAndResponseInt("Choose an option: ");
    }

}
