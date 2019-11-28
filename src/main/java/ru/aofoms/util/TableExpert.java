package ru.aofoms.util;

import ru.aofoms.entity.Expert;

public class TableExpert {

    private long id;
    private String number;
    private String lastName;
    private String firstName;
    private String middleName;
    private String exclusion;

    public TableExpert(Expert expert, String exclusion) {
        this.id = expert.getId();
        this.number = expert.getNumber();
        this.lastName = expert.getSurname();
        this.firstName = expert.getName();
        this.middleName = expert.getPatronymic();
        this.exclusion = exclusion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getExclusion() {
        return exclusion;
    }

    public void setExclusion(String exclusion) {
        this.exclusion = exclusion;
    }
}
