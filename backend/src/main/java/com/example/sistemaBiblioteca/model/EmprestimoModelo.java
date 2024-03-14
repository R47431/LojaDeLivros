package com.example.sistemaBiblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@Entity
@Table(name = "emprestimos")
public class EmprestimoModelo {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "emprestimo_id")
   private Long emprestimoId;

   @JsonBackReference
   @ManyToOne
   @JoinColumn(name = "cliente_id")
   private ClienteModelo cliente;

   @ManyToOne
   @JoinColumn(name = "livro_id")
   private LivroModelo livro;

   @FutureOrPresent(message = "Data de empréstimo deve ser presente ou futura")
   @Temporal(TemporalType.DATE)
   private LocalDate dataEmprestimo;

   @Future(message = "Data de devolução deve ser no futuro")
   @Temporal(TemporalType.DATE)
   private LocalDate dataDevolucao;

   public EmprestimoModelo(ClienteModelo cliente, LivroModelo livro,
         @FutureOrPresent(message = "Data de empréstimo deve ser presente ou futura") LocalDate dataEmprestimo) {
      this.cliente = cliente;
      this.livro = livro;
      this.dataEmprestimo = dataEmprestimo;
   }

   public EmprestimoModelo(Long emprestimoId, ClienteModelo cliente, LivroModelo livro,
         @FutureOrPresent(message = "Data de empréstimo deve ser presente ou futura") LocalDate dataEmprestimo,
         @Future(message = "Data de devolução deve ser no futuro") LocalDate dataDevolucao) {
      this.emprestimoId = emprestimoId;
      this.cliente = cliente;
      this.livro = livro;
      this.dataEmprestimo = dataEmprestimo;
      this.dataDevolucao = dataDevolucao;
   }

   public EmprestimoModelo() {
   }

   

}