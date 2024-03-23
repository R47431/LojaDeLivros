package com.example.sistemaBiblioteca.mapperImpl;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.mapper.LivroMapper;
import com.example.sistemaBiblioteca.model.LivroModelo;

@Component
public class LivroImpl implements LivroMapper {

    @Override
    public LivroDto toLivroDto(LivroModelo livroModelo) {
        if (livroModelo == null) {
            return null;
        }

        Long livroId = livroModelo.getLivroId();
        String imagemDoLivro = livroModelo.getImagemDoLivro();
        String titulo = livroModelo.getTitulo();
        String nomeDoAutor = livroModelo.getNomeDoAutor();
        String nacionalidade = livroModelo.getNacionalidade();
        LocalDate data = livroModelo.getData();
        String editora = livroModelo.getEditora();
        String genero = livroModelo.getGenero();
        String sinopse = livroModelo.getSinopse();

        LivroDto livroDto = new LivroDto(livroId, imagemDoLivro, titulo, nomeDoAutor, nacionalidade, data, editora,
                genero, sinopse);

        return livroDto;
    }

    @Override
    public LivroModelo toLivroModelo(LivroDto livroDto) {
        if (livroDto == null) {
            return null;
        }

        Long livroId = livroDto.livroId();
        String imagemDoLivro = livroDto.imagemDoLivro();
        String titulo = livroDto.titulo();
        String nomeDoAutor = livroDto.nomeDoAutor();
        String nacionalidade = livroDto.nacionalidade();
        LocalDate data = livroDto.data();
        String editora = livroDto.editora();
        String genero = livroDto.genero();
        String sinopse = livroDto.sinopse();

        LivroModelo livroModelo = new LivroModelo(livroId, imagemDoLivro, titulo, nomeDoAutor, nacionalidade, data,
                editora,
                genero, sinopse);

        return livroModelo;
    }

}
