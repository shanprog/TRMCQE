package ru.aofoms.util;

public class ExpertMenuItem {

    private ExpertDocType expertDocType;

    public ExpertMenuItem(ExpertDocType docType) {
        this.expertDocType = docType;
    }

    public ExpertDocType getExpertDocType() {
        return expertDocType;
    }

    @Override
    public String toString() {
        return expertDocType.toString();
    }
}
