package org.example.chapitre1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chapitre1.entity.OperationTypeEnum;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationDto {
    private Long id;
    @NotNull(message = "An operation must have an OperationType")
    private OperationTypeEnum operationType;
    @NotNull(message = "An operation must have an accountId")
    private Long accountId;
    @NotNull(message = "An operation must have an amount value")
    private Float amount;

    public OperationDto(OperationTypeEnum operationType, Long accountId, Float amount) {
        this.operationType = operationType;
        this.accountId = accountId;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationDto that)) return false;
        return Objects.equals(getId(), that.getId()) && getOperationType() == that.getOperationType() && Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOperationType(), getAmount());
    }
}
