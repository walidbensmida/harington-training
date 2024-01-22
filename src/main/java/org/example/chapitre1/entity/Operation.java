package org.example.chapitre1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OperationTypeEnum operationType;
    private float amount;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation operation)) return false;
        return Float.compare(getAmount(), operation.getAmount()) == 0 && Objects.equals(getId(), operation.getId()) && getOperationType() == operation.getOperationType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOperationType(), getAmount());
    }

}
