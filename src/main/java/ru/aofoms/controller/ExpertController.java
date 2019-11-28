package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.entity.EntityWithId;
import ru.aofoms.entity.Expert;
import ru.aofoms.service.ExpertService;
import ru.aofoms.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FXMLController
public class ExpertController {

    private final ExpertService expertService;
    private final SaveInfoIdGenerator generator;
    @Autowired // При убирании Autowired происходит ошибка
    private WindowService windowService;

    @Autowired
    protected SaveInfoList saveInfoList;

    @Autowired
    private SolutionPrinter solutionPrinter;

    private long id;
    private Expert expert;
    private ExpertMenuItem expertMenuItem;

    @FXML
    private TextField number;
    @FXML
    private TextField surname;
    @FXML
    private TextField name;
    @FXML
    private TextField patronymic;
    @FXML
    private MaskField snils;
    @FXML
    private ListView<ExpertMenuItem> menuList;
    @FXML
    private ListView<SaveInfo> elementsList = new ListView<>();
    @FXML
    private Button addButton;
    @FXML
    private Button delButton;

    public ExpertController(ExpertService expertService, SaveInfoIdGenerator generator) {
        this.expertService = expertService;
        this.generator = generator;
    }

    @FXML
    public void initialize() {
        elementsList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && elementsList.getSelectionModel().getSelectedItem() != null) {
                SaveInfo saveInfo = elementsList.getSelectionModel().getSelectedItem();

                Stage stage = new Stage();
                Scene scene = new Scene(windowService.getView(saveInfo.getObject()));

                stage.setScene(scene);
                ElementController controller = windowService.getController(saveInfo.getObject());
                controller.setValues(saveInfo.getObject());
                controller.setEditMode(true);
                controller.setSIId(saveInfo.getId());

                stage.setTitle(saveInfo.getExpertDocType().toString());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }
        });

        addButton.setOnAction(event -> addAction(expertMenuItem.getExpertDocType()));

        delButton.setOnAction(event -> deleteAction(elementsList));
    }

    void setValues(long id) {

        saveInfoList.clearSaveInfoList();

        List<ExpertMenuItem> menuItemList = new ArrayList<>();

        for (ExpertDocType docType : ExpertDocType.values()) {
            ExpertMenuItem expertMenuItem = new ExpertMenuItem(docType);
            menuItemList.add(expertMenuItem);
        }

        ObservableList<ExpertMenuItem> menuExpertList = FXCollections.observableList(menuItemList);
        menuList.setItems(menuExpertList);
        menuList.getSelectionModel().select(0);
        menuList.getFocusModel().focus(0);
        expertMenuItem = menuList.getSelectionModel().getSelectedItem();

        this.id = id;
        this.expert = expertService.findById(id);

        number.setText(expert.getNumber());
        surname.setText(expert.getSurname());
        name.setText(expert.getName());
        patronymic.setText(expert.getPatronymic());
        snils.setText(expert.getSnils());

        elementsSetting();
    }

    @FXML
    public void menuClick() {
        expertMenuItem = menuList.getSelectionModel().getSelectedItem();
        elementsSetting();
    }

    @FXML
    public void cancel(ActionEvent event) {

        if (isEdit()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Сохранение изменений");
            alert.setHeaderText("Вы сделали изменения");
            alert.setContentText("Вы уверены, что хотите отменить изменения?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {
                saveInfoList.clearSaveInfoList();
                generator.clearValue();
                ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
            } else if (option.get() == ButtonType.CANCEL) {
                alert.close();
            }
        } else {
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        }
    }

    boolean isEdit() {
        boolean edit = false;

        for (SaveInfo e : saveInfoList.getSaveInfoList()) {
            if (e.getAction() == Action.ADD || e.getAction() == Action.EDIT || e.getAction() == Action.DELETE) {
                edit = true;
            }
        }
        return edit;
    }

    @FXML
    public void ok() {

        if (isEdit()) {
            try {
                expertService.updateExpert(saveInfoList.getSaveInfoList());
                expertService.updateEditDate(expert);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(e.toString());

                e.printStackTrace();
            }
        }

        saveInfoList.clearSaveInfoList();
        generator.clearValue();
        elementsSetting();
    }

    void onClose(WindowEvent event) {
        saveInfoList.clearSaveInfoList();
        generator.clearValue();
        ((Stage) event.getSource()).close();
    }

    private void addAction(ExpertDocType expertDocType) {
        Stage stage = new Stage();
        Scene scene = new Scene(windowService.getView(expertDocType));

        stage.setScene(scene);

        ElementController elementController = windowService.getController(expertDocType);
        elementController.setEditMode(false);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle(expertDocType.toString());
        stage.show();
    }

    private void deleteAction(ListView<SaveInfo> elementList) {
        SaveInfo selectedItem = elementList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {

            if (selectedItem.getAction() == Action.ADD && selectedItem.getExpertDocType() == ExpertDocType.SPECIALTY) {
                saveInfoList.removeExpertiseElement(selectedItem);
            }


            saveInfoList.removeElement(selectedItem);
            if (selectedItem.getAction() != Action.ADD)
                saveInfoList.addElement(new SaveInfo(selectedItem.getId(), Action.DELETE, selectedItem.getObject()));
        }

        elementList.getItems().remove(selectedItem);
    }

    public void addSaveInfo(SaveInfo saveInfo) {
        saveInfoList.addSaveInfo(saveInfo, expert);
        elementsSetting();
    }

    // При добавлении нового элемента - исправить метод expertService.getElements
    private void elementsSetting() {
        ObservableList<EntityWithId> listItems = expertService.getElements(id, expertMenuItem.getExpertDocType());
        ObservableList<SaveInfo> listToView = saveInfoList.getListToView(expertMenuItem.getExpertDocType(), listItems);

        elementsList.setItems(listToView);
        elementsList.setCellFactory(new Callback<ListView<SaveInfo>, ListCell<SaveInfo>>() {
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
                            } else
                                setStyle("-fx-control-inner-background: derive(-fx-base, 80%);");
                        }
                    }
                };
            }
        });
        elementsList.refresh();
    }

    public long getId() {
        return id;
    }

    public List<SaveInfo> getSaveInfoList() {
        return saveInfoList.getSaveInfoList();
    }

    @FXML
    private void printIncluding() {
        solutionPrinter.aboutInclusion(expert);
    }

    @FXML
    private void printExcluding() {
        solutionPrinter.aboutExclusion(expert);
    }
}
