package ru.aofoms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.util.MaskField;
import ru.aofoms.util.SaveInfo;
import ru.aofoms.util.SaveInfoIdGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class AbstractElementController implements ElementController, StyleController {

    @Autowired
    protected ExpertController expertController;

    @Autowired
    protected SaveInfoIdGenerator siGenerator;

    private boolean newExpert;
    private boolean editMode;
    String siid;

    protected abstract boolean check();

    protected abstract void assembleElement(Object assembledElement);

    @Override
    public boolean isNewExpert() {
        return newExpert;
    }

    @Override
    public void setNewExpert(boolean newExpert) {
        this.newExpert = newExpert;
    }

    @Override
    public void setSIId(String id) {
        this.siid = id;
    }

    @Override
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    @Override
    public boolean isEditMode() {
        return editMode;
    }

    @FXML
    public void cancel(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    boolean findDuplicates(Object o1) {

        for (SaveInfo si : expertController.getSaveInfoList()) {

            // Проверка на DELETE Action

            if (si.getObject().equals(o1)) {
                return true;
            }
        }

        return false;
    }
}
