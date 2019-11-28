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
public class Window3Controller extends NewExpertMasterController {

    @FXML
    private ListView<SaveInfo> listInclusions;
    @FXML
    private ListView<SaveInfo> listWorkplace;

    @FXML
    private void next() {
        if (check()) {
            window4Controller.setSaveInfoList(saveInfoList.getSaveInfoList());
            window4Controller.setExpert(expert);

            showWindow(window4View, window4Controller);
            stage.close();
        }
    }

    @FXML
    private void back() {
        window2Controller.setSaveInfoList(saveInfoList.getSaveInfoList());
        window2Controller.setExpert(expert);

        showWindow(window2View, window2Controller);
        stage.close();
    }

    @FXML
    private void editWorkplace(MouseEvent mouseEvent) {
        editAction(mouseEvent);
    }

    @FXML
    private void addWorkplace() {
        addAction(ExpertDocType.WORKPLACE);
    }

    @FXML
    private void delWorkplace() {
        delAction(listWorkplace);
    }

    @FXML
    private void editInclusions(MouseEvent mouseEvent) {
        editAction(mouseEvent);
    }

    @FXML
    private void addInclusions() {
        addAction(ExpertDocType.INCLUSION);
    }

    @FXML
    private void delInclusions() {
        delAction(listInclusions);
    }

    @Override
    public boolean check() {
        boolean check = true;

        disableErrorStyle(listWorkplace, listInclusions);

        if (errorsInListView(listWorkplace, listInclusions)) {
            check = false;
        }

        return check;
    }

    @Override
    public void setElementsValues() {
        ObservableList listInclusionsItems = FXCollections.observableArrayList();
        ObservableList<SaveInfo> listToViewInclusions = saveInfoList.getListToView(ExpertDocType.INCLUSION, listInclusionsItems);

        listInclusions.setItems(listToViewInclusions);
        listInclusions.refresh();


        ObservableList listWorkplaceItems = FXCollections.observableArrayList();
        ObservableList<SaveInfo> listToViewWorkplace = saveInfoList.getListToView(ExpertDocType.WORKPLACE, listWorkplaceItems);

        listWorkplace.setItems(listToViewWorkplace);
        listWorkplace.refresh();
    }
}
