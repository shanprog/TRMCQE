package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.controller.newExpertMaster.Window5Controller;
import ru.aofoms.entity.*;
import ru.aofoms.service.ExpertSpecialtyService;
import ru.aofoms.service.WorkplaceService;
import ru.aofoms.util.Action;
import ru.aofoms.util.SaveInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@FXMLController
public class ExpertSpecialtyController extends AbstractElementController {

    @Autowired
    private Window5Controller window5Controller;

    @FXML
    private ComboBox<Workplace> workplaceBox;
    @FXML
    private TreeView<Specialty> specTree;
    @FXML
    private ComboBox<CodeGVS> gvsBox;
    @FXML
    private TextField experienceField;

    private ExpertSpecialty expertSpecialty;

    private final ExpertSpecialtyService expertSpecialtyService;
    private final WorkplaceService workplaceService;

    public ExpertSpecialtyController(ExpertSpecialtyService expertSpecialtyService, WorkplaceService workplaceService) {
        this.expertSpecialtyService = expertSpecialtyService;
        this.workplaceService = workplaceService;
    }

    @FXML
    public void initialize() {

        List<Specialty> specialitiesList = expertSpecialtyService.findAllSpecialties();

        List<TreeItem<Specialty>> nodes = new ArrayList<>();

        for (Specialty sp : specialitiesList)
            nodes.add(new TreeItem<>(sp));

        for (int i = 0; i < specialitiesList.size(); i++) {
            Specialty s = specialitiesList.get(i);
            int ref = s.getRef();

            for (int j = 0; j < specialitiesList.size(); j++) {
                Specialty s2 = specialitiesList.get(j);
                if (ref == s2.getCode())
                    nodes.get(j).getChildren().add(nodes.get(i));
            }
        }

        TreeItem<Specialty> root = new TreeItem<>();
        for (TreeItem<Specialty> node : nodes)
            if (node.getValue().getRef() == -1)
                root = node;

        root.setExpanded(true);
        specTree.setRoot(root);

        workplaceBox.setItems(FXCollections.observableArrayList(workplaceService.findByExpertId(expertController.getId())));
        gvsBox.setItems(FXCollections.observableArrayList(expertSpecialtyService.findAllCodeGVS()));
        gvsBox.getSelectionModel().select(0);
    }

    @FXML
    public void ok(ActionEvent event) {
        if (check()) {
            ExpertSpecialty expertSpecialty;
            Expertise expertise;

            SaveInfo saveInfo = new SaveInfo();
            SaveInfo expertiseSaveInfo = new SaveInfo();


            if (isEditMode()) {
                expertSpecialty = this.expertSpecialty;


                saveInfo.setId(siid);
                saveInfo.setAction(Action.EDIT);
            } else {
                expertSpecialty = new ExpertSpecialty();
                expertise = new Expertise();

                expertise.setSpecialty(expertSpecialty);
                expertise.setCount(0);
                expertise.setReexp(0);
                expertise.setYear(LocalDate.now().getYear() - 1);

                saveInfo.setId(siGenerator.getNextValue(ExpertSpecialty.class));
                saveInfo.setAction(Action.ADD);
                expertiseSaveInfo.setId(siGenerator.getNextValue(Expertise.class));
                expertiseSaveInfo.setObject(expertise);
                expertiseSaveInfo.setAction(Action.ADD);
            }

            assembleElement(expertSpecialty);

            saveInfo.setObject(expertSpecialty);

            if (isNewExpert()) {
                window5Controller.addSaveInfo(saveInfo);
                if (!isEditMode()) window5Controller.addSaveInfo(expertiseSaveInfo);
            } else {
                expertController.addSaveInfo(saveInfo);
                if (!isEditMode()) expertController.addSaveInfo(expertiseSaveInfo);
            }

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        }
    }

    @Override
    protected boolean check() {
        boolean check = true;

        disableErrorStyle(specTree, workplaceBox, experienceField);

        if (errorsInTextField(experienceField)) {
            check = false;
        }

        if (specTree.getSelectionModel().getSelectedItem() == null) {
            enableErrorStyle(specTree);
            check = false;
        }

        if (workplaceBox.getSelectionModel().getSelectedIndex() == -1) {
            enableErrorStyle(workplaceBox);
            check = false;
        }

        if (check) {
            ExpertSpecialty tempExpertSpecialty = new ExpertSpecialty();
            assembleElement(tempExpertSpecialty);

            if (findDuplicates(tempExpertSpecialty)) {
                enableErrorStyle(specTree);
                check = false;
            }
        }

        return check;
    }

    @Override
    protected void assembleElement(Object assembledElement) {
        ((ExpertSpecialty) assembledElement).setSpecialty(specTree.getSelectionModel().getSelectedItem().getValue());
        ((ExpertSpecialty) assembledElement).setWorkplace(workplaceBox.getValue());
        ((ExpertSpecialty) assembledElement).setCodeGVS(gvsBox.getValue());
        ((ExpertSpecialty) assembledElement).setExperience(Integer.valueOf(experienceField.getText().trim()));
    }

    @Override
    public void setValues(Object object) {
        expertSpecialty = (ExpertSpecialty) object;

        specTree.getSelectionModel().select(new TreeItem<>(expertSpecialty.getSpecialty()));
        workplaceBox.getSelectionModel().select(expertSpecialty.getWorkplace());
        gvsBox.getSelectionModel().select(expertSpecialty.getCodeGVS());
        experienceField.setText(String.valueOf(expertSpecialty.getExperience()));
    }

    void setWorkplacesList(ObservableList<Workplace> workplacesList) {
        workplaceBox.setItems(workplacesList);
    }
}
