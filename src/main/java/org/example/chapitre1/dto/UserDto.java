package org.example.chapitre1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chapitre1.entity.RoleEnum;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private RoleEnum role;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Invalid email format")
    private String email;
    @NotNull
    private String password;


    public UserDto(String firstName, String lastName, RoleEnum role, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto userDto)) return false;
        return Objects.equals(getId(), userDto.getId()) && Objects.equals(getFirstName(), userDto.getFirstName()) && Objects.equals(getLastName(), userDto.getLastName()) && getRole() == userDto.getRole() && Objects.equals(getEmail(), userDto.getEmail()) && Objects.equals(getPassword(), userDto.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getRole(), getEmail(), getPassword());
    }
}
