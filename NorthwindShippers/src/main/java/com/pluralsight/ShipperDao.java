package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipperDao {

    private final BasicDataSource dataSource;
    private static final String URL = "jdbc:mysql://localhost:3306/northwind";
    private static final String USER = "root";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public ShipperDao() {
        this.dataSource = new BasicDataSource();
        this.dataSource.setUrl(URL);
        this.dataSource.setUsername(USER);
        this.dataSource.setPassword(PASSWORD);
    }

    public void insertNewShipper(String companyName, String phone) {
        try (Connection connection = dataSource.getConnection()) {
            String insert = """
                    insert into shippers (CompanyName, Phone)
                    	values (?, ?);""";
            PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, companyName);
            statement.setString(2, phone);
            int rowsUpdated = statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            System.out.println(rowsUpdated + "row(s) updated");
            while (keys.next())
                System.out.println("New Shipper Key: " + keys.getLong(1));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Shipper> getAllShippers() {
        List<Shipper> shippers = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = """
                    SELECT *
                    FROM shippers;""";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int id = results.getInt("ShipperID");
                String name = results.getString("CompanyName");
                String phone = results.getString("Phone");

                Shipper shipper = new Shipper(id, name, phone);
                shippers.add(shipper);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippers;
    }

    public void updateShipperPhoneNumber(int id, String phoneNumber) {
        int rowsUpdated = 0;
        try (Connection connection = dataSource.getConnection()) {
            String update = """
                    update shippers
                    	set Phone = ?
                        where ShipperID = ?""";
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, phoneNumber);
            statement.setString(2, String.valueOf(id));
            rowsUpdated = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(rowsUpdated + " row(s) updated.");
    }

    public void deleteShipper (int id) {
        int rowsUpdated = 0;
        try (Connection connection = dataSource.getConnection()) {
            String delete = """
                    delete from shippers
                    	where ShipperID = ?;""";
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setString(1, String.valueOf(id));
            rowsUpdated = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(rowsUpdated + "row(s) updated.");
    }

    public void displayShipperList(List<Shipper> shippers) {
        for (Shipper shipper : shippers) {
            System.out.println("\n" + shipper);
        }
    }
}
