package com.example.sistemaBiblioteca.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClienteComEmprestimosDto {
   private Long clienteId;
   private String nome;
   private Integer numeroDeTelefone;
   private String email;
   private List<EmprestimoComLivroDto> emprestimos;

}
