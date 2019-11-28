package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.aofoms.controller.newExpertMaster.Window4Controller;
import ru.aofoms.entity.Diploma;
import ru.aofoms.util.Action;
import ru.aofoms.util.MaskField;
import ru.aofoms.util.SaveInfo;

import java.time.LocalDate;

@FXMLController
public class DiplomaController extends AbstractElementController {

    private final Window4Controller window4Controller;

    @FXML
    private TextField specialField;
    @FXML
    private TextField seriesField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField outOrgField;
    @FXML
    private MaskField outDateField;

    private Diploma diploma;

    public DiplomaController(Window4Controller window4Controller) {
        this.window4Controller = window4Controller;
    }

    @FXML
    public void ok(ActionEvent event) {

        if (check()) {
            Diploma diploma;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                diploma = this.diploma;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                diploma = new Diploma();

                saveInfo.setId(siGenerator.getNextValue(Diploma.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(diploma);

            saveInfo.setObject(diploma);

            if (isNewExpert())
                window4Controller.addSaveInfo(saveInfo);
            else
                expertController.addSaveInfo(saveInfo);

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        }

    }

    @Override
    protected boolean check() {

        boolean check = true;

        disableErrorStyle(specialField, numberField, outOrgField, outDateField);

        if (errorsInTextField(specialField, numberField, outOrgField)) {
            check = false;
        }

        if (errorsInDateField(outDateField)) {
            check = false;
        }

        if (!isEditMode() && check) {
            Diploma tempDiploma = new Diploma();
            assembleElement(tempDiploma);

            if (findDuplicates(tempDiploma)) {
                enableErrorStyle(specialField, numberField, outOrgField, outDateField);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((Diploma) assembledElement).setSpecial(specialField.getText().trim());
        ((Diploma) assembledElement).setSeries(seriesField.getText().trim());
        ((Diploma) assembledElement).setNumber(numberField.getText().trim());
        ((Diploma) assembledElement).setOutOrg(outOrgField.getText().trim());
        ((Diploma) assembledElement).setOutDate(LocalDate.parse(outDateField.getText(), formatter));
    }

    @Override
    public void setValues(Object object) {
        diploma = (Diploma) object;

        specialField.setText(diploma.getSpecial());
        seriesField.setText(diploma.getSeries());
        numberField.setText(diploma.getNumber());
        outOrgField.setText(diploma.getOutOrg());
        outDateField.setText(diploma.getOutDate().format(formatter));
    }
}
