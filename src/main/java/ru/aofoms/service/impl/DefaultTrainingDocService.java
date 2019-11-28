package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.TrainingDoc;
import ru.aofoms.repository.TrainingDocRepo;
import ru.aofoms.service.TrainingDocService;

import java.util.List;

@Service
public class DefaultTrainingDocService implements TrainingDocService {

    private final TrainingDocRepo trainingDocRepo;

    public DefaultTrainingDocService(TrainingDocRepo trainingDocRepo) {
        this.trainingDocRepo = trainingDocRepo;
    }

    @Override
    public List<TrainingDoc> findByExpertId(long expertId) {
        return trainingDocRepo.findByExpertId(expertId);
    }

    @Override
    public void save(TrainingDoc trainingDoc) {
        trainingDocRepo.save(trainingDoc);
    }

    @Override
    public void delete(TrainingDoc trainingDoc) {
        trainingDocRepo.delete(trainingDoc);
    }
}
