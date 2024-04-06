package com.example.sistemaBiblioteca.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "livros")
@Getter
@Setter
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

    
    public LivroModelo(Long livroId, String imagemDoLivro, String titulo, String nomeDoAutor, String nacionalidade,
            LocalDate data, String editora, String genero, String sinopse) {
        this.livroId = livroId;
        this.imagemDoLivro = imagemDoLivro;
        this.titulo = titulo;
        this.nomeDoAutor = nomeDoAutor;
        this.nacionalidade = nacionalidade;
        this.data = data;
        this.editora = editora;
        this.genero = genero;
        this.sinopse = sinopse;
    }


    public LivroModelo(Long livroId) {
        this.livroId = livroId;
    }


    public LivroModelo(Long livroId, String imagemDoLivro, String titulo, String nomeDoAutor, String nacionalidade,
            LocalDate data, String editora) {
        this.livroId = livroId;
        this.imagemDoLivro = imagemDoLivro;
        this.titulo = titulo;
        this.nomeDoAutor = nomeDoAutor;
        this.nacionalidade = nacionalidade;
        this.data = data;
        this.editora = editora;
    }


    public LivroModelo() {
    }
}