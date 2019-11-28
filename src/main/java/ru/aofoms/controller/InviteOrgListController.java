package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.entity.InviteOrg;
import ru.aofoms.service.InviteOrgService;
import ru.aofoms.util.Action;
import ru.aofoms.util.SaveInfo;
import ru.aofoms.util.SaveInfoList;
import ru.aofoms.view.InviteOrgView;

import java.util.List;

@FXMLController
public class InviteOrgListController {

    @Autowired
    private SaveInfoList saveInfoList;
    @Autowired
    private InviteOrgView inviteOrgView;
    @Autowired
    private InviteOrgController inviteOrgController;
    @Autowired
    private InviteOrgService inviteOrgService;

    @FXML
    private ListView<SaveInfo> listInviteOrgs;

    protected Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        elementSetting();
    }

    private void elementSetting() {
        ObservableList listItems = FXCollections.observableArrayList(inviteOrgService.findAll());
        ObservableList<SaveInfo> listToView = saveInfoList.getListToViewInviteOrg(listItems);

        listInviteOrgs.setItems(listToView);
        listInviteOrgs.refresh();
    }

    @FXML
    public void cancel() {
        saveInfoList.clearSaveInfoList();
        stage.close();
    }

    @FXML
    public void save() {

        saveInfoList.getSaveInfoList().forEach(saveInfo -> {
            if (saveInfo.getAction() == Action.ADD || saveInfo.getAction() == Action.EDIT) {
                inviteOrgService.save((InviteOrg) saveInfo.getObject());
            }

            if (saveInfo.getAction() == Action.DELETE) {
                inviteOrgService.delete((InviteOrg) saveInfo.getObject());
            }
        });

        saveInfoList.clearSaveInfoList();

        elementSetting();
    }

    @FXML
    public void editInviteOrg(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            SaveInfo saveInfo = listInviteOrgs.getSelectionModel().getSelectedItem();

            Stage stage = new Stage();
            Scene scene = new Scene(inviteOrgView.getView());

            stage.setScene(scene);

            inviteOrgController.setValues(saveInfo.getObject());
            inviteOrgController.setEditMode(true);
            inviteOrgController.setSIId(saveInfo.getId());

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }

    @FXML
    public void addInviteOrg() {
        Stage stage = new Stage();
        Scene scene = new Scene(inviteOrgView.getView());

        stage.setScene(scene);

        inviteOrgController.setEditMode(false);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    public void delInviteOrg() {
        SaveInfo selectedItem = listInviteOrgs.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            saveInfoList.removeElement(selectedItem);
            if (selectedItem.getAction() != Action.ADD)
                saveInfoList.addElement(new SaveInfo(selectedItem.getId(), Action.DELETE, selectedItem.getObject()));
        }

        listInviteOrgs.getItems().remove(selectedItem);
    }

    public void addSaveInfo(SaveInfo saveInfo) {
        saveInfoList.addElement(saveInfo);
        elementSetting();
    }

    public List<SaveInfo> getSaveInfoList() {
        return saveInfoList.getSaveInfoList();
    }

}
