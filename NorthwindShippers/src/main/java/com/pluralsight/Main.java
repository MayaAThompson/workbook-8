package com.pluralsight;

public class Main {

    public static void main(String[] args) {
        ShipperDao dataManager = new ShipperDao();

        dataManager.displayShipperList(dataManager.getAllShippers());

        System.out.println("Add a new shipper");
        String companyName = "TransUS Freight";
        String phoneNumber = "214-1233-4567";
        dataManager.insertNewShipper(companyName, phoneNumber);

        dataManager.displayShipperList(dataManager.getAllShippers());

        System.out.println("Update the phone number");
        dataManager.updateShipperPhoneNumber(7, "214-098-7654");

        dataManager.displayShipperList(dataManager.getAllShippers());

        System.out.println("Delete a shipper");
        dataManager.deleteShipper(7);

        dataManager.displayShipperList(dataManager.getAllShippers());

    }
}
