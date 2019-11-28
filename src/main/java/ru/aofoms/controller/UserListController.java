package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.service.UserService;
import ru.aofoms.util.Action;
import ru.aofoms.util.CleverList;
import ru.aofoms.util.SaveInfo;
import ru.aofoms.view.UserView;

@FXMLController
public class UserListController {

    @Autowired
    private UserView userView;
    @Autowired
    private UserController userController;

    @Autowired
    private CleverList cleverList;

    @FXML
    private ListView<SaveInfo> userListView;
    private Stage stage;

    private final UserService userService;

    public UserListController(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void initialize() {
        userListView.setItems(cleverList.getObservableList(userService.findAll()));
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void cancel(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void ok(ActionEvent event) {

        try {
            userService.updateUsers(cleverList.getObservableList());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.toString());

            e.printStackTrace();
        }

        cleverList.clear();

        cancel(event);
    }

    @FXML
    private void addUser() {
        Stage stage = new Stage();
        Scene scene = new Scene(userView.getView());

        stage.setScene(scene);

        userController.setEditMode(false);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Пользователи");
        stage.show();
    }

    @FXML
    private void delUser() {
        SaveInfo selectedItem = userListView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {

            if (selectedItem.getAction() == Action.ADD) {
                userListView.getItems().remove(selectedItem);
                cleverList.removeElement(selectedItem);
            } else {
                cleverList.setDeleteMarker(selectedItem);
            }
        }

        paintElements();
        userListView.refresh();
    }

    @FXML
    private void editUser(MouseEvent mouseEvent) {

        if (mouseEvent.getClickCount() == 2 && userListView.getSelectionModel().getSelectedItem() != null) {
            SaveInfo saveInfo = userListView.getSelectionModel().getSelectedItem();

            Stage stage = new Stage();
            Scene scene = new Scene(userView.getView());

            stage.setScene(scene);

            userController.setValues(saveInfo);
            userController.setEditMode(true);

            stage.setTitle("Пользователь");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }

    }

    public void addSaveInfo(SaveInfo saveInfo) {

        if (saveInfo.getAction() == Action.EDIT) {
            cleverList.replaceElement(saveInfo);
        } else if (saveInfo.getAction() == Action.ADD) {
            cleverList.addElement(saveInfo);
        }

        userListView.setItems(cleverList.getObservableList());
        paintElements();
    }

    private void paintElements() {
        userListView.setCellFactory(new Callback<ListView<SaveInfo>, ListCell<SaveInfo>>() {
            @Override
            public ListCell<SaveInfo> call(ListView<SaveInfo> param) {
                return new ListCell<SaveInfo>() {
                    @Override
                    protected void updateItem(SaveInfo item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle("-fx-control-inner-background: derive(-fx-base, 80%);");
                        } else {
                            setText(item.getObject().toString());
                            if (item.getAction() == Action.ADD) {
                                setStyle("-fx-control-inner-background: derive(palegreen, 50%);");
                            } else if (item.getAction() == Action.EDIT) {
                                setStyle("-fx-control-inner-background: derive(sandybrown, 50%);");
                            } else if (item.getAction() == Action.DELETE) {
                                setStyle("-fx-control-inner-background: derive(tomato, 50%);");
                            } else
                                setStyle("-fx-control-inner-background: derive(-fx-base, 80%);");
                        }
                    }
                };
            }
        });
    }
}
