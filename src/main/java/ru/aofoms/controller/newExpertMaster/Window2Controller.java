package ru.aofoms.controller.newExpertMaster;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import ru.aofoms.controller.NewExpertMasterController;
import ru.aofoms.util.ExpertDocType;
import ru.aofoms.util.SaveInfo;

@FXMLController
public class Window2Controller extends NewExpertMasterController {

    @FXML
    private ListView<SaveInfo> listPassport;
    @FXML
    private ListView<SaveInfo> listPhone;
    @FXML
    private ListView<SaveInfo> listEmail;

    @FXML
    private void next() {

        if (check()) {
            window3Controller.setSaveInfoList(saveInfoList.getSaveInfoList());
            window3Controller.setExpert(expert);

            showWindow(window3View, window3Controller);
            stage.close();
        }
    }

    @FXML
    private void back() {
        window1Controller.setSaveInfoList(saveInfoList.getSaveInfoList());
        window1Controller.setExpert(expert);

        showWindow(window1View, window1Controller);
        stage.close();
    }

    @FXML
    private void editPassport(MouseEvent mouseEvent) {
        editAction(mouseEvent);
    }

    @FXML
    private void addPassport() {
        addAction(ExpertDocType.PASSPORT);
    }

    @FXML
    private void delPassport() {
        delAction(listPassport);
    }

    @FXML
    private void editPhone(MouseEvent mouseEvent) {
        editAction(mouseEvent);
    }

    @FXML
    private void addPhone() {
        addAction(ExpertDocType.PHONE);
    }

    @FXML
    private void delPhone() {
        delAction(listPhone);
    }

    @FXML
    private void editEmail(MouseEvent mouseEvent) {
        editAction(mouseEvent);
    }

    @FXML
    private void addEmail() {
        addAction(ExpertDocType.EMAIL);
    }

    @FXML
    private void delEmail() {
        delAction(listEmail);
    }

    @Override
    public boolean check() {
        boolean check = true;

        disableErrorStyle(listPhone, listPassport);

        if (errorsInListView(listPhone, listPassport)) {
            check = false;
        }

        return check;
    }

    @Override
    public void setElementsValues() {
        ObservableList listPassportItems = FXCollections.observableArrayList();
        ObservableList<SaveInfo> listToViewPassport = saveInfoList.getListToView(ExpertDocType.PASSPORT, listPassportItems);

        listPassport.setItems(listToViewPassport);
        listPassport.refresh();

        ObservableList listPhoneItems = FXCollections.observableArrayList();
        ObservableList<SaveInfo> listToViewPhone = saveInfoList.getListToView(ExpertDocType.PHONE, listPhoneItems);

        listPhone.setItems(listToViewPhone);
        listPhone.refresh();

        ObservableList listEmailItems = FXCollections.observableArrayList();
        ObservableList<SaveInfo> listToViewEmail = saveInfoList.getListToView(ExpertDocType.EMAIL, listEmailItems);

        listEmail.setItems(listToViewEmail);
        listEmail.refresh();
    }

}
