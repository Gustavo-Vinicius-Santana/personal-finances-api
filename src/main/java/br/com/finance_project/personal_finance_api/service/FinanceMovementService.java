package br.com.finance_project.personal_finance_api.service;

import br.com.finance_project.personal_finance_api.dto.FinanceMovementRequestDTO;
import br.com.finance_project.personal_finance_api.dto.FinanceMovementResponseDTO;
import br.com.finance_project.personal_finance_api.model.MovementType;
import br.com.finance_project.personal_finance_api.model.User;

import java.util.List;

public interface FinanceMovementService {

    List<FinanceMovementResponseDTO> findAll(User user, MovementType type);
    FinanceMovementResponseDTO findById(Long id, User user);
    FinanceMovementResponseDTO save(FinanceMovementRequestDTO financeMovement, User user);
    FinanceMovementResponseDTO update(Long id, FinanceMovementRequestDTO financeMovement, User user);
    void deleteId(long id, User user);
}
