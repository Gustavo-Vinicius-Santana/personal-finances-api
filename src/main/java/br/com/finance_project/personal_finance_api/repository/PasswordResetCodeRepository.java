package br.com.finance_project.personal_finance_api.repository;

import br.com.finance_project.personal_finance_api.model.PasswordResetCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCode, String> {

    Optional<PasswordResetCode>findByEmailAndCodeAndUsedFalse(String email, String code);

    void deleteByEmail(String email);
}

