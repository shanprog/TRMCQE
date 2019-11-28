package ru.aofoms.controller;

import de.felixroske.jfxsupport.AbstractFxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.controller.newExpertMaster.*;
import ru.aofoms.entity.Expert;
import ru.aofoms.util.ExpertDocType;
import ru.aofoms.util.SaveInfo;
import ru.aofoms.util.SaveInfoList;
import ru.aofoms.util.WindowService;
import ru.aofoms.view.newExpertMaster.*;

import java.util.List;

public abstract class NewExpertMasterController implements StyleController {

    protected Stage stage;
    protected Expert expert;

    @Autowired
    protected SaveInfoList saveInfoList;

    @Autowired
    protected WindowService windowService;
    @Autowired
    protected Window1View window1View;
    @Autowired
    protected Window1Controller window1Controller;
    @Autowired
    protected Window2View window2View;
    @Autowired
    protected Window2Controller window2Controller;
    @Autowired
    protected Window3View window3View;
    @Autowired
    protected Window3Controller window3Controller;
    @Autowired
    protected Window4View window4View;
    @Autowired
    protected Window4Controller window4Controller;
    @Autowired
    protected Window5View window5View;
    @Autowired
    protected Window5Controller window5Controller;

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void cancel(ActionEvent event) {
        saveInfoList.clearSaveInfoList();
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    protected void showWindow(AbstractFxmlView windowView, NewExpertMasterController windowController) {
        Stage nextStage = new Stage();
        nextStage.setScene(new Scene(windowView.getView()));
        nextStage.setX(stage.getX());
        nextStage.setY(stage.getY());
        nextStage.setTitle(stage.getTitle());
        nextStage.setResizable(false);
        windowController.setStage(nextStage);
        windowController.setElementsValues();
        nextStage.setResizable(false);
        nextStage.show();
    }

    public abstract boolean check();

    public void setSaveInfoList(List<SaveInfo> saveInfoList) {
        this.saveInfoList.setSaveInfoList(saveInfoList);
    }

    protected void delAction(ListView<SaveInfo> list) {
        SaveInfo selectedItem = list.getSelectionModel().getSelectedItem();

        if (selectedItem != null)
            saveInfoList.removeElement(selectedItem);

        list.getItems().remove(selectedItem);
    }

    protected void addAction(ExpertDocType expertDocType) {
        Stage stage = new Stage();
        Scene scene = new Scene(windowService.getView(expertDocType));

        stage.setScene(scene);

        ElementController elementController = windowService.getController(expertDocType);
        elementController.setEditMode(false);
        elementController.setNewExpert(true);

        if (expertDocType == ExpertDocType.QUALIFICATION) {
            ((QualificationDocController) elementController).setSpecialtiesList(saveInfoList.getSpecialtiesList());
        }

        if (expertDocType == ExpertDocType.SPECIALTY) {
            ((ExpertSpecialtyController) elementController).setWorkplacesList(saveInfoList.getWorkplacesList());
        }

        if (expertDocType == ExpertDocType.CERTIFICATE) {
            ((CertificateController) elementController).setSpecialtiesList(saveInfoList.getSpecialtiesList());

        }

        stage.setTitle(expertDocType.toString());
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    protected void editAction(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            @SuppressWarnings("unchecked")
            SaveInfo saveInfo = ((ListView<SaveInfo>) mouseEvent.getSource()).getSelectionModel().getSelectedItem();

            Stage stage = new Stage();
            Scene scene = new Scene(windowService.getView(saveInfo.getExpertDocType()));

            stage.setScene(scene);
            ElementController controller = windowService.getController(saveInfo.getExpertDocType());
            controller.setValues(saveInfo.getObject());
            controller.setEditMode(true);
            controller.setSIId(saveInfo.getId());
            controller.setNewExpert(true);

            if (saveInfo.getExpertDocType() == ExpertDocType.QUALIFICATION) {
                ((QualificationDocController) controller).setSpecialtiesList(saveInfoList.getSpecialtiesList());
            }

            if (saveInfo.getExpertDocType() == ExpertDocType.SPECIALTY) {
                ((ExpertSpecialtyController) controller).setWorkplacesList(saveInfoList.getWorkplacesList());
            }

            if (saveInfo.getExpertDocType() == ExpertDocType.CERTIFICATE) {
                ((CertificateController) controller).setSpecialtiesList(saveInfoList.getSpecialtiesList());
            }

            stage.setTitle(saveInfo.getExpertDocType().toString());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }

    protected void addSaveInfo(SaveInfo saveInfo) {
        saveInfoList.addSaveInfo(saveInfo, expert);
        setElementsValues();
    }

    public abstract void setElementsValues();
}
