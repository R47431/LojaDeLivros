package com.example.sistemaBiblioteca.dto;

import java.time.LocalDate;


public record PedirEmprestimoDTO(
    ObterClienteDTO obterClienteDTO,
    ObterLivroDTO obterLivroDTO,
    LocalDate dataEmprestimo
) {
}
