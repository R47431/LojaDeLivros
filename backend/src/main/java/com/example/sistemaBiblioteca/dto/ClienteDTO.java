package com.example.sistemaBiblioteca.dto;

import java.time.LocalDate;

public record ClienteDTO(
        Long clienteId,
        String nome,
        LocalDate dataDeNascimento,
        Integer numeroDeTelefone,
        String email

) {
}