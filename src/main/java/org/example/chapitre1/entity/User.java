package org.example.chapitre1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_user")
@Getter
@Setter
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    private String email;
    @OneToOne
    private Account account;
    private String password;

    public User(Long id, String firstName, String lastName, RoleEnum role, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, RoleEnum role, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && getRole() == user.getRole() && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getRole(), getEmail(), getPassword());
    }

}
