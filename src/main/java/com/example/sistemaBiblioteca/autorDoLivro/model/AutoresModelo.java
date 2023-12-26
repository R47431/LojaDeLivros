package com.example.sistemaBiblioteca.autorDoLivro.model;

import java.time.LocalDate;
import java.util.List;

import com.example.sistemaBiblioteca.livros.model.LivroModelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "autores")
public class AutoresModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autorId;
    private String nome;
    private String nacionalidade;

    @Temporal(TemporalType.DATE)
    private LocalDate dataDeNacimento;

    @OneToMany(mappedBy = "autor")
    private List<LivroModelo> listaDosLivros;
}
