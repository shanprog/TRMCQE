package ru.aofoms.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import ru.aofoms.entity.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JDOMLogic {
    public static Document create(List<Expert> experts) {

        Element root = new Element("packet");

        Element version = new Element("version");
        version.addContent("2.1");

        Element date = new Element("date");
        date.addContent(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        root.addContent(version);
        root.addContent(date);

        for (Expert expert : experts) {
            Element expertElement = new Element("omsExpert");

            Element tfokatoElement = new Element("tf_okato");
            tfokatoElement.addContent("10000");
            expertElement.addContent(tfokatoElement);

            Element nExpertElement = new Element("n_expert");
            nExpertElement.addContent(String.valueOf(expert.getNumber()));
            expertElement.addContent(nExpertElement);

            Element famElement = new Element("fam");
            famElement.addContent(expert.getSurname());
            expertElement.addContent(famElement);


            Element imElement = new Element("im");
            imElement.addContent(expert.getName());
            expertElement.addContent(imElement);


            Element otElement = new Element("ot");
            otElement.addContent(expert.getPatronymic());
            expertElement.addContent(otElement);

            Element snilsElement = new Element("SNILS");
            snilsElement.addContent(expert.getSnils());
            expertElement.addContent(snilsElement);

            if (expert.getPhones().size() > 0) {

                List<Phone> phones = new ArrayList<>(expert.getPhones());
                for (int i = 0; i < phones.size(); i++) {

                    if (i > 1)
                        break;

                    Phone phone = phones.get(i);
                    Element phoneElement = new Element("phone");
                    phoneElement.addContent(phone.getNumber());
                    expertElement.addContent(phoneElement);
                }
            }

            if (expert.getEmails().size() > 0) {
                if (!new ArrayList<>(expert.getEmails()).get(0).getEmail().isEmpty()) {
                    Element emailElement = new Element("e_mail");
                    emailElement.addContent(new ArrayList<>(expert.getEmails()).get(0).getEmail());
                    expertElement.addContent(emailElement);
                }
            }

            if (expert.getInclusions().size() > 0) {

                for (Inclusion incl : expert.getInclusions()) {
                    Element expIncludeElement = new Element("expInclude");

                    Element dateBElement = new Element("Date_b");
                    dateBElement.addContent(incl.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    expIncludeElement.addContent(dateBElement);

                    Element organCodElement = new Element("organ_cod");
                    organCodElement.addContent(String.valueOf(incl.getInviteOrg().getInviteOrgCode().getCode()));
                    expIncludeElement.addContent(organCodElement);

                    List<Exclusion> exclusions = expert.getExclusions();
                    if (exclusions.size() > 0) {
                        exclusions.forEach(exclusion -> {
                            if (exclusion.getInclusion().getId() == incl.getId()) {
                                Element dateEElement = new Element("date_e");
                                dateEElement.addContent(exclusion.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                                expIncludeElement.addContent(dateEElement);

                                Element nameEElement = new Element("Name_e");
                                nameEElement.addContent(String.valueOf(exclusion.getExclusionReason().getCode()));
                                expIncludeElement.addContent(nameEElement);
                            }
                        });

                    }
                    expertElement.addContent(expIncludeElement);
                }
            }

            Element expDocElement = new Element("expDoc");

            if (expert.getAcademicDiplomas().size() > 0) {
                for (AcademicDiploma ad : expert.getAcademicDiplomas()) {
                    Element scDiplomElement = new Element("docScDiplom");
                    Element nameStepElement = new Element("name_step");
                    nameStepElement.addContent(String.valueOf(ad.getAcademicDegree().getCode()));
                    scDiplomElement.addContent(nameStepElement);
                    expDocElement.addContent(scDiplomElement);
                }
            }

            List<ExpertSpecialty> expertSpecialties = expert.getExpertSpecialties();

            if (expertSpecialties.size() > 0) {
                expertSpecialties.forEach(es -> {

                    Certificate cert = getMaxCertificate(es.getCertificates());

                    Element docSertElement = new Element("docSertificate");

                    Element specNameElement = new Element("spec_names");
                    specNameElement.addContent(String.valueOf(es.getSpecialty().getCode()));
                    docSertElement.addContent(specNameElement);

                    Element specCodeElement = new Element("spec_code");
                    specCodeElement.addContent("V015");
                    docSertElement.addContent(specCodeElement);

                    Element dateKElement = new Element("date_k");
                    LocalDate datek = cert.getOutDate().plusYears(5);
                    dateKElement.addContent(datek.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    docSertElement.addContent(dateKElement);

                    Element nameKatElement = new Element("name_kat");
                    QualificationDoc maxDoc = getMaxQualificationDoc(es.getQualificationDocs());
                    nameKatElement.addContent(String.valueOf(maxDoc.getCategory().getCode()));
                    docSertElement.addContent(nameKatElement);


                    Element organNameElement = new Element("organ_name");
                    organNameElement.addContent(String.valueOf(es.getWorkplace().getName()));
                    docSertElement.addContent(organNameElement);

                    Element dolgnostElement = new Element("dolgnost");
                    dolgnostElement.addContent(String.valueOf(es.getWorkplace().getPosition()));
                    docSertElement.addContent(dolgnostElement);

                    Element stazhElement = new Element("stazh");
                    stazhElement.addContent(String.valueOf(es.getExperience()));
                    docSertElement.addContent(stazhElement);


                    Element eksGElement = new Element("eks_g");
                    eksGElement.addContent(String.valueOf(es.getExpertise().get(0).getCount()));
                    docSertElement.addContent(eksGElement);

                    Element eksReElement = new Element("eks_re");
                    eksReElement.addContent(String.valueOf(es.getExpertise().get(0).getReexp()));
                    docSertElement.addContent(eksReElement);

                    if (es.getCodeGVS() != null && es.getCodeGVS().getNumber() != 0) {
                        Element mzNomenElement = new Element("MZnomen");
                        mzNomenElement.addContent(String.valueOf(es.getCodeGVS().getNumber()));
                        docSertElement.addContent(mzNomenElement);
                    }

                    expDocElement.addContent(docSertElement);
                });
            }

            expertElement.addContent(expDocElement);

            Element dateRedElement = new Element("date_red");
            dateRedElement.addContent(expert.getEditDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            expertElement.addContent(dateRedElement);


            root.addContent(expertElement);
        }

        return new Document(root);
    }

    private static Certificate getMaxCertificate(List<Certificate> certificates) {

        Certificate result = null;

        if (certificates.size() > 0) {
            result = certificates.get(0);

            for (Certificate certificate : certificates) {
                if (certificate.getOutDate().isAfter(result.getOutDate())) {
                    result = certificate;
                }
            }
        }

        return result;
    }

    private static QualificationDoc getMaxQualificationDoc(List<QualificationDoc> qualificationDocs) {
        QualificationDoc result = null;

        if (qualificationDocs.size() == 0) {
            result = new QualificationDoc();
            Category category = new Category();
            category.setCode(0);
            result.setCategory(category);

            return result;
        } else if (qualificationDocs.size() == 1) {
            return qualificationDocs.get(0);
        } else {
            int i;
            for (i = 0; i < qualificationDocs.size(); i++) {
                if (qualificationDocs.get(i).getCategory().getCode() == 0)
                    continue;

                result = qualificationDocs.get(i);
            }

            if (result != null) {
                for (int j = i + 1; j < qualificationDocs.size(); j++) {
                    if (qualificationDocs.get(j).getCategory().getCode() != 0) {
                        if (result.getOutDate().isBefore(qualificationDocs.get(j).getOutDate())) {
                            result = qualificationDocs.get(j);
                        }
                    }
                }

                return result;
            } else {
                result = new QualificationDoc();
                Category category = new Category();
                category.setCode(0);
                result.setCategory(category);

                return result;
            }
        }
    }

    public static boolean saveDocument(String fileName, Document doc) {
        boolean complete = true;
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat().setEncoding("windows-1251"));

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            outputter.output(doc, fos);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            complete = false;
        }

        return complete;
    }
}
