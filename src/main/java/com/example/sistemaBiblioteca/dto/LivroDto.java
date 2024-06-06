package com.example.sistemaBiblioteca.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LivroDto {
    private Long livroId;
    @NotEmpty(message = "A imagem do livro é obrigatória.")
    private String imagemDoLivro;
    @NotBlank(message = "O título é obrigatório e não pode ser vazio.")
    private String titulo;
    @NotBlank(message = "O nome do autor é obrigatório e não pode ser vazio.")
    private String nomeDoAutor;
    @NotBlank(message = "A nacionalidade é obrigatória e não pode ser vazia.")
    private String nacionalidade;
    @NotEmpty(message = "A data é obrigatória.")
    private LocalDate data;
    @NotBlank(message = "A editora é obrigatória e não pode ser vazia.")
    private String editora;
    @NotBlank(message = "O gênero é obrigatório e não pode ser vazio.")
    private String genero;
    @NotBlank(message = "A sinopse é obrigatória e não pode ser vazia.")
    private String sinopse;

}
