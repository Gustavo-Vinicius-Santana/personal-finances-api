package br.com.finance_project.personal_finance_api.service;

import br.com.finance_project.personal_finance_api.dto.FinanceMovementRequestDTO;
import br.com.finance_project.personal_finance_api.dto.FinanceMovementResponseDTO;
import br.com.finance_project.personal_finance_api.exception.BusinessException;
import br.com.finance_project.personal_finance_api.exception.ResourceNotFoundException;
import br.com.finance_project.personal_finance_api.model.FinanceMovement;
import br.com.finance_project.personal_finance_api.model.MovementType;
import br.com.finance_project.personal_finance_api.model.User;
import br.com.finance_project.personal_finance_api.repository.FinanceMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceMovementServiceIml implements FinanceMovementService {

    private final FinanceMovementRepository financeMovementRepository;

    @Override
    public List<FinanceMovementResponseDTO> findAll(User user, MovementType type) {

        List<FinanceMovement> movements;

        if (type != null) {
            movements = financeMovementRepository
                    .findByUserIdAndType(user.getId(), type);
        } else {
            movements = financeMovementRepository
                    .findByUserId(user.getId());
        }

        return movements
                .stream()
                .map(FinanceMovementResponseDTO::new)
                .toList();
    }

    @Override
    public FinanceMovementResponseDTO findById(Long id, User user) {

        FinanceMovement movement = financeMovementRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movement not found"));

        return new FinanceMovementResponseDTO(movement);
    }

    @Override
    public FinanceMovementResponseDTO save(
            FinanceMovementRequestDTO financeMovement,
            User user
    ) {

        validateBusinessRules(financeMovement);

        var movement = FinanceMovement.fromDto(financeMovement);
        movement.setUser(user);

        var savedMovement = financeMovementRepository.save(movement);

        return new FinanceMovementResponseDTO(savedMovement);
    }

    @Override
    public FinanceMovementResponseDTO update(
            Long id,
            FinanceMovementRequestDTO financeMovement,
            User user
    ) {

        FinanceMovement existingMovement = financeMovementRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movement not found"));

        validateBusinessRules(financeMovement);

        existingMovement.updateFromDto(financeMovement);

        var updatedMovement = financeMovementRepository.save(existingMovement);

        return new FinanceMovementResponseDTO(updatedMovement);
    }

    @Override
    public void deleteId(long id, User user) {

        FinanceMovement movement = financeMovementRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movement not found"));

        financeMovementRepository.delete(movement);
    }

    private void validateBusinessRules(FinanceMovementRequestDTO dto) {

        if (dto.amount() == null ||
                dto.amount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new BusinessException(
                    "Movement amount must be greater than zero"
            );
        }

        if (dto.type() == null) {
            throw new BusinessException(
                    "Movement type is required"
            );
        }

        if (dto.description() == null ||
                dto.description().isBlank()) {

            throw new BusinessException(
                    "Description is required"
            );
        }
    }
}
