package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.GeneralProgressRequest;
import org.example.finalproject.api.v1.dtos.GeneralProgressResponse;

import java.util.List;

public interface GeneralProgressService {

    GeneralProgressResponse findById(Integer id);
    List<GeneralProgressResponse> getAllGeneralProgress();
    void createGeneralProgress(GeneralProgressRequest generalProgressRequest);
    void updateGeneralProgress(Integer id, GeneralProgressRequest generalProgressRequest);
    void deleteGeneralProgress(Integer id);

}
