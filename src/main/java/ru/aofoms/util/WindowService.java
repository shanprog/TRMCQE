package ru.aofoms.util;

import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aofoms.controller.*;
import ru.aofoms.view.*;

@Service
public class WindowService {

    @Autowired
    private PhoneView phoneView;
    @Autowired
    private EmailView emailView;
    @Autowired
    private PassportView passportView;
    @Autowired
    private DiplomaView diplomaView;
    @Autowired
    private TrainingDocView trainingDocView;
    @Autowired
    private AcademicDiplomaView academicDiplomaView;
    @Autowired
    private WorkplaceView workplaceView;
    @Autowired
    private QualificationDocView qualificationDocView;
    @Autowired
    private InclusionView inclusionView;
    @Autowired
    private ExclusionView exclusionView;
    @Autowired
    private ExpertSpecialtyView expertSpecialtyView;
    @Autowired
    private ExpertiseView expertiseView;
    @Autowired
    private CertificateView certificateView;

    @Autowired
    private PhoneController phoneController;
    @Autowired
    private EmailController emailController;
    @Autowired
    private PassportController passportController;
    @Autowired
    private DiplomaController diplomaController;
    @Autowired
    private TrainingDocController trainingDocController;
    @Autowired
    private AcademicDiplomaController academicDiplomaController;
    @Autowired
    private WorkplaceController workplaceController;
    @Autowired
    private QualificationDocController qualificationDocController;
    @Autowired
    private InclusionController inclusionController;
    @Autowired
    private ExclusionController exclusionController;
    @Autowired
    private ExpertSpecialtyController expertSpecialtyController;
    @Autowired
    private ExpertiseController expertiseController;
    @Autowired
    private CertificateController certificateController;

    public WindowService() {
    }

    public Parent getView(ExpertDocType expertDocType) {

        switch (expertDocType) {
            case PHONE:
                return phoneView.getView();
            case EMAIL:
                return emailView.getView();
            case PASSPORT:
                return passportView.getView();
            case DIPLOMA:
                return diplomaView.getView();
            case TRAINING_DOC:
                return trainingDocView.getView();
            case ACADEMIC_DIPLOMA:
                return academicDiplomaView.getView();
            case WORKPLACE:
                return workplaceView.getView();
            case QUALIFICATION:
                return qualificationDocView.getView();
            case INCLUSION:
                return inclusionView.getView();
            case EXCLUSION:
                return exclusionView.getView();
            case SPECIALTY:
                return expertSpecialtyView.getView();
            case EXPERTISE:
                return expertiseView.getView();
            case CERTIFICATE:
                return certificateView.getView();
        }

        return null;
    }

    public Parent getView(Object object) {
        return getView(ExpertDocType.getExpertDocType(object));
    }

    public ElementController getController(ExpertDocType expertDocType) {
        switch (expertDocType) {
            case PHONE:
                return phoneController;
            case EMAIL:
                return emailController;
            case PASSPORT:
                return passportController;
            case DIPLOMA:
                return diplomaController;
            case TRAINING_DOC:
                return trainingDocController;
            case ACADEMIC_DIPLOMA:
                return academicDiplomaController;
            case WORKPLACE:
                return workplaceController;
            case QUALIFICATION:
                return qualificationDocController;
            case INCLUSION:
                return inclusionController;
            case EXCLUSION:
                return exclusionController;
            case SPECIALTY:
                return expertSpecialtyController;
            case EXPERTISE:
                return expertiseController;
            case CERTIFICATE:
                return certificateController;
        }

        return null;
    }

    public ElementController getController(Object object) {
        return getController(ExpertDocType.getExpertDocType(object));
    }
}
