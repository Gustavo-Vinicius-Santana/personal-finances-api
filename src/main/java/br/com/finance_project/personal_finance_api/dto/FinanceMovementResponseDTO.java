package br.com.finance_project.personal_finance_api.dto;

import br.com.finance_project.personal_finance_api.model.FinanceMovement;
import br.com.finance_project.personal_finance_api.model.MovementType;

import java.util.Date;

public record FinanceMovementResponseDTO(
        Long id,
        String description,
        Number amount,
        Date date,
        MovementType type,
        Long userId
) {
    public FinanceMovementResponseDTO(FinanceMovement financeMovement){
        this(
                financeMovement.getId(),
                financeMovement.getDescription(),
                financeMovement.getAmount(),
                financeMovement.getDate(),
                financeMovement.getType(),
                financeMovement.getUser().getId()
        );
    }
}
