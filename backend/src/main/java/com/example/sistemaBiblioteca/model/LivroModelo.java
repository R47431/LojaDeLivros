package com.example.sistemaBiblioteca.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "livros")
public class LivroModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long livroId;
    private String imagemDoLivro;
    private String titulo;

    private String nomeDoAutor;
    private String nacionalidade;

    @Temporal(TemporalType.DATE)
    private LocalDate data;

    private String editora;
    private String genero;
    private String sinopse;
}