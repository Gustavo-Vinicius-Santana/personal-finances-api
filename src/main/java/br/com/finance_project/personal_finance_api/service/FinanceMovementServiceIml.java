package br.com.finance_project.personal_finance_api.service;

import br.com.finance_project.personal_finance_api.dto.FinanceMovementRequestDTO;
import br.com.finance_project.personal_finance_api.dto.FinanceMovementResponseDTO;
import br.com.finance_project.personal_finance_api.model.FinanceMovement;
import br.com.finance_project.personal_finance_api.repository.FinanceMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceMovementServiceIml implements FinanceMovementService {
    private final FinanceMovementRepository financeMovementRepository;

    public List<FinanceMovementResponseDTO> findAll() {
        return financeMovementRepository
                .findAll()
                .stream()
                .map(FinanceMovementResponseDTO::new)
                .toList();
    }

    @Override
    public FinanceMovementResponseDTO findById(Long id) {
        return financeMovementRepository.findById(id)
                .map(FinanceMovementResponseDTO::new)
                .orElseThrow(() -> new RuntimeException("not find"));
    }

    @Override
    public FinanceMovementResponseDTO save(FinanceMovementRequestDTO financeMovement) {
        var movement = FinanceMovement.fromDto(financeMovement);

        var saveMovement = financeMovementRepository.save(movement);

        return new FinanceMovementResponseDTO(saveMovement);
    }

    @Override
    public FinanceMovementResponseDTO update(Long id, FinanceMovementRequestDTO financeMovement) {
        var existingMovement = financeMovementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not find"));

        existingMovement.updateFromDto(financeMovement);

        var updateMovement = financeMovementRepository.save(existingMovement);

        return new FinanceMovementResponseDTO(updateMovement);
    }

    @Override
    public void deleteId(long id) {
        financeMovementRepository.deleteById(id);

    }
}
