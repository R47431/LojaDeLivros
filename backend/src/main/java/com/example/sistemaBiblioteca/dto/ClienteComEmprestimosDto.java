package com.example.sistemaBiblioteca.dto;

import java.util.List;

import org.springframework.stereotype.Component;

public record ClienteComEmprestimosDto(
                Long clienteId,
                String nome,
                String email,
                List<EmprestimoComLivroDto> emprestimos) {
}
