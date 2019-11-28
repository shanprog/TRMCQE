package ru.aofoms.controller;

public interface ElementController {

    void setValues(Object object);

    void setEditMode(boolean editMode);

    boolean isEditMode();

    void setSIId(String id);

    void setNewExpert(boolean newExpert);

    boolean isNewExpert();
}
