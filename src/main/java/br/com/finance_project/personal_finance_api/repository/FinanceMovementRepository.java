package br.com.finance_project.personal_finance_api.repository;

import br.com.finance_project.personal_finance_api.model.FinanceMovement;
import br.com.finance_project.personal_finance_api.model.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinanceMovementRepository extends JpaRepository<FinanceMovement, Long> {

    List<FinanceMovement> findByUserId(Long userId);

    Optional<FinanceMovement> findByIdAndUserId(Long id, Long userId);

    List<FinanceMovement> findByUserIdAndType(Long userId, MovementType type);

    void deleteByIdAndUserId(Long id, Long userId);
}
