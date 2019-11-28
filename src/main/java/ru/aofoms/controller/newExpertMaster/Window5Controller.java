package ru.aofoms.controller.newExpertMaster;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.controller.MainUserController;
import ru.aofoms.controller.NewExpertMasterController;
import ru.aofoms.service.ExpertService;
import ru.aofoms.util.ExpertDocType;
import ru.aofoms.util.SaveInfo;

@FXMLController
public class Window5Controller extends NewExpertMasterController {

    @FXML
    private ListView<SaveInfo> listSpecialties;
    @FXML
    private ListView<SaveInfo> listQualifications;
    @FXML
    private ListView<SaveInfo> listCertificates;

    @Autowired
    private ExpertService expertService;
    @Autowired
    private MainUserController mainUserController;

    @SuppressWarnings("unchecked")
    @FXML
    private void save() {

        if (check()) {
            expert.setPassports(saveInfoList.getListByType(ExpertDocType.PASSPORT));
            expert.setPhones(saveInfoList.getListByType(ExpertDocType.PHONE));
            expert.setEmails(saveInfoList.getListByType(ExpertDocType.EMAIL));
            expert.setInclusions(saveInfoList.getListByType(ExpertDocType.INCLUSION));
            expert.setWorkplaces(saveInfoList.getListByType(ExpertDocType.WORKPLACE));
            expert.setDiplomas(saveInfoList.getListByType(ExpertDocType.DIPLOMA));
            expert.setAcademicDiplomas(saveInfoList.getListByType(ExpertDocType.ACADEMIC_DIPLOMA));
            expert.setTrainingDocs(saveInfoList.getListByType(ExpertDocType.TRAINING_DOC));
            expert.setExpertSpecialties(saveInfoList.getListByType(ExpertDocType.SPECIALTY));
            expert.setQualificationDocs(saveInfoList.getListByType(ExpertDocType.QUALIFICATION));

            expertService.save(expert);
            expertService.saveCertificates(saveInfoList.getListByType(ExpertDocType.CERTIFICATE));
            expertService.saveExpertise(saveInfoList.getListByType(ExpertDocType.EXPERTISE));

            mainUserController.updateExperts();

            stage.close();
        }
    }

    @FXML
    private void back() {
        window4Controller.setSaveInfoList(saveInfoList.getSaveInfoList());
        window4Controller.setExpert(expert);

        showWindow(window4View, window4Controller);
        stage.close();
    }

    @FXML
    private void editSpecialty(MouseEvent mouseEvent) {
        editAction(mouseEvent);
    }

    @FXML
    private void addSpecialty() {
        addAction(ExpertDocType.SPECIALTY);
    }

    @FXML
    private void delSpecialty() {
        delAction(listSpecialties);
    }

    @FXML
    private void editQualification(MouseEvent mouseEvent) {
        editAction(mouseEvent);
    }

    @FXML
    private void addQualification() {
        addAction(ExpertDocType.QUALIFICATION);
    }

    @FXML
    private void delQualification() {
        delAction(listQualifications);
    }

    @FXML
    private void editCertificate(MouseEvent mouseEvent) {
        editAction(mouseEvent);
    }

    @FXML
    private void addCertificate() {
        addAction(ExpertDocType.CERTIFICATE);
    }

    @FXML
    private void delCertificate() {
        delAction(listCertificates);
    }

    @Override
    public boolean check() {
        boolean check = true;

        disableErrorStyle(listSpecialties, listQualifications, listCertificates);

        if (errorsInListView(listSpecialties, listQualifications, listCertificates)) {
            check = false;
        }

        return check;
    }

    @Override
    public void setElementsValues() {
        ObservableList listQualificationsItems = FXCollections.observableArrayList();
        ObservableList<SaveInfo> listToViewQualifications = saveInfoList.getListToView(ExpertDocType.QUALIFICATION, listQualificationsItems);

        listQualifications.setItems(listToViewQualifications);
        listQualifications.refresh();

        ObservableList listCertificatesItems = FXCollections.observableArrayList();
        ObservableList<SaveInfo> listToViewCertificates = saveInfoList.getListToView(ExpertDocType.CERTIFICATE, listCertificatesItems);

        listCertificates.setItems(listToViewCertificates);
        listCertificates.refresh();

        ObservableList listSpecialtiesItems = FXCollections.observableArrayList();
        ObservableList<SaveInfo> listToViewSpecialties = saveInfoList.getListToView(ExpertDocType.SPECIALTY, listSpecialtiesItems);

        listSpecialties.setItems(listToViewSpecialties);
        listSpecialties.refresh();
    }
}
