package com.example.sistemaBiblioteca.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public LivroDto(String titulo, String nomeDoAutor) {
        this.titulo = titulo;
        this.nomeDoAutor = nomeDoAutor;
    }
}
