package br.com.finance_project.personal_finance_api.controller;

import br.com.finance_project.personal_finance_api.dto.FinanceMovementRequestDTO;
import br.com.finance_project.personal_finance_api.dto.FinanceMovementResponseDTO;
import br.com.finance_project.personal_finance_api.dto.FinanceMovementUserFinancesResponseDTO;
import br.com.finance_project.personal_finance_api.model.MovementType;
import br.com.finance_project.personal_finance_api.model.User;
import br.com.finance_project.personal_finance_api.service.FinanceMovementServiceIml;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance-movement")
@RequiredArgsConstructor
public class FinanceMovementController {

    private final FinanceMovementServiceIml financeMovementService;

    @GetMapping
    public ResponseEntity<List<FinanceMovementResponseDTO>> findAll(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) MovementType type
    ) {
        return ResponseEntity.ok(financeMovementService.findAll(user, type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceMovementResponseDTO> findById(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(financeMovementService.findById(id, user));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinanceMovementResponseDTO> save(
            @Valid @RequestBody FinanceMovementRequestDTO requestFinanceMovement,
            @AuthenticationPrincipal User user
    ){
        FinanceMovementResponseDTO saveMovement = financeMovementService.save(requestFinanceMovement, user);

        return ResponseEntity.ok(saveMovement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinanceMovementResponseDTO> update(
            @PathVariable("id") long id,
            @Valid @RequestBody FinanceMovementRequestDTO requestFinanceMovement,
            @AuthenticationPrincipal User user
    ){
        FinanceMovementResponseDTO updateMovement = financeMovementService.update(id, requestFinanceMovement, user);

        return ResponseEntity.ok(updateMovement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FinanceMovementResponseDTO> delete(
            @PathVariable("id") long id, @AuthenticationPrincipal User user
    ){
        financeMovementService.deleteId(id, user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("user-finances")
    public ResponseEntity<FinanceMovementUserFinancesResponseDTO> userFinances(
            @AuthenticationPrincipal User user
    ){
        FinanceMovementUserFinancesResponseDTO userFinance = financeMovementService.userFinances(user);
        return ResponseEntity.ok(userFinance);
    }
}
