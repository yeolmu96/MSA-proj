package sp.informationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sp.informationservice.controller.request.InstitutionRequest;
import sp.informationservice.controller.response.ApiResponse;

import sp.informationservice.controller.response.InstitutionResponse;
import sp.informationservice.entity.Institution;
import sp.informationservice.repository.InstitutionRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;

    //전체조회
    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<InstitutionResponse>> getAllInstitutions() {
        List<Institution> institutions = institutionRepository.findAll();
        List<InstitutionResponse> responses = institutions.stream()
                .map(InstitutionResponse::from)
                .toList();
        return ApiResponse.success("훈련기관 목록을 성공적으로 조회했습니다", responses);
    }

    //id로 조회
    @Override
    @Transactional(readOnly = true)
    public ApiResponse<InstitutionResponse> getInstitution(Long id) {
        return institutionRepository.findById(id)
                .map(institution -> {
                    InstitutionResponse response = InstitutionResponse.from(institution);
                    return ApiResponse.success("훈련기관 정보를 성공적으로 조회했습니다", response);
                })
                .orElse(ApiResponse.failure("해당하는 훈련기관이 없습니다"));
    }

    //생성
    @Override
    @Transactional
    public ApiResponse<InstitutionResponse> createInstitution(InstitutionRequest request) {
        boolean chkExist = institutionRepository.existsByName(request.getName());
        if (chkExist) {
            return ApiResponse.failure("이미 존재하는 기관명입니다.");
        }
        Institution institution = request.toEntity();
        Institution savedInstitution = institutionRepository.save(institution);
        InstitutionResponse response = InstitutionResponse.from(savedInstitution);

        return ApiResponse.success("훈련기관이 성공적으로 등록되었습니다", response);
    }

    //수정
    @Override
    @Transactional
    public ApiResponse<InstitutionResponse> updateInstitution(Long id, InstitutionRequest request) {
        Optional<Institution> findById = institutionRepository.findById(id);
        if (findById.isEmpty()) {
            return ApiResponse.failure("해당하는 훈련기관이 없습니다");
        }
        Institution institution = findById.get();
        institution.update(request);

        Institution savedInstitution = institutionRepository.save(institution);
        InstitutionResponse response = InstitutionResponse.from(savedInstitution);

        return ApiResponse.success("훈련기관이 성공적으로 수정되었습니다", response);
    }

    //삭제
    @Override
    @Transactional
    public ApiResponse<String> deleteInstitution(Long id) {
        Optional<Institution> findById = institutionRepository.findById(id);
        if (findById.isEmpty()) {
            return ApiResponse.failure("해당하는 훈련기관이 없습니다");
        }
        institutionRepository.deleteById(id);
        return ApiResponse.success("해당하는 훈련기관을 삭제했습니다.");
    }

    private Boolean validateDuplicateInstitution(String name) {
        return institutionRepository.existsByName(name);
    }
}
