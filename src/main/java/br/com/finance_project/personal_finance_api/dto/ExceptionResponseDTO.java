package br.com.finance_project.personal_finance_api.dto;

import java.time.LocalDateTime;

public record ExceptionResponseDTO(
        LocalDateTime dateTime,
        Integer status,
        String error,
        String message,
        String path
) {
}
