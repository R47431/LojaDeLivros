package com.example.sistemaBiblioteca.dto;

import java.time.LocalDate;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmprestimoComLivroDto {
   private Long emprestimoId;
   private LocalDate dataEmprestimo;
   private LocalDate dataDevolucao;
   private LivroDto livro;
  
}
