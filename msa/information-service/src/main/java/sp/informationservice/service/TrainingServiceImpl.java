package sp.informationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sp.informationservice.controller.request.TrainingRequest;
import sp.informationservice.controller.response.ApiResponse;
import sp.informationservice.controller.response.TrainingResponse;
import sp.informationservice.entity.Institution;
import sp.informationservice.entity.Training;
import sp.informationservice.repository.InstitutionRepository;
import sp.informationservice.repository.TrainingRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final InstitutionRepository institutionRepository;

    //전체조회
    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<TrainingResponse>> getAllTraining() {
        List<Training> trainings = trainingRepository.findAll();
        List<TrainingResponse> responses = trainings.stream().map(TrainingResponse::from).toList();
        return ApiResponse.success("훈련과정 목록을 성공적으로 조회했습니다", responses);
    }

    //id로 조회
    @Override
    @Transactional(readOnly = true)
    public ApiResponse<TrainingResponse> getTraining(Long id) {
        return trainingRepository.findById(id)
                .map(training -> {
                    TrainingResponse response = TrainingResponse.from(training);
                    return ApiResponse.success("훈련과정 정보를 성공적으로 조회했습니다", response);
                })
                .orElse(ApiResponse.failure("해당하는 훈련과정이 없습니다"));
    }

    //생성
    @Override
    @Transactional
    public ApiResponse<TrainingResponse> createTraining(TrainingRequest request) {
        boolean chkExist = trainingRepository.existsByName(request.getName());
        if (chkExist) {
            return ApiResponse.failure("이미 존재하는 훈련과정입니다.");
        }

        Optional<Institution> institution = institutionRepository.findById(request.getInstitutionId());
        if (institution.isEmpty()) {
            return ApiResponse.failure("해당하는 훈련기관이 없습니다.");
        }

        Training training = request.toEntity();
        training.setInstitution(institution.get());

        Training savedTraining = trainingRepository.save(training);
        TrainingResponse response = TrainingResponse.from(savedTraining);

        return ApiResponse.success("훈련과정이 성공적으로 등록되었습니다", response);
    }

    //수정
    @Override
    @Transactional
    public ApiResponse<TrainingResponse> updateTraining(Long id, TrainingRequest request) {
        Optional<Training> findById = trainingRepository.findById(id);
        if (findById.isEmpty()) {
            return ApiResponse.failure("해당하는 훈련과정이 없습니다");
        }

        Optional<Institution> institutionOpt = institutionRepository.findById(request.getInstitutionId());
        if (institutionOpt.isEmpty()) {
            return ApiResponse.failure("해당하는 훈련기관이 없습니다");
        }

        Training training = findById.get();
        Institution institution = institutionOpt.get();
        training.update(request, institution);

        Training savedTraining = trainingRepository.save(training);
        TrainingResponse response = TrainingResponse.from(savedTraining);

        return ApiResponse.success("훈련과정이 성공적으로 수정되었습니다", response);
    }


    @Override
    @Transactional
    public ApiResponse<String> deleteTraining(Long id) {
        Optional<Training> findById = trainingRepository.findById(id);
        if (findById.isEmpty()) {
            return ApiResponse.failure("해당하는 훈련과정이 없습니다");
        }
        trainingRepository.deleteById(id);
        return ApiResponse.success("해당하는 훈련과정을 삭제했습니다.");
    }
}
