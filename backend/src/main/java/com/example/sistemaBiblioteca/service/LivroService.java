package com.example.sistemaBiblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.exception.NotFoundException;
import com.example.sistemaBiblioteca.mapper.LivroMapper;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.repository.EmprestimosRepository;
import com.example.sistemaBiblioteca.repository.LivroRepository;

import jakarta.transaction.Transactional;

import java.io.File;

@Service
public class LivroService {

    @Autowired
    private  LivroMapper livroMapper;

    private final LivroRepository livroRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;

    }

    @Value("${app.img.dir}")
    private String imgdir;

    public String diretorioDaImagem(LivroModelo livroModelo) {
        String nomeImagem = livroModelo.getLivroId() + ".jpg";
        if (nomeImagem.equals("null")) {
            throw new NotFoundException("");
        }
        return imgdir + File.separator + nomeImagem;
    }

    @Transactional
    public LivroDto verLivro(long livroId) {
        
        LivroModelo livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro Not Found"));

        LivroDto livroModelo = livroMapper.toLivroDto(livro);

        return livroModelo;
    }
}