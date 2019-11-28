package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.entity.User;
import ru.aofoms.util.Action;
import ru.aofoms.util.SaveInfo;
import ru.aofoms.util.SaveInfoIdGenerator;

@FXMLController
public class UserController implements StyleController {
    @FXML
    private TextField surnameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField patronymicField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox activeCheck;

    @Autowired
    private SaveInfoIdGenerator siGenerator;

    @Autowired
    private UserListController userListController;

    private boolean editMode = false;

    private User user;
    private String siid;

    @FXML
    private void cancel(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void ok(ActionEvent event) {

        if (check()) {
            User user;
            SaveInfo saveInfo = new SaveInfo();

            if (isEditMode()) {
                user = this.user;

                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                user = new User();

                saveInfo.setId(siGenerator.getNextValue(User.class));
                saveInfo.setAction(Action.ADD);
            }

            assembleElement(user);

            saveInfo.setObject(user);

            userListController.addSaveInfo(saveInfo);

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        }

    }

    protected boolean check() {

        boolean check = true;

        disableErrorStyle(loginField, passwordField);

        if (errorsInTextField(loginField, passwordField))
            check = false;

        return check;
    }

    protected void assembleElement(Object assembledElement) {
        ((User) assembledElement).setSurname(surnameField.getText().trim());
        ((User) assembledElement).setName(nameField.getText().trim());
        ((User) assembledElement).setPatronymic(patronymicField.getText().trim());
        ((User) assembledElement).setLogin(loginField.getText().trim());
        ((User) assembledElement).setPassword(DigestUtils.md5Hex(passwordField.getText().trim()));
        ((User) assembledElement).setActive(activeCheck.isSelected());
    }

    // Вместо Object передал SaveInfo
    public void setValues(SaveInfo object) {
        user = (User) object.getObject();

        siid = object.getId();

        surnameField.setText(user.getSurname());
        nameField.setText(user.getName());
        patronymicField.setText(user.getPatronymic());
        loginField.setText(user.getLogin());
        activeCheck.setSelected(user.getActive());
    }

    public void setEditMode(boolean editMode){
        this.editMode = editMode;
    }

    public boolean isEditMode() {
        return editMode;
    }
}
