package br.com.finance_project.personal_finance_api.model;

import br.com.finance_project.personal_finance_api.dto.FinanceMovementRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "finance_movement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinanceMovement {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String description;

    @Column
    private Number amount;

    @Column
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static FinanceMovement fromDto(FinanceMovementRequestDTO dto) {
        FinanceMovement financeMovement = new FinanceMovement();

        financeMovement.setDescription(dto.description());
        financeMovement.setAmount(dto.amount());
        financeMovement.setDate(dto.date());
        financeMovement.setType(dto.type());

        return financeMovement;
    }

    public void updateFromDto(FinanceMovementRequestDTO dto){
        this.setDescription(dto.description());
        this.setAmount(dto.amount());
        this.setDate(dto.date());
        this.setType(dto.type());
    }

}
