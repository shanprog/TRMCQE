package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.jdom2.Document;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.entity.Expert;
import ru.aofoms.service.DownloadNameService;
import ru.aofoms.service.ExpertService;
import ru.aofoms.util.JDOMLogic;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@FXMLController
public class DownloadXMLController {

    private Stage stage;

    private ObservableList<Expert> currentExperts;
    private ObservableList<Expert> downloadExperts;

    @Autowired
    private ExpertService expertService;
    @Autowired
    private DownloadNameService downloadNameService;

    @FXML
    private ListView<Expert> listFrom;
    @FXML
    private ListView<Expert> listTo;
    @FXML
    private TextField path;

    @FXML
    public void initialize() {
        path.setText("D:\\EKMP\\out_xml");
        currentExperts = FXCollections.observableArrayList(expertService.findAllForXML());
        downloadExperts = FXCollections.observableArrayList();

        listFrom.setItems(currentExperts);
    }

    @FXML
    private void openDirectoryChooser(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Сохранить в...");

        File directory = new File(path.getText().trim());

        directoryChooser.setInitialDirectory(directory);
        directory = directoryChooser.showDialog(stage);

        if (directory != null) {
            path.setText(directory.getAbsolutePath());
        }
    }

    @FXML
    private void addExpert(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
            Expert selectedItem = listFrom.getSelectionModel().getSelectedItem();
            downloadExperts.add(selectedItem);
            currentExperts.remove(selectedItem);

            downloadExperts.sort(Comparator.comparingInt(o -> Integer.valueOf(o.getNumber())));

            listFrom.setItems(currentExperts);
            listTo.setItems(downloadExperts);
        }
    }

    @FXML
    private void delExpert(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
            Expert selectedItem = listTo.getSelectionModel().getSelectedItem();
            downloadExperts.remove(selectedItem);
            currentExperts.add(selectedItem);

            currentExperts.sort(Comparator.comparingInt(o -> Integer.valueOf(o.getNumber())));

            listFrom.setItems(currentExperts);
            listTo.setItems(downloadExperts);
        }
    }

    @FXML
    private void ok() {
        Document doc = JDOMLogic.create(downloadExperts);

        String nNumber;

        int nextNumber = downloadNameService.getMaxNumber("xml") + 1;
        if (nextNumber < 10) {
            nNumber = "0" + nextNumber;
        } else nNumber = String.valueOf(nextNumber);

        if (JDOMLogic.saveDocument(path.getText() + "\\E28" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM")) + nNumber + ".xml", doc)) {
            downloadNameService.addDoc("xml", nextNumber, LocalDate.now());
            openExplorerWithFile(path.getText());
            System.out.println("Документ создан");
//            infoAllert("Документ создан");
        } else {
            System.out.println("Документ не создан");
//            infoAllert("Документ не создан");
        }

        stage.close();
    }

    @FXML
    private void cancel() {
        stage.close();
    }

    @FXML
    private void addAll() {
        downloadExperts.addAll(currentExperts);
        currentExperts.clear();

        downloadExperts.sort(Comparator.comparingInt(o -> Integer.valueOf(o.getNumber())));

        listFrom.setItems(currentExperts);
        listTo.setItems(downloadExperts);
    }

    @FXML
    public void delAll() {
        currentExperts.addAll(downloadExperts);
        downloadExperts.clear();

        currentExperts.sort(Comparator.comparingInt(o -> Integer.valueOf(o.getNumber())));

        listFrom.setItems(currentExperts);
        listTo.setItems(downloadExperts);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // https://docs.oracle.com/javase/tutorial/uiswing/misc/desktop.html
    private void openExplorerWithFile(String path) {
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }

        try {
            if (desktop != null)
                desktop.open(new File(path));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
