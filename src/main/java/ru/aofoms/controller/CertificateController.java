package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.controller.newExpertMaster.Window5Controller;
import ru.aofoms.entity.Certificate;
import ru.aofoms.entity.CodeGVS;
import ru.aofoms.entity.ExpertSpecialty;
import ru.aofoms.service.CertificateService;
import ru.aofoms.service.ExpertSpecialtyService;
import ru.aofoms.util.Action;
import ru.aofoms.util.MaskField;
import ru.aofoms.util.SaveInfo;

import java.time.LocalDate;

@FXMLController
public class CertificateController extends AbstractElementController {

    @Autowired
    private Window5Controller window5Controller;

    @FXML
    private TextField seriesField;
    @FXML
    private TextField numberField;
    @FXML
    private MaskField outDateField;
    @FXML
    private TextField outOrgField;
    @FXML
    private ComboBox<ExpertSpecialty> specBox;

    private final ExpertSpecialtyService specialtyService;

    private Certificate certificate;

    public CertificateController(ExpertSpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @FXML
    public void initialize() {
        specBox.setItems(FXCollections.observableArrayList(specialtyService.findByExpertId(expertController.getId())));
    }

    @FXML
    public void ok(ActionEvent event) {
        if (check()) {
            Certificate certificate;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                certificate = this.certificate;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                certificate = new Certificate();

                saveInfo.setId(siGenerator.getNextValue(Certificate.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(certificate);

            saveInfo.setObject(certificate);

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

        disableErrorStyle(outDateField, specBox);

        if (errorsInDateField(outDateField)) {
            check = false;
        }

        if (specBox.getSelectionModel().getSelectedIndex() == -1) {
            enableErrorStyle(specBox);
            check = false;
        }

        if (!isEditMode() && check) {
            Certificate tempCertificate = new Certificate();
            assembleElement(tempCertificate);

            if (findDuplicates(tempCertificate)) {
                enableErrorStyle(specBox, outDateField);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((Certificate) assembledElement).setSeries(seriesField.getText().trim());
        ((Certificate) assembledElement).setNumber(numberField.getText().trim());
        ((Certificate) assembledElement).setOutDate(LocalDate.parse(outDateField.getText(), formatter));
        ((Certificate) assembledElement).setOutOrg(outOrgField.getText().trim());
        ((Certificate) assembledElement).setSpecialty(specBox.getValue());
    }

    @Override
    public void setValues(Object object) {
        certificate = (Certificate) object;

        seriesField.setText(certificate.getSeries());
        numberField.setText(certificate.getNumber());
        outDateField.setText(certificate.getOutDate().format(formatter));
        outOrgField.setText(certificate.getOutOrg());
        specBox.getSelectionModel().select(certificate.getSpecialty());
    }

    void setSpecialtiesList(ObservableList<ExpertSpecialty> specialtiesList) {
        specBox.setItems(specialtiesList);
    }
}
