package com.example.sistemaBiblioteca.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoComLivroDto {
    private Long emprestimoId;
    @FutureOrPresent(message = "Data de empréstimo deve ser presente ou futura")
    @Temporal(TemporalType.DATE)
    private LocalDate dataEmprestimo;
    @FutureOrPresent(message = "Data de devolução deve ser no futuro")
    @Temporal(TemporalType.DATE)
    private LocalDate dataDevolucao;
    private LivroDto livro;
}
