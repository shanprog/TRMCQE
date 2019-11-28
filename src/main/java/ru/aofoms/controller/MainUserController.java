package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.aofoms.controller.newExpertMaster.Window1Controller;
import ru.aofoms.service.ExpertService;
import ru.aofoms.util.TableExpert;
import ru.aofoms.view.DownloadXLSView;
import ru.aofoms.view.DownloadXMLView;
import ru.aofoms.view.ExpertView;
import ru.aofoms.view.InviteOrgListView;
import ru.aofoms.view.newExpertMaster.Window1View;

@FXMLController
public class MainUserController {

    private final ExpertService expertService;


    private ObservableList<TableExpert> usersData = FXCollections.observableArrayList();

    @FXML
    private TableView<TableExpert> tableExpertsTV;
    @FXML
    private TableColumn<TableExpert, String> numberCol;
    @FXML
    private TableColumn<TableExpert, String> lastNameCol;
    @FXML
    private TableColumn<TableExpert, String> firstNameCol;
    @FXML
    private TableColumn<TableExpert, String> middleNameCol;
    @FXML
    private TableColumn<TableExpert, String> exclusionCol;

    private final ExpertController expertController;
    private final ExpertView expertView;
    private final Window1Controller newExpertController;
    private final Window1View newExpertView;
    private final InviteOrgListController inviteOrgListController;
    private final InviteOrgListView inviteOrgListView;
    private final DownloadXLSController downloadXLSController;
    private final DownloadXLSView downloadXLSView;
    private final DownloadXMLController downloadXMLController;
    private final DownloadXMLView downloadXMLView;

    public MainUserController(ExpertService expertService, ExpertController expertController, ExpertView expertView, Window1Controller newExpertController, Window1View newExpertView, InviteOrgListController inviteOrgListController, InviteOrgListView inviteOrgListView, DownloadXLSController downloadXLSController, DownloadXLSView downloadXLSView, DownloadXMLController downloadXMLController, DownloadXMLView downloadXMLView) {
        this.expertService = expertService;
        this.expertController = expertController;
        this.expertView = expertView;
        this.newExpertController = newExpertController;
        this.newExpertView = newExpertView;
        this.inviteOrgListController = inviteOrgListController;
        this.inviteOrgListView = inviteOrgListView;
        this.downloadXLSController = downloadXLSController;
        this.downloadXLSView = downloadXLSView;
        this.downloadXMLController = downloadXMLController;
        this.downloadXMLView = downloadXMLView;
    }

    @FXML
    private void initialize() {
        initData();

        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameCol.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        exclusionCol.setCellValueFactory(new PropertyValueFactory<>("exclusion"));

        tableExpertsTV.setItems(usersData);
    }

    private void initData() {
        usersData.setAll(expertService.getExpertForMain());
    }

    @FXML
    private void doubleClickOnExpert(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2 && tableExpertsTV.getSelectionModel().getSelectedIndex() != -1) {
            Stage stage = new Stage();
            stage.setScene(new Scene(expertView.getView()));

            stage.initModality(Modality.APPLICATION_MODAL);

            TableExpert expert = tableExpertsTV.getItems().get(tableExpertsTV.getSelectionModel().getSelectedIndex());
            expertController.setValues(expert.getId());

            stage.setOnCloseRequest(expertController::onClose);
            stage.setTitle(expert.getLastName() + " " + expert.getFirstName() + " " + expert.getMiddleName());
            stage.setResizable(false);

            stage.show();
        }
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    @FXML
    private void newExpert() {
        Stage stage = new Stage();
        stage.setScene(new Scene(newExpertView.getView()));

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle("Новый эксперт");
        stage.setResizable(false);
        newExpertController.setStage(stage);

        stage.show();
    }

    @FXML
    private void inviteOrgs() {
        Stage stage = new Stage();
        stage.setScene(new Scene(inviteOrgListView.getView()));

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle("Предложившие организации");
        stage.setResizable(false);
        inviteOrgListController.setStage(stage);

        stage.show();
    }

    public void updateExperts() {
        usersData.clear();
        initData();
    }

    @FXML
    private void downloadXML() {
        Stage stage = new Stage();
        stage.setScene(new Scene(downloadXMLView.getView()));

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle("Выгрузка в XML");
        stage.setResizable(false);
        downloadXMLController.setStage(stage);

        stage.show();
    }

    @FXML
    private void downloadXLS() {
        Stage stage = new Stage();
        stage.setScene(new Scene(downloadXLSView.getView()));

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle("Выгрузка в XLS");
        stage.setResizable(false);
        downloadXLSController.setStage(stage);

        stage.show();
    }
}
