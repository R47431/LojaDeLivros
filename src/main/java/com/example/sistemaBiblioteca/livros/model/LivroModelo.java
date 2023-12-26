package com.example.sistemaBiblioteca.livros.model;

import java.time.LocalDate;

import com.example.sistemaBiblioteca.autorDoLivro.model.AutoresModelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private AutoresModelo autor;

    @Temporal(TemporalType.DATE)
    private LocalDate data;
    
    private String editora;
    private String genero;
    private String sinopse;
}