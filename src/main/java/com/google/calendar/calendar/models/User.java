package com.google.calendar.calendar.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@Table("table_user")
public class User implements Serializable {

    private static final long serialVersionUID = -844543254388547680L;

    @Id
    @ToString.Exclude
    private Long id;

    private String nome;

    @NotBlank
    @Email
    private String username;

    @NotBlank
    @Size(min = 6)
    @ToString.Exclude
    private String password;
}
