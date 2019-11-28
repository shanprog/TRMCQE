package ru.aofoms.service;

import ru.aofoms.entity.TrainingDoc;

import java.util.List;

public interface TrainingDocService {

    List<TrainingDoc> findByExpertId(long expertId);

    void save(TrainingDoc trainingDoc);

    void delete(TrainingDoc trainingDoc);
}
