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
import ru.aofoms.entity.InviteOrg;
import ru.aofoms.entity.InviteOrgCode;
import ru.aofoms.entity.Passport;
import ru.aofoms.service.InviteOrgService;
import ru.aofoms.util.Action;
import ru.aofoms.util.SaveInfo;
import ru.aofoms.util.SaveInfoIdGenerator;

@FXMLController
public class InviteOrgController implements StyleController {

    @Autowired
    private InviteOrgListController inviteOrgListController;
    @Autowired
    private SaveInfoIdGenerator siGenerator;
    @Autowired
    private InviteOrgService inviteOrgService;

    @FXML
    private ComboBox<InviteOrgCode> orgTypeBox;
    @FXML
    private TextField orgNameField;
    @FXML
    private TextField addressField;

    private boolean editMode = false;
    private InviteOrg inviteOrg;
    private String siid;
    private boolean extendCheck;

    @FXML
    public void initialize() {
        orgTypeBox.setItems(FXCollections.observableArrayList(inviteOrgService.findAllInviteOrgCode()));
    }

    @FXML
    private void typeListener() {
        if (orgTypeBox.getSelectionModel().getSelectedItem().getCode() == 7) {
            disableErrorStyle(orgTypeBox);
            disableFields(true);
        } else {
            disableFields(false);
        }
    }

    private void disableFields(boolean disable) {

        if (disable) {
            addressField.setDisable(true);
            addressField.setText("");
            orgNameField.setDisable(true);
            orgNameField.setText("");
            extendCheck = false;
        } else {
            addressField.setDisable(false);
            orgNameField.setDisable(false);
            extendCheck = true;
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void ok(ActionEvent event) {
        if (check()) {
            InviteOrg inviteOrg;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                inviteOrg = this.inviteOrg;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                inviteOrg = new InviteOrg();

                saveInfo.setId(siGenerator.getNextValue(InviteOrg.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(inviteOrg);

            saveInfo.setObject(inviteOrg);

            inviteOrgListController.addSaveInfo(saveInfo);

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        }
    }

    protected boolean check() {
        boolean check = true;

        disableErrorStyle(orgTypeBox, orgNameField, addressField);

        if (orgTypeBox.getSelectionModel().getSelectedIndex() == -1) {
            enableErrorStyle(orgTypeBox);
            check = false;
        }

        if (extendCheck) {
            if (errorsInTextField(orgNameField, addressField)) {
                check = false;
            }
        }

        if (!isEditMode() && check) {
            InviteOrg tempInviteOrg = new InviteOrg();
            assembleElement(tempInviteOrg);

            if (findDuplicates(tempInviteOrg)) {
                if (orgTypeBox.getSelectionModel().getSelectedItem().getCode() == 7)
                    enableErrorStyle(orgTypeBox);
                else
                    enableErrorStyle(orgTypeBox, orgNameField, addressField);

                check = false;
            }
        }

        return check;
    }

    private boolean findDuplicates(InviteOrg tempInviteOrg) {
        for (SaveInfo si : inviteOrgListController.getSaveInfoList()) {

            // Проверка на DELETE Action

            if (si.getObject().equals(tempInviteOrg)) {
                return true;
            }
        }

        return false;
    }

    private void assembleElement(InviteOrg tempInviteOrg) {
        tempInviteOrg.setInviteOrgCode(orgTypeBox.getSelectionModel().getSelectedItem());
        tempInviteOrg.setName(orgNameField.getText().trim());
        tempInviteOrg.setAddress(addressField.getText().trim());
    }

    public boolean isEditMode() {
        return editMode;
    }

    void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setValues(Object object) {
        inviteOrg = (InviteOrg) object;
        orgTypeBox.getSelectionModel().select(inviteOrg.getInviteOrgCode());

        if (inviteOrg.getInviteOrgCode().getCode() != 7) {
            orgNameField.setText(inviteOrg.getName());
            addressField.setText(inviteOrg.getAddress());
        } else {
            orgNameField.setText("");
            addressField.setText("");
        }

        typeListener();
    }

    void setSIId(String siid) {
        this.siid = siid;
    }
}
