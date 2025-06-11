package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

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

    public boolean insertNewShipper() {
        throw new RuntimeException("Not yet implemented");
    }

    public List<Shipper> getAllShippers() {
        throw new RuntimeException("Not yet implemented");
    }
}
