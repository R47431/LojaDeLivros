package com.example.sistemaBiblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;

@Getter
@Setter
@Entity
@Table(name = "emprestimos")
public class EmprestimoModelo {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long emprestimoId;

   @ManyToOne
   @JoinColumn(name = "cliente_id")
   private ClienteModelo cliente;

   @ManyToOne
   @JoinColumn(name = "livro_id")
   private LivroModelo livro;

   @FutureOrPresent
   @Temporal(TemporalType.DATE)
   private LocalDate dataEmprestimo;


   @Future
   @Temporal(TemporalType.DATE)
   private LocalDate dataDevolucao;
}