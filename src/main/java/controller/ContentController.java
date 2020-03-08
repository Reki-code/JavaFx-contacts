package main.java.controller;

import data.model.Person;

public class ContentController {
    private Person currentPerson;
    public void setCurrentPerson(Person Person) {
        currentPerson = Person;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }
}
