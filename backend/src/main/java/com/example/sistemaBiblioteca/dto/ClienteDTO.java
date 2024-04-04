package com.example.sistemaBiblioteca.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter 
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
        Long clienteId;
        String nome;
        LocalDate dataDeNascimento;
        String numeroDeTelefone;
        String email;
        
}
