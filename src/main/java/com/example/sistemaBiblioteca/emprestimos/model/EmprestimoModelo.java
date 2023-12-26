package com.example.sistemaBiblioteca.emprestimos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import com.example.sistemaBiblioteca.client.model.ClienteModelo;
import com.example.sistemaBiblioteca.livros.model.LivroModelo;

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

   @Temporal(TemporalType.DATE)
   private LocalDate dataEmprestimo;

   @Temporal(TemporalType.DATE)
   private LocalDate dataDevolucao;
}

