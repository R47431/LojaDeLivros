package com.example.sistemaBiblioteca.dto;

import java.time.LocalDate;

public record LivroDto(
        Long livroId,
        String imagemDoLivro,
        String titulo,
        String nomeDoAutor,
        String nacionalidade,
        LocalDate data,
        String editora,
        String genero,
        String sinopse) {
}
