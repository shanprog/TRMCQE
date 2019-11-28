package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.controller.newExpertMaster.Window5Controller;
import ru.aofoms.entity.Category;
import ru.aofoms.entity.ExpertSpecialty;
import ru.aofoms.entity.QualificationDoc;
import ru.aofoms.service.ExpertSpecialtyService;
import ru.aofoms.service.QualificationDocService;
import ru.aofoms.util.Action;
import ru.aofoms.util.MaskField;
import ru.aofoms.util.SaveInfo;

import java.time.LocalDate;

@FXMLController
public class QualificationDocController extends AbstractElementController {

    @Autowired
    private Window5Controller window5Controller;

    @FXML
    private ComboBox<ExpertSpecialty> specialtyBox;
    @FXML
    private ComboBox<Category> categoryBox;
    @FXML
    private TextField seriesField;
    @FXML
    private TextField numberField;
    @FXML
    private MaskField outDateField;
    @FXML
    private TextArea outOrgArea;

    private QualificationDoc qualificationDoc;
    private boolean extendCheck = false;

    private final QualificationDocService qualificationDocService;
    private final ExpertSpecialtyService expertSpecialtyService;

    public QualificationDocController(QualificationDocService qualificationDocService, ExpertSpecialtyService expertSpecialtyService) {
        this.qualificationDocService = qualificationDocService;
        this.expertSpecialtyService = expertSpecialtyService;
    }

    @FXML
    public void initialize() {
        disableFields(true);
        categoryBox.setItems(FXCollections.observableArrayList(qualificationDocService.getCategories()));
        specialtyBox.setItems(FXCollections.observableArrayList(expertSpecialtyService.findByExpertId(expertController.getId())));
    }

    @FXML
    public void categoryListener() {
        if (categoryBox.getSelectionModel().getSelectedItem().getCode() == 0) {
            disableErrorStyle(categoryBox, outDateField, numberField);
            disableFields(true);
        } else {
            disableFields(false);
        }
    }

    private void disableFields(boolean disable) {

        if (disable) {
            seriesField.setDisable(true);
            seriesField.setText("");
            numberField.setDisable(true);
            numberField.setText("");
            outDateField.setDisable(true);
            outDateField.setPlainText("");
            outOrgArea.setDisable(true);
            outOrgArea.setText("");
            extendCheck = false;
        } else {
            seriesField.setDisable(false);
            numberField.setDisable(false);
            outDateField.setDisable(false);
            outOrgArea.setDisable(false);
            extendCheck = true;
        }
    }

    @FXML
    public void ok(ActionEvent event) {
        if (check()) {
            QualificationDoc qualificationDoc;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                qualificationDoc = this.qualificationDoc;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                qualificationDoc = new QualificationDoc();

                saveInfo.setId(siGenerator.getNextValue(QualificationDoc.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(qualificationDoc);

            saveInfo.setObject(qualificationDoc);

            if (isNewExpert())
                window5Controller.addSaveInfo(saveInfo);
            else
                expertController.addSaveInfo(saveInfo);

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        }
    }

    @Override
    protected boolean check() {

        boolean check = true;

        disableErrorStyle(numberField, categoryBox, outDateField, specialtyBox);

        if (specialtyBox.getSelectionModel().getSelectedIndex() == -1) {
            enableErrorStyle(specialtyBox);
            check = false;
        }

        if (extendCheck) {
            if (errorsInTextField(numberField)) {
                check = false;
            }

            if (errorsInDateField(outDateField)) {
                check = false;
            }
        }

        if (categoryBox.getSelectionModel().getSelectedIndex() == -1) {
            enableErrorStyle(categoryBox);
            check = false;
        }

        if (!isEditMode() && check) {
            QualificationDoc tempQualification = new QualificationDoc();
            assembleElement(tempQualification);

            if (findDuplicates(tempQualification)) {
                enableErrorStyle(numberField, categoryBox, outDateField);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {

        ((QualificationDoc) assembledElement).setCategory(categoryBox.getValue());
        ((QualificationDoc) assembledElement).setSpecialty(specialtyBox.getValue());
        if (extendCheck) {
            ((QualificationDoc) assembledElement).setSeries(seriesField.getText().trim());
            ((QualificationDoc) assembledElement).setNumber(numberField.getText().trim());
            ((QualificationDoc) assembledElement).setOutOrg(outOrgArea.getText().trim());
            ((QualificationDoc) assembledElement).setOutDate(LocalDate.parse(outDateField.getText(), formatter));
        } else {
            ((QualificationDoc) assembledElement).setSeries(null);
            ((QualificationDoc) assembledElement).setNumber(null);
            ((QualificationDoc) assembledElement).setOutOrg(null);
            ((QualificationDoc) assembledElement).setOutDate(null);
        }

    }

    @Override
    public void setValues(Object object) {
        qualificationDoc = (QualificationDoc) object;

        if (qualificationDoc.getCategory().getCode() != 0) {
            seriesField.setText(qualificationDoc.getSeries());
            numberField.setText(qualificationDoc.getNumber());
            outDateField.setText(qualificationDoc.getOutDate().format(formatter));
            outOrgArea.setText(qualificationDoc.getOutOrg());
        } else {
            seriesField.setText("");
            numberField.setText("");
            outOrgArea.setText("");
        }
        categoryBox.getSelectionModel().select(qualificationDoc.getCategory());
        specialtyBox.getSelectionModel().select(qualificationDoc.getSpecialty());

        categoryListener();
    }

    void setSpecialtiesList(ObservableList<ExpertSpecialty> specialtiesList) {
        specialtyBox.setItems(specialtiesList);
    }
}
