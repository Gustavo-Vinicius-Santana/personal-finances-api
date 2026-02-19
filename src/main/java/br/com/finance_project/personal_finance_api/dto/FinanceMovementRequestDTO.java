package br.com.finance_project.personal_finance_api.dto;

import br.com.finance_project.personal_finance_api.model.MovementType;

import java.util.Date;

public record FinanceMovementRequestDTO(
        String description,
        Number amount,
        Date date,
        MovementType type
) {
}
