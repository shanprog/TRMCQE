package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.aofoms.controller.newExpertMaster.Window4Controller;
import ru.aofoms.controller.newExpertMaster.Window5Controller;
import ru.aofoms.entity.TrainingDoc;
import ru.aofoms.util.Action;
import ru.aofoms.util.MaskField;
import ru.aofoms.util.SaveInfo;

import java.time.LocalDate;

@FXMLController
public class TrainingDocController extends AbstractElementController {

    private final Window4Controller window4Controller;

    @FXML
    private TextArea cycleNameArea;
    @FXML
    private MaskField outDateField;
    @FXML
    private TextField hoursField;
    @FXML
    private TextArea outOrgArea;

    private TrainingDoc trainingDoc;

    public TrainingDocController(Window4Controller window4Controller) {
        this.window4Controller = window4Controller;
    }

    @FXML
    public void ok(ActionEvent event) {

        if (check()) {
            TrainingDoc trainingDoc;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                trainingDoc = this.trainingDoc;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                trainingDoc = new TrainingDoc();

                saveInfo.setId(siGenerator.getNextValue(TrainingDoc.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(trainingDoc);

            saveInfo.setObject(trainingDoc);

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

        disableErrorStyle(outDateField, hoursField);

        if (errorsInTextField(hoursField))
            check = false;

        if (errorsInDateField(outDateField)) {
            check = false;
        }

        for (char ch : hoursField.getText().toCharArray())
            if (!Character.isDigit(ch)) {
                enableErrorStyle(hoursField);
                check = false;
            }

        if (!isEditMode() && check) {
            TrainingDoc tempTrainingDoc = new TrainingDoc();
            assembleElement(tempTrainingDoc);

            if (findDuplicates(tempTrainingDoc)) {
                enableErrorStyle(outDateField, hoursField);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((TrainingDoc) assembledElement).setCycleName(cycleNameArea.getText().trim());
        ((TrainingDoc) assembledElement).setOutDate(LocalDate.parse(outDateField.getText(), formatter));
        ((TrainingDoc) assembledElement).setHours(Integer.valueOf(hoursField.getText().trim()));
        ((TrainingDoc) assembledElement).setOutOrg(outOrgArea.getText().trim());
    }

    @Override
    public void setValues(Object object) {
        trainingDoc = (TrainingDoc) object;

        cycleNameArea.setText(trainingDoc.getCycleName());
        outDateField.setText(trainingDoc.getOutDate().format(formatter));
        hoursField.setText(String.valueOf(trainingDoc.getHours()));
        outOrgArea.setText(trainingDoc.getOutOrg());
    }
}
