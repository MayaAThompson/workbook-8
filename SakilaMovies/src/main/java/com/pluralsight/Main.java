package com.pluralsight;

import com.pluralsight.utils.IOUtils;

public class Main {

    public static void main(String[] args) {

        boolean keepRunning = true;
        while (keepRunning) {
            keepRunning = mainMenuOperation(mainMenuUi());
        }

    }

    public static int mainMenuUi() {
        System.out.println("""
                
                what would you like to do?
                1) Search for an actor
                2) Search films by actor ID
                0) Exit""");
        return IOUtils.messageAndResponseInt("Enter your choice: ");
    }

    public static boolean mainMenuOperation(int i) {
        SakilaDataManager dataManager = new SakilaDataManager();
        boolean keepRunning = true;
        switch (i) {
            case 1 -> dataManager.printActorList(dataManager.getActorsByName(IOUtils.messageAndResponse("Enter actor name: ")));
            case 2 -> dataManager.printFilmList(dataManager.getFilmByActorId(IOUtils.messageAndResponseInt("Enter actor ID: ")));
            case 0 -> keepRunning= false;
            default -> System.out.println("Please select and available option.");
        }
        return keepRunning;
    }
}
