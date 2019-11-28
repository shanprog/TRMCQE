package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.controller.newExpertMaster.Window2Controller;
import ru.aofoms.entity.Phone;
import ru.aofoms.util.Action;
import ru.aofoms.util.MaskField;
import ru.aofoms.util.SaveInfo;

@FXMLController
public class PhoneController extends AbstractElementController {

    private final Window2Controller window2Controller;

    @FXML
    private MaskField numberField;

    private Phone phone;

    public PhoneController(Window2Controller window2Controller) {
        this.window2Controller = window2Controller;
    }

    @FXML
    public void ok(ActionEvent event) {

        if (check()) {
            Phone phone;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                phone = this.phone;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                phone = new Phone();

                saveInfo.setId(siGenerator.getNextValue(Phone.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(phone);

            saveInfo.setObject(phone);

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

        disableErrorStyle(numberField);

        if (numberField.getPlainText().length() < 10) {
            enableErrorStyle(numberField);
            check = false;
        }

        Phone tempPhone = new Phone();
        assembleElement(tempPhone);

        if (check) {
            if (findDuplicates(tempPhone)) {
                enableErrorStyle(numberField);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((Phone) assembledElement).setNumber(numberField.getText().trim());
    }

    @Override
    public void setValues(Object object) {
        phone = (Phone) object;

        numberField.setText(phone.getNumber());
    }
}
