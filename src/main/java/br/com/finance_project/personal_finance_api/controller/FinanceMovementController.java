package br.com.finance_project.personal_finance_api.controller;

import br.com.finance_project.personal_finance_api.dto.FinanceMovementRequestDTO;
import br.com.finance_project.personal_finance_api.dto.FinanceMovementResponseDTO;
import br.com.finance_project.personal_finance_api.service.FinanceMovementServiceIml;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance-movement")
@RequiredArgsConstructor
public class FinanceMovementController {

    private final FinanceMovementServiceIml financeMovementService;

    @GetMapping
    public ResponseEntity<List<FinanceMovementResponseDTO>> findAll() {
        return ResponseEntity.ok(financeMovementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceMovementResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(financeMovementService.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinanceMovementResponseDTO> save(
            @Valid @RequestBody FinanceMovementRequestDTO requestFinanceMovement
    ){
        FinanceMovementResponseDTO saveMovement = financeMovementService.save(requestFinanceMovement);

        return ResponseEntity.ok(saveMovement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinanceMovementResponseDTO> update(
            @PathVariable("id") long id,
            @Valid @RequestBody FinanceMovementRequestDTO requestFinanceMovement
    ){
        FinanceMovementResponseDTO updateMovement = financeMovementService.update(id, requestFinanceMovement);

        return ResponseEntity.ok(updateMovement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FinanceMovementResponseDTO> delete(@PathVariable("id") long id){
        financeMovementService.deleteId(id);

        return ResponseEntity.noContent().build();
    }
}
