package br.com.finance_project.personal_finance_api.service;

public interface EmailService {
    Void sendResetCode(String to, String code);
}
