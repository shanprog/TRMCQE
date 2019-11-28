package ru.aofoms.util;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aofoms.entity.*;
import ru.aofoms.service.ExpertService;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class SolutionPrinter {

    @Autowired
    private ExpertService expertService;

    enum SolutionType {INCLUSION, EXCLUSION}

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private Expert expert;

    public void aboutInclusion(Expert expert) {
        this.expert = expert;
        exportSolutionToWord(SolutionType.INCLUSION);
    }

    public void aboutExclusion(Expert expert) {
        this.expert = expert;
        exportSolutionToWord(SolutionType.EXCLUSION);
    }

    private void exportSolutionToWord(SolutionType solutionType) {
        String templPath = "";
        String resultPath = "";

        if (solutionType == SolutionType.INCLUSION) {
            templPath = "D:\\EKMP\\templates\\templ_include.docx";
            resultPath = "D:\\EKMP\\incl\\включение_" + expert.getNumber() + ".docx";
        } else if (solutionType == SolutionType.EXCLUSION) {
            templPath = "D:\\EKMP\\templates\\templ_exclude.docx";
            resultPath = "D:\\EKMP\\excl\\исключение_" + expert.getNumber() + ".docx";
        }

        try (FileInputStream fileInputStream = new FileInputStream(templPath)) {

            XWPFDocument docxFile = new XWPFDocument(OPCPackage.open(fileInputStream));

            if (solutionType == SolutionType.INCLUSION) {
                inclGeneration(docxFile);
            } else if (solutionType == SolutionType.EXCLUSION) {
                exclGeneration(docxFile);
            }

            FileOutputStream outputStream = new FileOutputStream(resultPath);
            docxFile.write(outputStream);
            outputStream.close();

            openExplorerWithFile(resultPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inclGeneration(XWPFDocument docxFile) {
        List<XWPFParagraph> paragraphs = docxFile.getParagraphs();

        for (XWPFParagraph xwpfParagraph : paragraphs) {
            replace(xwpfParagraph, "${fio}", expert.getSurname() + " " + expert.getName() + " " + expert.getPatronymic());
            replace(xwpfParagraph, "${number}", expert.getNumber());
        }

        for (XWPFTable tbl : docxFile.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {

                        Passport passport = expertService.findLastPassport(expert.getId());
                        String textPassport = "Паспорт: " + passport.getSeries() + " " + passport.getNumber() + "\nВыдан: " + passport.getOutOrg() + ", " + passport.getOutDate().format(dateFormatter) + "\nРегистрация: " + passport.getRegistration();
                        replace(p, "${passport}", textPassport);

                        InviteOrg inviteOrg = expertService.findLastInclusion(expert.getId()).getInviteOrg();
                        String textInviteOrg = "Организация: " + inviteOrg.getName() + "\n" + "Адрес: " + inviteOrg.getAddress();
                        replace(p, "${invite}", textInviteOrg);

                        StringBuilder textDiploma = new StringBuilder();
                        for (Diploma diploma : expertService.findDiplomasByExpertId(expert.getId())) {
                            textDiploma
                                    .append("Специальность: ")
                                    .append(diploma.getSpecial())
                                    .append("\nСерия и номер: ")
                                    .append(diploma.getSeries())
                                    .append(" ")
                                    .append(diploma.getNumber())
                                    .append("\nВыдан: ")
                                    .append(diploma.getOutOrg())
                                    .append(", ")
                                    .append(diploma.getOutDate())
                                    .append("\n")
                                    .append("\n");
                        }
                        replace(p, "${diplom}", textDiploma.toString());

                        StringBuilder textCertificate = new StringBuilder();
                        for (ExpertSpecialty es : expertService.findExpertSpecialtiesByExpertId(expert.getId())) {
                            for (Certificate c : expertService.findCertificatesBySpecId(es.getId())) {
                                textCertificate
                                        .append("Выдан: ").append(c.getOutOrg())
                                        .append(" ").append(c.getOutDate().format(dateFormatter)).append(",")
                                        .append("\nспециальность: ").append(es.getSpecialty().getName()).append(",")
                                        .append("\nсрок действия: ").append(c.getOutDate().plusYears(5L))
                                        .append("\n")
                                        .append("\n");
                            }

                        }
                        replace(p, "${cert}", textCertificate);

                        StringBuilder textTrainingDoc = new StringBuilder();
                        for (TrainingDoc trainingDoc : expertService.findActiveTrainingDocs(expert.getId())) {
                            textTrainingDoc
                                    .append(trainingDoc.getCycleName())
                                    .append(" от ").append(trainingDoc.getOutDate().format(dateFormatter))
                                    .append("\nчасов: ").append(trainingDoc.getHours())
                                    .append("\nвыдан: ").append(trainingDoc.getOutOrg())
                                    .append("\n")
                                    .append("\n");
                        }
                        replace(p, "${training}", textTrainingDoc);

                        StringBuilder textQualification = new StringBuilder();
                        for (QualificationDoc qualification : expertService.findQualificationDocsByExpertId(expert.getId())) {
                            if (qualification.getCategory().getCode() == 0)
                                textQualification
                                        .append(qualification.getCategory().getName())
                                        .append("\n")
                                        .append("\n");
                            else
                                textQualification
                                        .append(qualification.getCategory().getName())
                                        .append(" от ").append(qualification.getOutDate().format(dateFormatter))
                                        .append("\nномер документа: ").append(qualification.getNumber())
                                        .append(", ").append(qualification.getOutOrg())
                                        .append("\n")
                                        .append("\n");
                        }
                        replace(p, "${qual}", textQualification);

                        StringBuilder textAcademDiploma = new StringBuilder();
                        for (AcademicDiploma diploma : expertService.findAcademicDiplomasByExpertId(expert.getId())) {
                            textAcademDiploma
                                    .append(diploma.getAcademicDegree().getName())
                                    .append("\nНомер: ").append(diploma.getSeries()).append(" ").append(diploma.getNumber())
                                    .append(", от ").append(diploma.getOutDate().format(dateFormatter))
                                    .append("\n")
                                    .append("\n");
                        }
                        replace(p, "${academ}", textAcademDiploma);

                        StringBuilder textJob = new StringBuilder();
                        for (Workplace workplace : expertService.findWorkplacesByExpertId(expert.getId())) {
                            textJob
                                    .append(workplace.getName())
                                    .append("\n").append(workplace.getAddress())
                                    .append("\n")
                                    .append("\n");
                        }
                        replace(p, "${job}", textJob);
                    }
                }
            }
        }
    }

    private void exclGeneration(XWPFDocument docxFile) {

        List<XWPFParagraph> paragraphs = docxFile.getParagraphs();

        for (XWPFParagraph xwpfParagraph : paragraphs) {
            replace(xwpfParagraph, "${fio}", expert.getSurname() + " " + expert.getName() + " " + expert.getPatronymic());
            replace(xwpfParagraph, "${date}", expertService.getLastExclusionDate(expert.getId()).format(dateFormatter));

            StringBuilder textCert = new StringBuilder();

            List<ExpertSpecialty> specialties = expertService.findExpertSpecialtiesByExpertId(expert.getId());
            IntStream.range(0, specialties.size()).forEach(i -> {
                textCert.append(specialties.get(i).getSpecialty().getName());
                if (i + 1 < specialties.size()) {
                    textCert.append(", ");
                }
            });

            replace(xwpfParagraph, "${spec}", textCert);
        }
    }


    private static <V> void replace(XWPFParagraph paragraph, String searchText, V replacement) {
        boolean found = true;
        while (found) {
            found = false;
            int pos = paragraph.getText().indexOf(searchText);
            if (pos >= 0) {
                found = true;
                Map<Integer, XWPFRun> posToRuns = getPosToRuns(paragraph);
                XWPFRun run = posToRuns.get(pos);
                XWPFRun lastRun = posToRuns.get(pos + searchText.length() - 1);
                int runNum = paragraph.getRuns().indexOf(run);
                int lastRunNum = paragraph.getRuns().indexOf(lastRun);
                String texts[] = replacement.toString().split("\n");
                run.setText(texts[0], 0);
                XWPFRun newRun = run;
                for (int i = 1; i < texts.length; i++) {
                    newRun.addBreak();
                    newRun = paragraph.insertNewRun(runNum + i);

                    newRun.setText(texts[i]);
                    newRun.setBold(run.isBold());
                    newRun.setCapitalized(run.isCapitalized());
                    // newRun.setCharacterSpacing(run.getCharacterSpacing());
                    newRun.setColor(run.getColor());
                    newRun.setDoubleStrikethrough(run.isDoubleStrikeThrough());
                    newRun.setEmbossed(run.isEmbossed());
                    newRun.setFontFamily(run.getFontFamily());
                    newRun.setFontSize(run.getFontSize());
                    newRun.setImprinted(run.isImprinted());
                    newRun.setItalic(run.isItalic());
                    newRun.setKerning(run.getKerning());
                    newRun.setShadow(run.isShadowed());
                    newRun.setSmallCaps(run.isSmallCaps());
                    newRun.setStrikeThrough(run.isStrikeThrough());
                    newRun.setSubscript(run.getSubscript());
                    newRun.setUnderline(run.getUnderline());
                }
                for (int i = lastRunNum + texts.length - 1; i > runNum + texts.length - 1; i--) {
                    paragraph.removeRun(i);
                }
            }
        }
    }

    private static Map<Integer, XWPFRun> getPosToRuns(XWPFParagraph paragraph) {
        int pos = 0;
        Map<Integer, XWPFRun> map = new HashMap<>(10);
        for (XWPFRun run : paragraph.getRuns()) {
            String runText = run.text();
            if (runText != null) {
                for (int i = 0; i < runText.length(); i++) {
                    map.put(pos + i, run);
                }
                pos += runText.length();
            }
        }
        return (map);
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
