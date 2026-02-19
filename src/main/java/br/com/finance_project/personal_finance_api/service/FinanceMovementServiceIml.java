package br.com.finance_project.personal_finance_api.service;

import br.com.finance_project.personal_finance_api.dto.FinanceMovementRequestDTO;
import br.com.finance_project.personal_finance_api.dto.FinanceMovementResponseDTO;
import br.com.finance_project.personal_finance_api.model.FinanceMovement;
import br.com.finance_project.personal_finance_api.model.User;
import br.com.finance_project.personal_finance_api.repository.FinanceMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceMovementServiceIml implements FinanceMovementService {
    private final FinanceMovementRepository financeMovementRepository;

    public List<FinanceMovementResponseDTO> findAll(User user) {

        return financeMovementRepository
                .findByUserId(user.getId())
                .stream()
                .map(FinanceMovementResponseDTO::new)
                .toList();
    }

    @Override
    public FinanceMovementResponseDTO findById(Long id, User user) {
        return financeMovementRepository
                .findByIdAndUserId(id, user.getId())
                .map(FinanceMovementResponseDTO::new)
                .orElseThrow(() -> new RuntimeException("Movement not found"));
    }

    @Override
    public FinanceMovementResponseDTO save(FinanceMovementRequestDTO financeMovement, User user) {
        var movement = FinanceMovement.fromDto(financeMovement);

        movement.setUser(user);

        var saveMovement = financeMovementRepository.save(movement);

        return new FinanceMovementResponseDTO(saveMovement);
    }

    @Override
    public FinanceMovementResponseDTO update(Long id, FinanceMovementRequestDTO financeMovement, User user) {
        var existingMovement = financeMovementRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Movement not found"));

        existingMovement.updateFromDto(financeMovement);

        var updateMovement = financeMovementRepository.save(existingMovement);

        return new FinanceMovementResponseDTO(updateMovement);
    }

    @Override
    public void deleteId(long id, User user) {
        var movement = financeMovementRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Movement not found"));

        financeMovementRepository.delete(movement);
    }
}
