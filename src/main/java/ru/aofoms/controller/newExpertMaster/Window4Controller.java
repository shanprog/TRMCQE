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
public class Window4Controller extends NewExpertMasterController {

    @FXML
    private ListView<SaveInfo> listDiplomas;
    @FXML
    private ListView<SaveInfo> listAcademicDiplomas;
    @FXML
    private ListView<SaveInfo> listTrainingDocs;

    @FXML
    private void next() {
        if (check()) {
            window5Controller.setSaveInfoList(saveInfoList.getSaveInfoList());
            window5Controller.setExpert(expert);

            showWindow(window5View, window5Controller);
            stage.close();
        }
    }

    @FXML
    private void back() {
        window3Controller.setSaveInfoList(saveInfoList.getSaveInfoList());
        window3Controller.setExpert(expert);

        showWindow(window3View, window3Controller);
        stage.close();
    }

    @FXML
    private void editDiploma(MouseEvent mouseEvent) {
        editAction(mouseEvent);
    }

    @FXML
    private void addDiploma() {
        addAction(ExpertDocType.DIPLOMA);
    }

    @FXML
    private void delDiploma() {
        delAction(listDiplomas);
    }

    @FXML
    private void editAcademicDiploma(MouseEvent mouseEvent) {
        editAction(mouseEvent);
    }

    @FXML
    private void addAcademicDiploma() {
        addAction(ExpertDocType.ACADEMIC_DIPLOMA);
    }

    @FXML
    private void delAcademicDiploma() {
        delAction(listAcademicDiplomas);
    }

    @FXML
    private void editTrainingDoc(MouseEvent mouseEvent) {
        editAction(mouseEvent);
    }

    @FXML
    private void addTrainingDoc() {
        addAction(ExpertDocType.TRAINING_DOC);
    }

    @FXML
    private void delTrainingDoc() {
        delAction(listTrainingDocs);
    }

    @Override
    public boolean check() {
        boolean check = true;

        disableErrorStyle(listDiplomas, listTrainingDocs);

        if (errorsInListView(listDiplomas, listTrainingDocs)) {
            check = false;
        }

        return check;
    }

    @Override
    public void setElementsValues() {
        ObservableList listDiplomatItems = FXCollections.observableArrayList();
        ObservableList<SaveInfo> listToViewDiploma = saveInfoList.getListToView(ExpertDocType.DIPLOMA, listDiplomatItems);

        listDiplomas.setItems(listToViewDiploma);
        listDiplomas.refresh();

        ObservableList listAcademicDiplomasItems = FXCollections.observableArrayList();
        ObservableList<SaveInfo> listToViewAcademicDiplomas = saveInfoList.getListToView(ExpertDocType.ACADEMIC_DIPLOMA, listAcademicDiplomasItems);

        listAcademicDiplomas.setItems(listToViewAcademicDiplomas);
        listAcademicDiplomas.refresh();

        ObservableList listTrainingDocsItems = FXCollections.observableArrayList();
        ObservableList<SaveInfo> listToViewTrainingDocs = saveInfoList.getListToView(ExpertDocType.TRAINING_DOC, listTrainingDocsItems);

        listTrainingDocs.setItems(listToViewTrainingDocs);
        listTrainingDocs.refresh();
    }

}
