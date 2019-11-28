package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.view.UserListView;

@FXMLController
public class MainAdminController {

    @Autowired
    private UserListView userListView;

    @Autowired
    private UserListController userListController;

    @FXML
    private void listUsers(ActionEvent event) {
        Stage stage = new Stage();
        stage.setScene(new Scene(userListView.getView()));

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle("Пользователи");
        stage.setResizable(false);
        userListController.setStage(stage);

        stage.show();
    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }
}
