package com.example.sistemaBiblioteca.dto;

import java.time.LocalDate;

public record EmprestimoComLivroDto(
      Long emprestimoId,
      LocalDate dataEmprestimo,
      LocalDate dataDevolucao,
      LivroDto livro

      

      ) {
}
