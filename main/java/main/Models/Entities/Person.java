package main.Models.Entities;

import java.io.Serializable;



public class Person implements Serializable{

    private int person_id;
    private String name;
    private String surname;

    public Person() {
        this.person_id = -1;
        this.name = "";

        this.surname = "";
    }

    public Person(int id, String name, String lastname) {
        this.person_id = id;
        this.name = name;
        this.surname = lastname;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String lastname) {
        this.surname = lastname;
    }

    @Override
    public String toString() {
        return "Person data:" + this.person_id + " " + this.name + " " + this.surname +" ";
    }
}
