package com.pluralsight;

public class Actor {

    int actorId;
    String firstName;
    String lastName;

    public Actor(int actorId, String firstName, String lastName) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return ("\nID: " + this.actorId +
                "\nFirst Name: " + this.firstName +
                "\nLast Name: " + this.lastName);
    }
}
