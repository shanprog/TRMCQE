package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.aofoms.entity.ExpertSpecialty;
import ru.aofoms.entity.Expertise;
import ru.aofoms.service.ExpertSpecialtyService;
import ru.aofoms.util.Action;
import ru.aofoms.util.MaskField;
import ru.aofoms.util.SaveInfo;

@FXMLController
public class ExpertiseController extends AbstractElementController {

    @FXML
    private ComboBox<ExpertSpecialty> specialtyBox;
    @FXML
    private MaskField year;
    @FXML
    private TextField exp;
    @FXML
    private TextField reexp;

    private Expertise expertise;

    private final ExpertSpecialtyService service;

    public ExpertiseController(ExpertSpecialtyService service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        specialtyBox.setItems(FXCollections.observableArrayList(service.findByExpertId(expertController.getId())));
    }

    @FXML
    public void ok(ActionEvent event) {
        if (check()) {
            Expertise expertise;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                expertise = this.expertise;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                expertise = new Expertise();

                saveInfo.setId(siGenerator.getNextValue(Expertise.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(expertise);

            saveInfo.setObject(expertise);
            expertController.addSaveInfo(saveInfo);

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        }
    }

    @Override
    protected boolean check() {
        boolean check = true;

        disableErrorStyle(specialtyBox, year, exp, reexp);

        if (errorsInTextField(exp, reexp, year))
            check = false;

        for (char ch : year.getText().trim().toCharArray())
            if (ch == '_') {
                enableErrorStyle(year);
                check = false;
            }

        for (char ch : exp.getText().toCharArray())
            if (!Character.isDigit(ch)) {
                enableErrorStyle(exp);
                check = false;
            }

        for (char ch : reexp.getText().toCharArray())
            if (!Character.isDigit(ch)) {
                enableErrorStyle(reexp);
                check = false;
            }

        if (specialtyBox.getSelectionModel().getSelectedIndex() == -1) {
            enableErrorStyle(specialtyBox);
            check = false;
        }

        if (!isEditMode() && check) {
            Expertise tempExpertise = new Expertise();
            assembleElement(tempExpertise);

            if (findDuplicates(tempExpertise)) {
                enableErrorStyle(specialtyBox, year);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((Expertise) assembledElement).setCount(Integer.valueOf(exp.getText().trim()));
        ((Expertise) assembledElement).setReexp(Integer.valueOf(reexp.getText().trim()));
        ((Expertise) assembledElement).setYear(Integer.valueOf(year.getText()));
        ((Expertise) assembledElement).setSpecialty(specialtyBox.getSelectionModel().getSelectedItem());
    }

    @Override
    public void setValues(Object object) {
        expertise = (Expertise) object;

        specialtyBox.getSelectionModel().select(expertise.getSpecialty());
        year.setText(String.valueOf(expertise.getYear()));
        exp.setText(String.valueOf(expertise.getCount()));
        reexp.setText(String.valueOf(expertise.getReexp()));
    }
}
