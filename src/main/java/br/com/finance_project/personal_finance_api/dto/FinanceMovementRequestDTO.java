package br.com.finance_project.personal_finance_api.dto;

import java.util.Date;

public record FinanceMovementRequestDTO(
        String description,
        Number amount,
        Date date,
        String type
) {
}
