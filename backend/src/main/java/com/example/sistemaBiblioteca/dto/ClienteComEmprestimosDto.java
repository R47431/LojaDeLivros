package com.example.sistemaBiblioteca.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteComEmprestimosDto {
    @NotNull(message = "O campo clienteId é obrigatório.")
    private Long clienteId;
    @NotNull(message = "O campo nome é obrigatório.")
    private String nome;
    @Email(message = "O campo email é obrigatório.")
    private String email;
    private List<EmprestimoComLivroDto> emprestemos;
}
