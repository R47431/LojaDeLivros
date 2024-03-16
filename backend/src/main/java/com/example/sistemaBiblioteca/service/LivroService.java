package com.example.sistemaBiblioteca.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.exception.NotFoundException;
import com.example.sistemaBiblioteca.model.LivroModelo;

import java.io.File;

@Service
public class LivroService {

    // TODO criar tratamento de execao para se ocorra imagem null pedir para fazer
    // novaemnte
    @Value("${app.img.dir}")
    private String imgdir;

    public String diretorioDaImagem(LivroModelo livroModelo) {
       
        String nomeImagem = livroModelo.getLivroId() + ".jpg";
        if (nomeImagem.equals("null")) {
            throw new NotFoundException("");
        }
        return imgdir + File.separator + nomeImagem;
    }

}