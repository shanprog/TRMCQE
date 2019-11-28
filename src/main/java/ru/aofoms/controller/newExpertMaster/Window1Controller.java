package ru.aofoms.controller.newExpertMaster;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.aofoms.controller.NewExpertMasterController;
import ru.aofoms.entity.Expert;
import ru.aofoms.service.ExpertService;
import ru.aofoms.util.MaskField;

import java.time.LocalDate;

@FXMLController
public class Window1Controller extends NewExpertMasterController {

    private final ExpertService expertService;

    @FXML
    private MaskField numberField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField patronymicField;
    @FXML
    private MaskField snilsField;
    @FXML
    private TextField info;

    public Window1Controller(ExpertService expertService) {
        this.expertService = expertService;
    }

    @FXML
    public void initialize() {
        numberField.setText(expertService.getNextNumber());
    }

    public void next() {

        if (check()) {
            expert = new Expert();
            assembleExpert();

            window2Controller.setSaveInfoList(saveInfoList.getSaveInfoList());
            window2Controller.setExpert(expert);

            showWindow(window2View, window2Controller);
            stage.close();
        }

    }

    @Override
    public boolean check() {

        boolean check = true;

        disableErrorStyle(numberField, surnameField, nameField, snilsField);

        if (errorsInTextField(numberField, surnameField, nameField, snilsField)) {
            check = false;
        }

        if (errorsInMaskField(numberField, snilsField)) {
            check = false;
        }


        if (expertService.findNumber(numberField.getText()) != null) {
            enableErrorStyle(numberField);
            check = false;
        }

        if (expertService.findSnils(snilsField.getText()) != null) {
            enableErrorStyle(snilsField);
            check = false;
        }

        return check;
    }

    private void assembleExpert() {
        expert.setNumber(numberField.getText());
        expert.setName(nameField.getText().trim());
        expert.setSurname(surnameField.getText().trim());
        expert.setPatronymic(patronymicField.getText().trim());
        expert.setSnils(snilsField.getText());
        expert.setEditDate(LocalDate.now());
    }

    @Override
    public void setElementsValues() {
        if (expert != null) {
            numberField.setText(expert.getNumber());
            surnameField.setText(expert.getSurname());
            nameField.setText(expert.getName());
            patronymicField.setText(expert.getPatronymic());
            snilsField.setText(expert.getSnils());
        }
    }
}
