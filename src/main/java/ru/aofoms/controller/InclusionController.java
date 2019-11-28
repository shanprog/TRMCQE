package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.controller.newExpertMaster.Window3Controller;
import ru.aofoms.entity.Inclusion;
import ru.aofoms.entity.InviteOrg;
import ru.aofoms.service.InviteOrgService;
import ru.aofoms.util.Action;
import ru.aofoms.util.MaskField;
import ru.aofoms.util.SaveInfo;

import java.time.LocalDate;

@FXMLController
public class InclusionController extends AbstractElementController {

    @Autowired
    private Window3Controller window3Controller;

    @FXML
    private MaskField dateField;
    @FXML
    private ComboBox<InviteOrg> inviteOrgBox;
    private Inclusion inclusion;

    private final InviteOrgService inviteOrgService;

    public InclusionController(InviteOrgService inviteOrgService) {
        this.inviteOrgService = inviteOrgService;
    }

    @FXML
    public void initialize() {
        inviteOrgBox.setItems(FXCollections.observableArrayList(inviteOrgService.findAll()));
    }

    @FXML
    public void ok(ActionEvent event) {
        if (check()) {
            Inclusion inclusion;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                inclusion = this.inclusion;

                saveInfo.setId(siid);

                saveInfo.setAction(Action.EDIT);
            } else {
                inclusion = new Inclusion();

                saveInfo.setId(siGenerator.getNextValue(Inclusion.class));
                saveInfo.setAction(Action.ADD);
            }
            assembleElement(inclusion);

            saveInfo.setObject(inclusion);

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

        disableErrorStyle(dateField, inviteOrgBox);

        if (inviteOrgBox.getSelectionModel().getSelectedIndex() == -1) {
            enableErrorStyle(inviteOrgBox);
            check = false;
        }

        if (errorsInDateField(dateField)) {
            check = false;
        }

        if (check) {
            Inclusion tempInclusion = new Inclusion();

            assembleElement(tempInclusion);

            if (findDuplicates(tempInclusion)) {
                enableErrorStyle(inviteOrgBox, dateField);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((Inclusion) assembledElement).setDate(LocalDate.parse(dateField.getText(), formatter));
        ((Inclusion) assembledElement).setInviteOrg(inviteOrgBox.getSelectionModel().getSelectedItem());
    }

    @Override
    public void setValues(Object object) {
        inclusion = (Inclusion) object;

        dateField.setText(inclusion.getDate().format(formatter));
        inviteOrgBox.getSelectionModel().select(inclusion.getInviteOrg());
    }
}
