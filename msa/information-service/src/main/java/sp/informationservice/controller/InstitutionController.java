package sp.informationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sp.informationservice.controller.request.InstitutionRequest;
import sp.informationservice.controller.response.ApiResponse;
import sp.informationservice.controller.response.InstitutionResponse;
import sp.informationservice.service.InstitutionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {
    private final InstitutionService institutionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InstitutionResponse>>> getAllInstitutions() {
        return ResponseEntity.ok(institutionService.getAllInstitutions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InstitutionResponse>> getInstitutionById(@PathVariable Long id) {
        return ResponseEntity.ok(institutionService.getInstitution(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InstitutionResponse>> createInstitution(
            @Valid @RequestBody InstitutionRequest request) {
        return ResponseEntity.ok(institutionService.createInstitution(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InstitutionResponse>> updateInstitution(
            @Valid @RequestBody InstitutionRequest request, @PathVariable Long id
    ) {
        return ResponseEntity.ok(institutionService.updateInstitution(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteInstitution(@PathVariable Long id) {
        return ResponseEntity.ok(institutionService.deleteInstitution(id));
    }

}
