package sp.informationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sp.informationservice.controller.request.TrainingRequest;
import sp.informationservice.controller.response.ApiResponse;
import sp.informationservice.controller.response.TrainingResponse;
import sp.informationservice.service.TrainingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/training")
public class TrainingController {
    private final TrainingService trainingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TrainingResponse>>> getAllTrainings() {
        return ResponseEntity.ok(trainingService.getAllTraining());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TrainingResponse>> getTrainingById(@PathVariable Long id) {
        return ResponseEntity.ok(trainingService.getTraining(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TrainingResponse>> createTraining(@Valid @RequestBody TrainingRequest request) {
        return ResponseEntity.ok(trainingService.createTraining(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TrainingResponse>> updateTraining(@Valid @RequestBody TrainingRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(trainingService.updateTraining(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTraining(@PathVariable Long id) {
        return ResponseEntity.ok(trainingService.deleteTraining(id));
    }
}
