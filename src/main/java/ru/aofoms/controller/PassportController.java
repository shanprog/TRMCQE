package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.aofoms.controller.newExpertMaster.Window2Controller;
import ru.aofoms.controller.newExpertMaster.Window3Controller;
import ru.aofoms.entity.Passport;
import ru.aofoms.util.Action;
import ru.aofoms.util.MaskField;
import ru.aofoms.util.SaveInfo;

import java.time.LocalDate;

@FXMLController
public class PassportController extends AbstractElementController {

    private final Window2Controller window2Controller;

    @FXML
    private TextField seriesField;
    @FXML
    private TextField numberField;
    @FXML
    private TextArea outOrgArea;
    @FXML
    private MaskField dateField;
    @FXML
    private TextArea registrationArea;

    private Passport passport;

    public PassportController(Window2Controller window2Controller) {
        this.window2Controller = window2Controller;
    }

    @FXML
    public void ok(ActionEvent event) {

        if (check()) {
            Passport passport;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                passport = this.passport;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                passport = new Passport();

                saveInfo.setId(siGenerator.getNextValue(Passport.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(passport);

            saveInfo.setObject(passport);

            if (isNewExpert())
                window2Controller.addSaveInfo(saveInfo);
            else
                expertController.addSaveInfo(saveInfo);

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        }

    }

    @Override
    protected boolean check() {

        boolean check = true;

        disableErrorStyle(numberField, outOrgArea, registrationArea, dateField);

        if (errorsInTextField(numberField, outOrgArea, registrationArea))
            check = false;

        if (errorsInDateField(dateField)) {
            check = false;
        }

        if (!isEditMode() && check) {
            Passport tempPassport = new Passport();
            assembleElement(tempPassport);

            if (findDuplicates(tempPassport)) {
                enableErrorStyle(numberField, outOrgArea, registrationArea, dateField);
                check = false;
            }
        }

        return check;
    }

    @Override
    public void setValues(Object object) {
        passport = (Passport) object;

        seriesField.setText(passport.getSeries());
        numberField.setText(passport.getNumber());
        outOrgArea.setText(passport.getOutOrg());
        dateField.setText(passport.getOutDate().format(formatter));
        registrationArea.setText(passport.getRegistration());
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((Passport) assembledElement).setSeries(seriesField.getText().trim());
        ((Passport) assembledElement).setNumber(numberField.getText().trim());
        ((Passport) assembledElement).setOutOrg(outOrgArea.getText().trim());
        ((Passport) assembledElement).setOutDate(LocalDate.parse(dateField.getText(), formatter));
        ((Passport) assembledElement).setRegistration(registrationArea.getText().trim());
    }
}
