package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import ru.aofoms.entity.Exclusion;
import ru.aofoms.entity.ExclusionReason;
import ru.aofoms.entity.Inclusion;
import ru.aofoms.service.ExclusionService;
import ru.aofoms.util.Action;
import ru.aofoms.util.MaskField;
import ru.aofoms.util.SaveInfo;

import java.time.LocalDate;

@FXMLController
public class ExclusionController extends AbstractElementController {

    @FXML
    private MaskField dateField;
    @FXML
    private ComboBox<ExclusionReason> exclReasonBox;
    @FXML
    private ComboBox<Inclusion> inclBox;

    private Exclusion exclusion;

    private final ExclusionService service;

    public ExclusionController(ExclusionService service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        exclReasonBox.setItems(FXCollections.observableArrayList(service.getExclusionReasons()));
        inclBox.setItems(FXCollections.observableArrayList(service.findExpertInclusions(expertController.getId())));
    }

    @FXML
    public void ok(ActionEvent event) {
        if (check()) {
            Exclusion exclusion;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                exclusion = this.exclusion;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                exclusion = new Exclusion();

                saveInfo.setId(siGenerator.getNextValue(Exclusion.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(exclusion);

            saveInfo.setObject(exclusion);
            expertController.addSaveInfo(saveInfo);

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        }
    }

    @Override
    protected boolean check() {
        boolean check = true;

        disableErrorStyle(dateField, exclReasonBox, inclBox);

        if (errorsInDateField(dateField)) {
            check = false;
        }

        if (exclReasonBox.getSelectionModel().getSelectedIndex() == -1) {
            enableErrorStyle(exclReasonBox);
            check = false;
        }

        if (inclBox.getSelectionModel().getSelectedIndex() == -1) {
            enableErrorStyle(inclBox);
            check = false;
        }

        if (check) {
            Exclusion tempExclusion = new Exclusion();
            assembleElement(tempExclusion);

            if (findDuplicates(tempExclusion)) {
                enableErrorStyle(dateField, exclReasonBox, inclBox);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((Exclusion) assembledElement).setDate(LocalDate.parse(dateField.getText(), formatter));
        ((Exclusion) assembledElement).setExclusionReason(exclReasonBox.getSelectionModel().getSelectedItem());
        ((Exclusion) assembledElement).setInclusion(inclBox.getSelectionModel().getSelectedItem());
    }

    @Override
    public void setValues(Object object) {
        exclusion = (Exclusion) object;

        dateField.setText(exclusion.getDate().format(formatter));
        exclReasonBox.getSelectionModel().select(exclusion.getExclusionReason());
        inclBox.getSelectionModel().select(exclusion.getInclusion());
    }
}
