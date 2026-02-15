package br.com.finance_project.personal_finance_api.service;

import br.com.finance_project.personal_finance_api.dto.FinanceMovementRequestDTO;
import br.com.finance_project.personal_finance_api.dto.FinanceMovementResponseDTO;

import java.util.List;

public interface FinanceMovementService {
    List<FinanceMovementResponseDTO> findAll();

    FinanceMovementResponseDTO findById(Long id);

    FinanceMovementResponseDTO save(FinanceMovementRequestDTO financeMovement);

    FinanceMovementResponseDTO update(Long id, FinanceMovementRequestDTO financeMovement);

    void deleteId(long id);
}
