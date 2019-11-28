package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aofoms.entity.*;
import ru.aofoms.service.ExpertService;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@FXMLController
public class DownloadXLSController {

    private Stage stage;

    @FXML
    private TextField path;

    @Autowired
    private ExpertService expertService;

    @FXML
    public void initialize() {
        path.setText("D:\\EKMP\\out_xls");
    }

    @FXML
    private void openDirectoryChooser() {
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
    private void cancel(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void ok() {

        try (FileInputStream fis = new FileInputStream("D:\\EKMP\\templates\\templ_excel.xlsx")) {

            XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(fis));
            XSSFSheet sheet = workbook.getSheet("ЭКМП");

            List<Expert> experts = expertService.findAllForXLS();

            for (Expert expert : experts) {

                if (expert.getExclusions().size() == 0 || expertService.getLastExclusionDate(expert.getId()).isBefore(expertService.getLastInclusionDate(expert.getId()))) {

                    for (ExpertSpecialty es : expert.getExpertSpecialties()) {

                        XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);

                        row.createCell(0).setCellValue(expert.getNumber());
                        row.createCell(1).setCellValue(expert.getSurname());
                        row.createCell(2).setCellValue(expert.getName());
                        row.createCell(3).setCellValue(expert.getPatronymic());
                        row.createCell(4).setCellValue(es.getWorkplace().getName());
                        row.createCell(5).setCellValue(es.getWorkplace().getPosition());
                        row.createCell(6).setCellValue(es.getSpecialty().getName());

                        List<Phone> phones = new ArrayList<>(expert.getPhones());
                        row.createCell(7).setCellValue(phones.get(0).getNumber());
                        row.createCell(8).setCellValue(phones.size() > 1 ? phones.get(1).getNumber() : "");

                        QualificationDoc qualificationDoc = expertService.getLastQualificationDoc(es.getSpecialty().getId());
                        row.createCell(9).setCellValue(qualificationDoc != null ? qualificationDoc.getCategory().getName() : "");

                        AcademicDiploma academicDiploma = expertService.getMaxAcademicDiploma(expert.getAcademicDiplomas());
                        row.createCell(10).setCellValue(academicDiploma != null ? academicDiploma.getAcademicDegree().getName() : "");

                        try {
                            Expertise expertise = es.getExpertise().get(0);

                            if (expertise.getYear() == LocalDate.now().getYear() - 1) {
                                row.createCell(11).setCellValue(expertise.getCount());
                                row.createCell(12).setCellValue(expertise.getReexp());
                            } else {
                                row.createCell(11).setCellValue("");
                                row.createCell(12).setCellValue("");
                            }
                        } catch (Exception exception) {
                            row.createCell(11).setCellValue("");
                            row.createCell(12).setCellValue("");
                        }

                    }

                }
            }

            FileOutputStream outputStream = new FileOutputStream(path.getText() + "/ekmp_" + LocalDate.now() + ".xlsx");
            workbook.write(outputStream);
            outputStream.close();

            openExplorerWithFile(path.getText());
            stage.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


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

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
