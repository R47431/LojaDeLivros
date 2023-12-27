package com.example.sistemaBiblioteca.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroDto {
    private Long livroId;
    private String imagemDoLivro;
    private String titulo;
    private String nomeDoAutor;
    private String nacionalidade;
    private LocalDate data;
    private String editora;
    private String genero;
    private String sinopse;
}
