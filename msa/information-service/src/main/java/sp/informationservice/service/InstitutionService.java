package sp.informationservice.service;

import sp.informationservice.controller.request.InstitutionRequest;
import sp.informationservice.controller.response.ApiResponse;
import sp.informationservice.controller.response.InstitutionResponse;
import sp.informationservice.enums.InstitutionType;

import java.util.List;

public interface InstitutionService {
    ApiResponse<List<InstitutionResponse>> getAllInstitutions();

    ApiResponse<InstitutionResponse> getInstitution(Long id);

    ApiResponse<InstitutionResponse> createInstitution(InstitutionRequest request);

    ApiResponse<InstitutionResponse> updateInstitution(Long id, InstitutionRequest request);

    ApiResponse<String> deleteInstitution(Long id);

}
