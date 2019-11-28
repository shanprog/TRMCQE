package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.aofoms.controller.newExpertMaster.Window3Controller;
import ru.aofoms.entity.Workplace;
import ru.aofoms.util.Action;
import ru.aofoms.util.SaveInfo;

@FXMLController
public class WorkplaceController extends AbstractElementController {

    private final Window3Controller window3Controller;

    @FXML
    private TextField nameField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField addressField;

    private Workplace workplace;

    public WorkplaceController(Window3Controller window3Controller) {
        this.window3Controller = window3Controller;
    }

    @FXML
    public void ok(ActionEvent event) {

        if (check()) {
            Workplace workplace;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                workplace = this.workplace;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                workplace = new Workplace();

                saveInfo.setId(siGenerator.getNextValue(Workplace.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(workplace);

            saveInfo.setObject(workplace);

            if (isNewExpert())
                window3Controller.addSaveInfo(saveInfo);
            else
                expertController.addSaveInfo(saveInfo);

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        }
    }

    @Override
    protected boolean check() {

        boolean check = true;

        disableErrorStyle(nameField, positionField);

        if (errorsInTextField(nameField, positionField))
            check = false;

        if (!isEditMode() && check) {
            Workplace tempWorkplace = new Workplace();
            assembleElement(tempWorkplace);

            if (findDuplicates(tempWorkplace)) {
                enableErrorStyle(nameField, positionField);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((Workplace) assembledElement).setName(nameField.getText().trim());
        ((Workplace) assembledElement).setPosition(positionField.getText().trim());
        ((Workplace) assembledElement).setAddress(addressField.getText().trim());
    }

    @Override
    public void setValues(Object object) {
        workplace = (Workplace) object;

        nameField.setText(workplace.getName());
        positionField.setText(workplace.getPosition());
        addressField.setText(workplace.getAddress());
    }
}
