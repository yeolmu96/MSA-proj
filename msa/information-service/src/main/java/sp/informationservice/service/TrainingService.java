package sp.informationservice.service;

import sp.informationservice.controller.request.TrainingRequest;
import sp.informationservice.controller.response.ApiResponse;
import sp.informationservice.controller.response.InstitutionResponse;
import sp.informationservice.controller.response.TrainingResponse;

import java.util.List;

public interface TrainingService {
    ApiResponse<List<TrainingResponse>> getAllTraining();

    ApiResponse<TrainingResponse> getTraining(Long id);

    ApiResponse<TrainingResponse> createTraining(TrainingRequest request);

    ApiResponse<TrainingResponse> updateTraining(Long id, TrainingRequest request);

    ApiResponse<String> deleteTraining(Long id);

}
