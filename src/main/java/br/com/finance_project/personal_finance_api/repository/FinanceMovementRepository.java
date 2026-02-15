package br.com.finance_project.personal_finance_api.repository;

import br.com.finance_project.personal_finance_api.model.FinanceMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceMovementRepository extends JpaRepository<FinanceMovement, Long> {
}
