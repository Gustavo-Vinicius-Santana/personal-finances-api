package br.com.finance_project.personal_finance_api.dto;

import java.math.BigDecimal;

public record FinanceMovementUserFinancesResponseDTO(
        BigDecimal total
) {
}
