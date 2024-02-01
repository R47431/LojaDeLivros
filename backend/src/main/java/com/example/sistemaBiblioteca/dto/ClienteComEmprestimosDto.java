package com.example.sistemaBiblioteca.dto;

import java.util.List;

public record ClienteComEmprestimosDto(
                Long clienteId,
                String nome,
                String email,
                List<EmprestimoComLivroDto> emprestimos) {
}
