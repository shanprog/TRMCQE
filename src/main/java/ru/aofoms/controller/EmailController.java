package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.controller.newExpertMaster.Window2Controller;
import ru.aofoms.entity.Email;
import ru.aofoms.util.Action;
import ru.aofoms.util.SaveInfo;

@FXMLController
public class EmailController extends AbstractElementController {

    private final Window2Controller window2Controller;

    @FXML
    private TextField emailField;

    private Email email;

    public EmailController(Window2Controller window2Controller) {
        this.window2Controller = window2Controller;
    }

    @FXML
    public void ok(ActionEvent event) {

        if (check()) {
            Email email;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                email = this.email;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                email = new Email();

                saveInfo.setId(siGenerator.getNextValue(Email.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(email);

            saveInfo.setObject(email);

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

        disableErrorStyle(emailField);

        if (errorsInTextField(emailField))
            check = false;

        if (check) {
            Email tempEmail = new Email();
            assembleElement(tempEmail);

            if (findDuplicates(tempEmail)) {
                enableErrorStyle(emailField);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((Email) assembledElement).setEmail(emailField.getText().trim());
    }

    @Override
    public void setValues(Object object) {
        email = (Email) object;

        emailField.setText(email.getEmail());
    }
}
