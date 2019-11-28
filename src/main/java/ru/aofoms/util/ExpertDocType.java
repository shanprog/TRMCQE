package ru.aofoms.util;

import ru.aofoms.entity.*;

public enum ExpertDocType {

    SPECIALTY,
    CERTIFICATE,
    QUALIFICATION,
    EXPERTISE,
    TRAINING_DOC,
    ACADEMIC_DIPLOMA,
    DIPLOMA,
    WORKPLACE,
    PHONE,
    EMAIL,
    PASSPORT,
    INCLUSION,
    EXCLUSION;

    public static ExpertDocType getExpertDocType(Object object) {

        if (object instanceof Phone) {
            return PHONE;
        }

        if (object instanceof Email) {
            return EMAIL;
        }

        if (object instanceof Passport) {
            return PASSPORT;
        }

        if (object instanceof Diploma) {
            return DIPLOMA;
        }

        if (object instanceof TrainingDoc) {
            return TRAINING_DOC;
        }

        if (object instanceof AcademicDiploma) {
            return ACADEMIC_DIPLOMA;
        }

        if (object instanceof Workplace) {
            return WORKPLACE;
        }

        if (object instanceof QualificationDoc) {
            return QUALIFICATION;
        }

        if (object instanceof Inclusion) {
            return INCLUSION;
        }

        if (object instanceof Exclusion) {
            return EXCLUSION;
        }

        if (object instanceof ExpertSpecialty) {
            return SPECIALTY;
        }

        if (object instanceof Expertise) {
            return EXPERTISE;
        }

        if (object instanceof Certificate) {
            return CERTIFICATE;
        }

        return null;
    }

    @Override
    public String toString() {

        switch (this) {
            case PHONE:
                return "Телефон";
            case EMAIL:
                return "Email";
            case PASSPORT:
                return "Паспорт";
            case DIPLOMA:
                return "Диплом";
            case EXCLUSION:
                return "Исключения";
            case INCLUSION:
                return "Включения";
            case SPECIALTY:
                return "Специальность";
            case WORKPLACE:
                return "Место работы";
            case TRAINING_DOC:
                return "Документ подготовки";
            case QUALIFICATION:
                return "Квалификация";
            case ACADEMIC_DIPLOMA:
                return "Ученая степень";
            case CERTIFICATE:
                return "Сертификат";
            case EXPERTISE:
                return "Экспертизы";
            default:
                return "";
        }

    }
}
