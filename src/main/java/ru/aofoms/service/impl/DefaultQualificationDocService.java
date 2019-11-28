package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.Category;
import ru.aofoms.entity.QualificationDoc;
import ru.aofoms.repository.CategoryRepo;
import ru.aofoms.repository.QualificationDocRepo;
import ru.aofoms.service.QualificationDocService;

import java.util.List;

@Service
public class DefaultQualificationDocService implements QualificationDocService {

    private final QualificationDocRepo qualificationDocRepo;
    private final CategoryRepo categoryRepo;

    public DefaultQualificationDocService(QualificationDocRepo qualificationDocRepo, CategoryRepo categoryRepo) {
        this.qualificationDocRepo = qualificationDocRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<QualificationDoc> findByExpertId(long expertId) {
        return qualificationDocRepo.findByExpertId(expertId);
    }

    @Override
    public void save(QualificationDoc qualificationDoc) {
        qualificationDocRepo.save(qualificationDoc);
    }

    @Override
    public void delete(QualificationDoc qualificationDoc) {
        qualificationDocRepo.delete(qualificationDoc);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public List<QualificationDoc> findBySpecialty(long id) {
        return qualificationDocRepo.findBySpecialty(id);
    }

    @Override
    public QualificationDoc getLastQualificationDoc(long expertSpecId) {
        List<QualificationDoc> qdl = qualificationDocRepo.findBySpecialty(expertSpecId);

        if (qdl.size() > 0) {
            QualificationDoc qualification = qdl.get(0);
            for (QualificationDoc q : qdl) {
                if (q.getCategory().getCode() != 0 && q.getOutDate().isAfter(qualification.getOutDate())) {
                    qualification = q;
                }
            }

            return qualification;
        }

        return null;
    }
}
