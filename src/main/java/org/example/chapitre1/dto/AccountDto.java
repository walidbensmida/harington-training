package org.example.chapitre1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    @NotNull(message = "An account must have an userId")
    private Long userId;
    private Float balance;

    public AccountDto(Long userId, Float balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
