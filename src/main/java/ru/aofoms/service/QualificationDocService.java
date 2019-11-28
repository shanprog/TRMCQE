package ru.aofoms.service;

import ru.aofoms.entity.Category;
import ru.aofoms.entity.QualificationDoc;

import java.util.List;

public interface QualificationDocService {

    List<QualificationDoc> findByExpertId(long expertId);

    void save(QualificationDoc qualificationDoc);

    void delete(QualificationDoc qualificationDoc);

    List<Category> getCategories();

    List<QualificationDoc> findBySpecialty(long id);

    QualificationDoc getLastQualificationDoc(long expertSpecId);
}
