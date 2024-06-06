package com.example.sistemaBiblioteca.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@Entity
@Table(name = "emprestimos")
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emprestimo_id")
    private Long emprestimoId;

    @FutureOrPresent(message = "Data de empréstimo deve ser presente ou futura")
    @Temporal(TemporalType.DATE)
    private LocalDate dataEmprestimo;

    @Future(message = "Data de devolução deve ser no futuro")
    @Temporal(TemporalType.DATE)
    private LocalDate dataDevolucao;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModelo cliente;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private LivroModelo livro;

    public EmprestimoModelo(
            ClienteModelo cliente,
            LivroModelo livro,
            LocalDate dataEmprestimo) {
        this.cliente = cliente;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;

    }

}