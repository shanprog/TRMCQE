package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.controller.newExpertMaster.Window4Controller;
import ru.aofoms.entity.AcademicDegree;
import ru.aofoms.entity.AcademicDiploma;
import ru.aofoms.service.AcademicDiplomaService;
import ru.aofoms.util.Action;
import ru.aofoms.util.MaskField;
import ru.aofoms.util.SaveInfo;

import java.time.LocalDate;

@FXMLController
public class AcademicDiplomaController extends AbstractElementController {


    @FXML
    private TextField seriesField;
    @FXML
    private TextField numberField;
    @FXML
    private MaskField outDateField;
    @FXML
    private ComboBox<AcademicDegree> stepBox;

    private AcademicDiploma academicDiploma;

    private final AcademicDiplomaService service;
    @Autowired
    private Window4Controller window4Controller;

    public AcademicDiplomaController(AcademicDiplomaService service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        stepBox.setItems(FXCollections.observableArrayList(service.getAcademicDegrees()));
    }

    @FXML
    public void ok(ActionEvent event) {

        if (check()) {
            AcademicDiploma academicDiploma;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                academicDiploma = this.academicDiploma;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                academicDiploma = new AcademicDiploma();

                saveInfo.setId(siGenerator.getNextValue(AcademicDiploma.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(academicDiploma);

            saveInfo.setObject(academicDiploma);

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

        disableErrorStyle(numberField, stepBox, outDateField);

        if (errorsInTextField(numberField)) {
            check = false;
        }

        if (stepBox.getSelectionModel().getSelectedIndex() == -1) {
            enableErrorStyle(stepBox);
            check = false;
        }

        if (errorsInDateField(outDateField)) {
            check = false;
        }

        if (!isEditMode() && check) {
            AcademicDiploma tempAcademicDiploma = new AcademicDiploma();
            assembleElement(tempAcademicDiploma);

            if (findDuplicates(tempAcademicDiploma)) {
                enableErrorStyle(numberField, stepBox, outDateField);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((AcademicDiploma) assembledElement).setSeries(seriesField.getText().trim());
        ((AcademicDiploma) assembledElement).setNumber(numberField.getText().trim());
        ((AcademicDiploma) assembledElement).setOutDate(LocalDate.parse(outDateField.getText(), formatter));
        ((AcademicDiploma) assembledElement).setAcademicDegree(stepBox.getValue());
    }

    @Override
    public void setValues(Object object) {
        academicDiploma = (AcademicDiploma) object;

        seriesField.setText(academicDiploma.getSeries());
        numberField.setText(academicDiploma.getNumber());
        outDateField.setText(academicDiploma.getOutDate().format(formatter));
        stepBox.getSelectionModel().select(academicDiploma.getAcademicDegree());
    }
}
