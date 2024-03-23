package com.example.sistemaBiblioteca.mapper;

import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.model.LivroModelo;

public interface LivroMapper {

    LivroDto toLivroDto(LivroModelo livroModelo);

    LivroModelo toLivroModelo(LivroDto livroDto);

}
