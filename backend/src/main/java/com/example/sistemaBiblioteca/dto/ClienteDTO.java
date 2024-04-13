package com.example.sistemaBiblioteca.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    
    @NotBlank(message = "NOME NAO PODE SER VAZIO")
    private String nome;

    @Past(message = "DATA DE NASCIMENTO DEVE SER NO PASSADO")
    private LocalDate dataDeNascimento;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "NUMERO DE TELEFONE INVALIDO")
    @NotBlank(message = "TELEFONE NAO PODE SER VAZIO")
    private String numeroDeTelefone;

   @Email(message = "EMAIL INVALIDO")
    private String email;


}
